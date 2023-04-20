package com.example.demo.controllers;


import com.example.demo.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/email/send")
    public ResponseEntity<?> sendEmail() {
        emailService.sendEmail();

        return new ResponseEntity("Correo enviado con Exito", HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/email/send_html")
    public ResponseEntity<?> sendEmailTemplate() {
        emailService.sendEmailTemplate();

        return new ResponseEntity("Correo con plantilla enviado con Exito", HttpStatus.OK);
    }
}
