package com.example.demo.services;

import com.example.demo.entities.Organization;
import com.example.demo.entities.User;
import com.example.demo.enums.Type;
import com.example.demo.exceptions.MiException;
import com.example.demo.repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Transactional
    public void createOrganization(Organization organization, User user) throws MiException {
        validate(organization.getName(),organization.getEmail(),organization.getLocation(),organization.getPhone(), organization.getType());

        organization.setUploadUser(user);
        organization.setModifyUser(user);
        organization.setAlta(true);
        organization.setUploadDate(new Date());
        organization.setModificationDate(new Date());

        organizationRepository.save(organization);
    }

    @Transactional
    public Organization modifyOrganization(Organization organization, User user) throws MiException {
        validate(organization.getName(),organization.getEmail(),organization.getLocation(),organization.getPhone(), organization.getType());

        Optional<Organization> answer = organizationRepository.findById(organization.getId());
        if(answer.isPresent()){
            Organization organizationUpd= answer.get();

            organizationUpd.setModifyUser(user);
            organizationUpd.setModificationDate(new Date());
            organizationUpd.setType(organization.getType());
            organizationUpd.setEmail(organization.getEmail());
            organizationUpd.setName(organization.getName());
            organizationUpd.setLocation(organization.getLocation());
            organizationUpd.setPhone(organization.getPhone());

            return organizationRepository.save(organizationUpd);
        }
        throw new MiException("No se ha podido modificar la organizacion");
    }

    public List<Organization> listAll() {
        return organizationRepository.findAll();
    }

    public Organization get(Long id){
        return organizationRepository.findById(id).get();
    }

    public List<Organization> listByUser(String id){
        System.out.println(id);
        return organizationRepository.listByUser(id);
    }

    @Transactional
    public void delete(Long id) {
        organizationRepository.deleteById(id);
    }


    public void validate(String name, String email, String location, Long phone, Type type) throws MiException {
        if(name == null || name.isEmpty()){
            throw new MiException("El nombre no puede estar vacio");
        }
        if(email == null || email.isEmpty() || !email.contains("@") || !email.contains(".")){
            throw new MiException("Email invalido");
        }
        if(location == null || location.isEmpty()){
            throw new MiException("Seleccione una ubicacion");
        }
        if(phone == null){
            throw new MiException("Debe ingresar un Telefono");
        }
        if(type == null ){
            throw new MiException("Seleccione un tipo de Organizacion");
        }
    }
}
