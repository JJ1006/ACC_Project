package com.project.web.Controllers;

import com.project.web.Models.Product;
import com.project.web.Models.RequestSchema;
import com.project.web.Service.InvertedIndexingWithHTMLParser;
import com.project.web.Service.ProductService;
import com.project.web.Service.WordCompletion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
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
		String finalProduct = wordCompletion(product.getProduct());
		ResponseEntity<Map<String, List<Product>>> allProducts = productService.getAllProducts(finalProduct);
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


				System.out.println(subDir+" has "+searchResults.size()+" documents containing "+searchTerm+".");
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

	public String wordCompletion(String searchPrefix){
		WordCompletion productTrie = new WordCompletion();
		String finalProduct=searchPrefix;
		// Prepare to read input from the user
		Scanner userInputScanner = new Scanner(System.in);

		// Attempt to read product names from the specified file
		try (BufferedReader productReader = new BufferedReader(new FileReader(filePath))) {
			String productName; // Variable to hold each read line (product name)
			// Read each product name line by line until the end of the file
			while ((productName = productReader.readLine()) != null) {
				// Insert the trimmed product name into the trie for future searching
				productTrie.insertWord(productName.trim());
			}
		} catch (IOException ioException) {
			// Print an error message if there was an issue reading the file
			System.err.println("Error reading from file: " + ioException.getMessage());
			ioException.printStackTrace();
		}



		// Find all completions in the trie that match the given prefix
		List<String> productCompletions = productTrie.findCompletionsForPrefix(searchPrefix);

		// Check if there are any completions for the provided prefix
		if (!productCompletions.isEmpty()) {
			System.out.println("Completions for \"" + searchPrefix + "\":");
			// Iterate over the completions and print each one with an index
			for (int i = 0; i < productCompletions.size(); i++) {
				System.out.println((i + 1) + ". " + productCompletions.get(i));
			}
			// Ask the user to select one of the completions by its index
			System.out.println("Enter the number of the product you were looking for: ");
			int userChoice =0 ;
			try{
				if(userInputScanner.hasNextInt()) {
					userChoice = userInputScanner.nextInt();
				}
			}catch (Exception e){
				e.printStackTrace();
				System.out.println("Exception while reading input!");
			}
			// Validate the user's choice and respond accordingly
			if (userChoice > 0 && userChoice <= productCompletions.size()) {
				// User's choice is valid; print the selected product
				finalProduct = productCompletions.get(userChoice - 1);
				System.out.println("You selected: "+finalProduct);
			} else {
				// User's choice is invalid; print an error message
				System.out.println("Invalid choice. Exiting.");
			}
		} else {
			// No completions were found for the provided prefix
			System.out.println("No completions found for: " + searchPrefix);
		}

		// Close the scanner to prevent resource leaks
//		userInputScanner.close();
		return finalProduct;
	}



}
