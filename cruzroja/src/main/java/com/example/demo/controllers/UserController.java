package com.example.demo.controllers;

import com.example.demo.entities.Organization;
import com.example.demo.entities.User;
import com.example.demo.exceptions.MiException;
import com.example.demo.services.OrganizationService;
import com.example.demo.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/listar")
    public String listUser(Model model) {
        List<User> users = userService.listAll();
        model.addAttribute("users", users);

        return "user/user_list.html";
    }

    @RequestMapping("/modificar/{id}")
    public String uploadUser(@PathVariable(name = "id") String id, ModelMap model){
        model.put("user", userService.get(id));

        return "user/user_update";
    }

    @RequestMapping(value = "/modificar/{id}", method = RequestMethod.POST)
    public String uploadUser(@PathVariable(name = "id") String id, ModelMap model, @ModelAttribute("user") User user, @RequestParam String password2, MultipartFile file, HttpSession session) throws MiException{
        try {
            User logged = (User) session.getAttribute("usersession");

            if (logged.getRol().toString().equals("ADMIN")) {
                userService.update(user, password2, file);
                return "redirect:/admin/dashboard";
            }

            userService.update(user, password2, file);
        }catch (MiException ex){
            model.put("error", ex.getMessage());
            return "user/user_update";
        }
        return "redirect:../listar";
    }

    @RequestMapping("/eliminar/{id}")
    public String deleteUser(@PathVariable String id, HttpSession session) {
        User logged = (User) session.getAttribute("usersession");

        if (logged.getRol().toString().equals("ADMIN")) {
            userService.delete(id);
            return "redirect:/admin/dashboard";
        }

        userService.delete(id);
        return "redirect:../listar";
    }
}
