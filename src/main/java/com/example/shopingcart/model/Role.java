package com.example.shopingcart.model;


import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "rolename")
    private String roleName;

    public Role() {
    }

    public Role(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
