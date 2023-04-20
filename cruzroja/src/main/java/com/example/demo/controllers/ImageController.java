package com.example.demo.controllers;


import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
@RequestMapping("/imagen")
public class ImageController {

    @Autowired
    UserService userService;

    @RequestMapping("/perfil/{id}")
    public ResponseEntity<byte[]> userImage(@PathVariable String id){
        User user = userService.get(id);

        byte[] image = user.getImage().getContents();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);


        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}
