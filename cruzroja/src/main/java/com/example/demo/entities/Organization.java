package com.example.demo.entities;

import com.example.demo.enums.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Organization { //organizacion
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Type type;

    private String email;

    private String location;

    private Long phone;

    private Boolean alta;

    @Temporal(TemporalType.DATE)

    private Date uploadDate; //fechaCarga;

    @Temporal(TemporalType.DATE)

    private Date modificationDate; //fechaModificacion;

    @OneToOne
    private User uploadUser;

    @OneToOne
    private User modifyUser;
    public Organization() {
    }

    public Organization(Long id, String name, String email, String location, Long phone, Boolean alta, Date uploadDate, Date modificationDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.location = location;
        this.phone = phone;
        this.alta = alta;
        this.uploadDate = uploadDate;
        this.modificationDate = modificationDate;
    }

    public User getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(User uploadUser) {
        this.uploadUser = uploadUser;
    }

    public User getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(User modifyUser) {
        this.modifyUser = modifyUser;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
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

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=' " + type + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", phone=" + phone +
                ", alta=" + alta +
                ", uploadDate=" + uploadDate +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
