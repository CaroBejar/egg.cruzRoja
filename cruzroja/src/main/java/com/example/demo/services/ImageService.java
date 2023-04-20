package com.example.demo.services;

import com.example.demo.entities.Image;
import com.example.demo.exceptions.MiException;
import com.example.demo.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public Image save(MultipartFile file) throws MiException{
        if (file != null) {
            try {

                Image image = new Image();

                image.setMime(file.getContentType());

                image.setName(file.getName());

                image.setContents(file.getBytes());

                return imageRepository.save(image);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public Image update(MultipartFile file, String idImage) throws MiException {
        if (file != null) {
            try {

                Image image = new Image();

                if (idImage != null) {
                    Optional<Image> response = imageRepository.findById(idImage);

                    if (response.isPresent()) {
                        System.out.println(idImage);
                        image = response.get();
                    }
                }

                image.setMime(file.getContentType());

                image.setName(file.getName());

                image.setContents(file.getBytes());

                return imageRepository.save(image);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;

    }

    @Transactional(readOnly = true)
    public List<Image> ListAll() {
        return imageRepository.findAll();
    }

}
