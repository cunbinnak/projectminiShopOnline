package com.example.shopingcart.exception;

import com.example.shopingcart.model.RespondMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FileException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<RespondMessage> handleMaxsize(MaxUploadSizeExceededException exc){
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new RespondMessage("Kích thước file quá lớn"));
    }

}
