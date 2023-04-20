package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.exceptions.MiException;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PortalController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "organization/index.html";
    }

    @RequestMapping("/registrar")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register.html";
    }

    @RequestMapping(value = "/registro", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") User user, @RequestParam String password2, ModelMap model, MultipartFile file) throws MiException {
        try {
            userService.register(file, user, password2);
            model.put("exito", "Usuario registrado");
        } catch (MiException e) {
            model.put("error", e.getMessage());
            return "register.html";
        }
        return "redirect:/inicio";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap model) {
        if (error != null) {
            model.put("error", "Usuario o Contraseña invalidos");
        }
        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        User logged = (User) session.getAttribute("usersession");

        if (logged.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }

        return "init";
    }
}

