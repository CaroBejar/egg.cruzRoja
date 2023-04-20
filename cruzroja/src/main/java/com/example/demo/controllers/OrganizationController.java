package com.example.demo.controllers;


import com.example.demo.entities.Organization;
import com.example.demo.entities.User;
import com.example.demo.enums.Type;
import com.example.demo.exceptions.MiException;
import com.example.demo.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


@Controller
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
@RequestMapping("/organizacion")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @RequestMapping("/registrar")
    public String showOrganizationFom(Model model) {
        Organization organization = new Organization();
        model.addAttribute("organization", organization);

        return "organization/organization_registration";
    }

    @RequestMapping(value = "/registro",method = RequestMethod.POST)
    public String saveOrganization(@ModelAttribute("organization") Organization organization, HttpSession session, ModelMap model) throws MiException{
        try {
            User logged = (User) session.getAttribute("usersession");
            organizationService.createOrganization(organization, logged);
            model.put("exito", "El Refugio fue cargado correctamente!");
        } catch (MiException e) {
            model.put("error", e.getMessage());
            return "organization/organization_registration";
        }
        return "redirect:../listar";
    }

    @RequestMapping("/listar")
    public String listOrganizations(Model model, HttpSession session) {
        User logged = (User) session.getAttribute("usersession");
        List<Organization> organizationlist = organizationService.listByUser(logged.getId());
        model.addAttribute("organizationlist", organizationlist);
        return "organization/organization_list";
    }


    @RequestMapping("/modificar/{id}")
    public String modifyOrganization(@PathVariable(name = "id") Long id, ModelMap model){
        model.put("organization", organizationService.get(id));

        return "organization/organization_modification";
    }

    @RequestMapping(value = "/modificar/{id}", method = RequestMethod.POST)
    public String modifyOrganization(@PathVariable(name = "id") Long id, ModelMap model, @ModelAttribute("organization") Organization organization, HttpSession session){
        try {
            User logged = (User) session.getAttribute("usersession");

            if (logged.getRol().toString().equals("ADMIN")) {

                Organization org = organizationService.modifyOrganization(organization, logged);
                return "redirect:/admin/dashboard";
            }

            Organization org = organizationService.modifyOrganization(organization, logged);

        }catch (MiException ex){
            model.put("error", ex.getMessage());
            return "organization/organization_modification";
        }
        return "redirect:../listar";
    }


    @RequestMapping("/eliminar/{id}")
    public String deleteOrganization(@PathVariable(name = "id") Long id, HttpSession session) {
        User logged = (User) session.getAttribute("usersession");

        if (logged.getRol().toString().equals("ADMIN")) {
            organizationService.delete(id);
            return "redirect:/admin/dashboard";
        }

        organizationService.delete(id);
        return "redirect:../listar";
    }

}
