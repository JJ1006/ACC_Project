package com.project.web.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.project.web.Models.Product;
import com.project.web.scrapper.WebCrawler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


@Service
public class ProductService {

    @Autowired
    WebCrawler webCrawler;

    public Map<String, List<Product>> getAllProducts(String itemToSearch) {
        List<Product> amazonPoductsList = searchProductFromAmazon(itemToSearch);
        List<Product> bestBuyProductsList = searchProductFromBestBuy(itemToSearch);
        List<Product> visionsProductsList = searchProductsFromVision(itemToSearch);
        Map<String, List<Product>> allProducts = new HashMap<>();
        allProducts.put("amazon", amazonPoductsList);
        allProducts.put("bestbuy", bestBuyProductsList);
        allProducts.put("visions", visionsProductsList);


        String directoryPath = "C:\\Users\\priya\\Desktop\\MAC Sem1\\ACC\\Project\\ACC_Project-dharmil\\ACC_Project-dharmil\\web\\src\\main\\resources\\StoredData";
        try {
            File file = new File(directoryPath, itemToSearch + ".json");

            ResponseEntity<Map<String, List<Product>>> responseEntity = new ResponseEntity<>(allProducts, HttpStatus.OK); // replace with your ResponseEntity
            // Convert the ResponseEntity to JSON
            ObjectMapper mapper = new ObjectMapper();
//            String json = mapper.writeValueAsString(responseEntity.getBody());
            String json = mapper.writeValueAsString(allProducts);

            // Write the JSON to the file
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
//        return new ResponseEntity<>(allProducts, HttpStatus.OK);
        return allProducts;
    }

    public List<Product> searchProductFromAmazon(String itemToSearch) {
        String url = "https://www.amazon.ca/";
        List<Product> amazonPoductsList = webCrawler.fetchAllProductsFromAmazon(url, itemToSearch);
        return amazonPoductsList;
    }

    public List<Product> searchProductFromBestBuy(String itemToSearch) {
        String url = "https://www.bestbuy.ca/en-ca";
        List<Product> bestBuyProductsList = webCrawler.fetchAllProductsFromBestBuy(url, itemToSearch);
        return bestBuyProductsList;
    }

    public List<Product> searchProductsFromVision(String itemToSearch) {
        String url = "https://www.visions.ca/";
        List<Product> visionsProductsList = webCrawler.fetchAllProductsFromVisions(url, itemToSearch);
        return visionsProductsList;
    }

    public boolean scrolledPage(String searchProduct) {
        String directoryPath = "C:\\Users\\priya\\Desktop\\MAC Sem1\\ACC\\Project\\ACC_Project-dharmil\\ACC_Project-dharmil\\web\\src\\main\\resources\\StoredData";
        File file = new File(directoryPath, searchProduct+".json");
        if (file.exists()) {
                return true;
        } else {
            System.out.println("The file \"" + "\" does not exist in the directory \"" + directoryPath + "\".");
            return false;
        }
    }


    public Map<String, List<Product>> getProductsFromJson(String product) {
        String directoryPath = "C:\\Users\\priya\\Desktop\\MAC Sem1\\ACC\\Project\\ACC_Project-dharmil\\ACC_Project-dharmil\\web\\src\\main\\resources\\StoredData";
        String fileName = product+".json";

        File file = new File(directoryPath, fileName);
        if (file.exists()) {
            try {
                // Read the JSON from the file
                FileReader reader = new FileReader(file);
                Gson gson = new Gson();
                Type type = new TypeToken<Map<String, List<Product>>>(){}.getType();
                Map<String, List<Product>> map = gson.fromJson(reader, type);

                return map;

            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
                e.printStackTrace();
            }
        } else {
            System.out.println("The file \"" + fileName + "\" does not exist in the directory \"" + directoryPath + "\".");
        }
        return null;
    }

}

