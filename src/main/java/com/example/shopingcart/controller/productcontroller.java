package com.example.shopingcart.controller;
import com.example.shopingcart.model.product;
import com.example.shopingcart.repository.productRepo;

import com.example.shopingcart.model.RespondMessage;
import com.example.shopingcart.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/")
public class productcontroller {

    @Autowired
    private FileService fileService;

    @Autowired
    private productRepo productRepo;


    @GetMapping(value = "/products")
    public ResponseEntity<Map<String, Object>> getListProduct(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                              @RequestParam(value = "size", required = false, defaultValue = "6") int size){

        List<product> listProduct = new ArrayList<>();
        Pageable paging  = PageRequest.of(page, size);
        Page<product> pagePro = productRepo.findAll(paging);

        listProduct = pagePro.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("products", listProduct);
        response.put("currentpage", pagePro.getNumber());
        response.put("totalitem", pagePro.getTotalElements());
        response.put("totalpage", pagePro.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @GetMapping(value = "/products/{imgname}")
    public ResponseEntity<ByteArrayResource> addNewPro(@PathVariable String imgname){
            if(imgname!=""| imgname!=null){
                Path fileName = Paths.get("src/main/resources/images",imgname);
                try {
                    byte[] buffer = Files.readAllBytes(fileName);
                    ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
                    return  ResponseEntity.ok()
                            .contentLength(buffer.length)
                            .contentType(MediaType.parseMediaType("image/png"))
                            .body(byteArrayResource);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/admin/products")
    public ResponseEntity<RespondMessage> addProduct(@RequestParam(value = "file",required = false) MultipartFile file,
                                                     @RequestParam("productName") String productName,
                                                     @RequestParam("price") String price ) {
        String message = "";
        try {
            product newPro = new product();
            if(file!=null){
                fileService.saveFile(file);
                newPro.setUrlImg(file.getOriginalFilename());
            }

            newPro.setProductName(productName);
            newPro.setPrice(Float.parseFloat(price));

            productRepo.save(newPro);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new RespondMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: ";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new RespondMessage(message));
        }

    }




    @GetMapping(value = "/admin/products/{id}")
    public Optional<product> findById (@PathVariable("id") Integer id){
     Optional <product> product = productRepo.findById(id);
        return  product;
    }





    @PutMapping("/admin/products")
    public ResponseEntity<RespondMessage> editProduct(@RequestParam(value="editfile",required = false) MultipartFile editfile,
                                                     @RequestParam("productName") String productName,
                                                     @RequestParam("price") String price ,
                                                      @RequestParam ("productid") int id) {
        String message = "";
        try {
            product editPro = productRepo.findByProductId(id);
            if (editfile == null){
             editPro.setUrlImg(editPro.getUrlImg());

            }else {
                fileService.saveFile(editfile);
                editPro.setUrlImg(editfile.getOriginalFilename());
            }

            editPro.setProductId(id);
            editPro.setProductName(productName);
            editPro.setPrice(Float.parseFloat(price));

            productRepo.save(editPro);
            message = "Uploaded the file successfully: " + editfile.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new RespondMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " ;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new RespondMessage(message));
        }

    }

    @DeleteMapping(value = "/admin/products/{id}")
    public void deletePro(@PathVariable int id){
        product pro = productRepo.findByProductId(id);
        productRepo.delete(pro);
    }


    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}
