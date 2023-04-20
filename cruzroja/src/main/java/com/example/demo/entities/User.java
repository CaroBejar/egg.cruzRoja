package com.example.demo.entities;


import com.example.demo.enums.Rol;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    private String email;
    private String password;

    private Boolean alta;
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @OneToOne
    private Image image;

    @Temporal(TemporalType.DATE)
    private Date uploadDate; //fechaCarga

    @Temporal(TemporalType.DATE)
    private Date modificationDate; //fechaModificacion

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }


    public User() {
    }

    public User(String id, String name, String email, String password, Boolean alta, Rol rol, Image image, Date uploadDate, Date modificationDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.alta = alta;
        this.rol = rol;
        this.image = image;
        this.uploadDate = uploadDate;
        this.modificationDate = modificationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }
}
