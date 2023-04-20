package com.example.demo.services;



import com.example.demo.entities.Image;
import com.example.demo.entities.Organization;
import com.example.demo.entities.User;
import com.example.demo.enums.Rol;
import com.example.demo.exceptions.MiException;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;


    @Transactional
    public void register(MultipartFile file, User user, String password2) throws MiException {
        validate(user.getName(), user.getEmail(), user.getPassword(), password2);

        user.setAlta(true);
        user.setUploadDate(new Date());
        user.setModificationDate(new Date());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRol(Rol.USER);

        Image image = imageService.save(file);

        user.setImage(image);

        userRepository.save(user);
    }

    @Transactional
    public User update(User userNew, String password2, MultipartFile file) throws MiException {

        validate(userNew.getName(), userNew.getEmail(), userNew.getPassword(), password2);

        Optional<User> response = userRepository.findById(userNew.getId());
        if (response.isPresent()) {
            User userOld = response.get();

            userOld.setName(userNew.getName());
            userOld.setEmail(userNew.getEmail());
            userOld.setModificationDate(new Date());
            userOld.setPassword(new BCryptPasswordEncoder().encode(userNew.getPassword()));

            String idImage = null;



            if (userOld.getImage() != null) {
                idImage = userOld.getImage().getId();
            }



            Image image = imageService.update(file, idImage);

            userOld.setImage(image);

            return userRepository.save(userOld);
        }
        throw new MiException("El usuario no pudo ser modificado");
    }

    public User get(String id) {
        return userRepository.findById(id).get();
    }
    @Transactional(readOnly = true)
    public List<User> userList() {

        List<User> users = new ArrayList();

        users = userRepository.findAll();

        return users;
    }

    @Transactional
    public void changeRol(String id) {
        Optional<User> response = userRepository.findById(id);

        if (response.isPresent()) {

            User user = response.get();

            if (user.getRol().equals(Rol.USER)) {

                user.setRol(Rol.ADMIN);

            } else if (user.getRol().equals(Rol.ADMIN)) {
                user.setRol(Rol.USER);
            }
        }
    }

    private void validate(String name, String email, String password, String password2) throws MiException {

        if (name.isEmpty() || name == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacío");
        }
        if (email == null || email.isEmpty() || !email.contains("@") || !email.contains(".")) {
            throw new MiException("Direccion email invalida");
        }
        if (password.isEmpty()) {
            throw new MiException("Ingrese una contraseña");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }

    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if (user != null) {

            List<GrantedAuthority> permisses = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + user.getRol().toString());

            permisses.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usersession", user);

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), permisses);

        } else {
            return null;
        }
    }
    @Transactional
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    public List<User> listAll(){
        return userRepository.findAll();
    }

}
