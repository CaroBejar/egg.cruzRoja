package com.example.demo.controllers;


import com.example.demo.entities.Organization;
import com.example.demo.entities.User;
import com.example.demo.services.OrganizationService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrganizationService organizationService;

    @RequestMapping("/dashboard")
    public String administrationPanel(Model model){
        List<User> users = userService.listAll();
        model.addAttribute("users", users);

        List<Organization> organizations = organizationService.listAll();
        model.addAttribute("organizations", organizations);

        return "user/dashboard.html";
    }

    @GetMapping("/modificarRol/{id}")
    public String upLoadRol(@PathVariable String id) {
        userService.changeRol(id);

        return "vista.html";
    }
}
