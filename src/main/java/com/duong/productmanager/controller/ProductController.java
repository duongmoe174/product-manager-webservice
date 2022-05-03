package com.duong.productmanager.controller;

import com.duong.productmanager.model.Category;
import com.duong.productmanager.model.Product;
import com.duong.productmanager.model.ProductForm;
import com.duong.productmanager.service.category.ICategoryService;
import com.duong.productmanager.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@PropertySource("classpath:file_upload.properties")
public class ProductController {
    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @GetMapping("/list")
    public ModelAndView showListProduct() {
        ModelAndView modelAndView = new ModelAndView("product/list");
        modelAndView.addObject("products", productService.findAll());
        return modelAndView;
    }

    @PostMapping("")
    public ResponseEntity<Product> createProduct(@ModelAttribute ProductForm productForm) {
        MultipartFile file = productForm.getImage();
        String fileName = file.getOriginalFilename();
        String name = productForm.getName();
        int number = productForm.getNumber();
        double price = productForm.getPrice();
        Category category = productForm.getCategory();
        Product product = new Product(name, number, price, fileName, category);
        try {
            FileCopyUtils.copy(file.getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        productService.save(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Product>> allProduct() {
        Iterable<Product> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<Iterable<Category>> allCategory() {
        Iterable<Category> categories = categoryService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.remove(id);
        return new ResponseEntity<>(productOptional.get(), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productOptional.get(), HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @ModelAttribute ProductForm productForm) {
        Optional<Product> product = productService.findById(id);
        productForm.setId(product.get().getId());
        if (!product.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            MultipartFile file = productForm.getImage();
            String fileName = file.getOriginalFilename();
            String name = productForm.getName();
            int number = productForm.getNumber();
            double price = productForm.getPrice();
            Category category = productForm.getCategory();
            Product newProduct = new Product(name, number, price, fileName, category);
            try {
                FileCopyUtils.copy(file.getBytes(), new File(fileUpload + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (newProduct.getImage().equals("filename.jpg")) {
                newProduct.setImage(product.get().getImage());
            }
            productService.save(newProduct);
            return new ResponseEntity<>(newProduct, HttpStatus.OK);
        }
    }
}