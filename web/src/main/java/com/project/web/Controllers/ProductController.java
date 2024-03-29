package com.project.web.Controllers;

import com.project.web.Models.Product;
import com.project.web.Models.RequestSchema;
import com.project.web.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

	@Autowired
	ProductService productService;
	@GetMapping("/home")
	public String getController() {
		return "Hello world";
	}

	@PostMapping("/search")
	public ResponseEntity<Map<String, List<Product>>> searchProduct(@RequestBody RequestSchema product){


		if (productService.scrolledPage(product.getProduct())==true){
//			product is already searched , fetch value from file
		Map<String, List<Product>> allProducts = productService.getProductsFromJson(product.getProduct());


//			System.out.println("++++++++++++++++++found\n  "+ allProducts);
			return new ResponseEntity<>(allProducts, HttpStatus.OK);
		}
		else {
//			ResponseEntity<Map<String, List<Product>>> allProducts = productService.getAllProducts(product.getProduct());
			//directly returns allProducts

			Map<String, List<Product>> allProducts = productService.getAllProducts(product.getProduct());
			return new ResponseEntity<>(allProducts, HttpStatus.OK);

		}

	}
}
