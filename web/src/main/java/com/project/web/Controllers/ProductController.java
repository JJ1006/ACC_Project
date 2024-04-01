package com.project.web.Controllers;

import com.project.web.Models.Product;
import com.project.web.Models.RequestSchema;
import com.project.web.Service.AutoCompletionTrie;
import com.project.web.Service.InvertedIndexingWithHTMLParser;
import com.project.web.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@RestController
public class ProductController {


	@Value("${resources.path}")
	String resourcesPath;
	@Autowired
	ProductService productService;

	@Value("${word.completion.path}")
	String filePath;

	Map<String,Integer> wordSearched = new TreeMap<>();

	@GetMapping("/home")
	public String getController() {
		return "Hello world";
	}

	@PostMapping("/search")
	public ResponseEntity<Map<String, List<Product>>> searchProduct(@RequestBody RequestSchema product){
		invertedIndexing(product.getProduct());
		calculateWordSearchFrequency(product.getProduct());
//		wordAutoCompletion(product.getProduct());
		ResponseEntity<Map<String, List<Product>>> allProducts = productService.getAllProducts(product.getProduct());
		System.out.println("HTTP COde:"+allProducts.getStatusCode());
		return allProducts;
	}

//	@GetMapping("/invertedindexing")
	public void invertedIndexing(String product){

		InvertedIndexingWithHTMLParser indexer = new InvertedIndexingWithHTMLParser();


		// The names of the subdirectories
		String[] subDirectories = {"amazon", "bestbuy", "visions"};

		try

		{
			// Process each subdirectory in the resources folder
			for (String subDir : subDirectories) {
				String fullPath = Paths.get(resourcesPath, subDir).toString();
				System.out.println("Processing directory: " + fullPath);
				indexer.processHTMLFilesFromDirectory(fullPath);

				// Example search
				String searchTerm = product;
				Set<String> searchResults = indexer.search(searchTerm);


				System.out.println(subDir+" has "+searchResults.size()+" documents containg "+searchTerm+".");
				searchResults.clear();
			}
		} catch(IOException e)
		{
			System.out.println("An error occurred while processing the directories.");
		}
	}

	private void calculateWordSearchFrequency(String product){
		int count = 0;
		if(wordSearched.containsKey(product)){
			count = wordSearched.get(product);
			count++;
		}
		wordSearched.put(product,count);
		System.out.println(product + " has been searched for "+wordSearched.get(product)+" times previously.");
	}


}
