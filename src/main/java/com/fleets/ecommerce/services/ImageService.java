package com.fleets.ecommerce.services;

import com.fleets.ecommerce.entities.Image;
import com.fleets.ecommerce.entities.Product;
import com.fleets.ecommerce.exceptions.ProductNotFoundException;
import com.fleets.ecommerce.repositories.ImageRepository;
import com.fleets.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;

    private byte[] validateImage(MultipartFile image) throws IOException {
        String mimeType = image.getContentType().split(".+?/(?=[^/]+$)")[1];
        if (mimeType.equals("png")) {
            if(image.getSize() < 3000000) {
                return image.getBytes();
            }else {
                throw new IOException("Invalid size for image");
            }
        }else {
            throw new IOException("Invalid extension for image");
        }
    }

    public Image uploadImageForProduct(Long productId, MultipartFile image) throws IOException {
        Product product = productRepository.getById(productId);
        var productImage = new Image();
        if(product.getImage() != null){
            productImage = product.getImage();
        }
            productImage.setData(validateImage(image));
            imageRepository.save(productImage);
            product.setImage(productImage);
            productRepository.save(product);
            return productImage;
    }

    public Image getById(Long id){
        return imageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("There is no image with id " + id));
    }
}
