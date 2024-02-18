package com.project.web.Service;

import com.project.web.Models.Product;
import com.project.web.scrapper.WebCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    WebCrawler webCrawler;

    public ResponseEntity<Map<String, List<Product>>> getAllProducts(String itemToSearch){
        List<Product> amazonPoductsList = searchProductFromAmazon(itemToSearch);
        Map<String,List<Product>> allProducts = new HashMap<>();
        allProducts.put("amazon",amazonPoductsList);
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    public List<Product> searchProductFromAmazon(String itemToSearch){
        String url = "https://www.amazon.ca/";
        List<Product> amazonPoductsList = webCrawler.getHtmlDocument(url,itemToSearch);
        return amazonPoductsList;
    }
}
