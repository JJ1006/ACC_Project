package com.project.web.scrapper;

import com.project.web.Models.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class WebCrawler {

    @Value("${webdriver.chrome.driver.path}")
    String webDriverPath;

    public List<Product> fetchAllProductsFromAmazon(String url, String searchedItem){
        Document document = null;
        String docString = getHTMLDocForAmazonSearchedItem(url,searchedItem);
        document = Jsoup.parse(docString);
        Elements elements = document.select(".puis-card-container");
//        System.out.println(elements);
        List<Product> productList = new ArrayList<Product>();

        for(Element e: elements){
            Product p = new Product();
            String productName = e.select("h2 > a > span").text();
            String productImage =  e.select(" span > a > div > img").attr("src");
            String productUrl = "https://www.amazon.ca/"+e.select("h2 > a.a-link-normal").attr("href");
            String productRatingStars = e.select("i.a-icon-star-small > span.a-icon-alt").text();
            String productRatingCounts = e.select("div > div > div > div > div.a-size-small > span > a > span").text();
            String productPrice = e.select("div >div>a> span > span.a-offscreen").text();
            p.setProductName(productName);
            p.setProductImage(productImage);
            p.setProductUrl(productUrl);
            p.setProductRatingStars(productRatingStars);
            p.setProductRatingCounts(productRatingCounts);
            p.setProductPrice(productPrice);
            productList.add(p);
        }
        return productList;
    }

    public String getHTMLDocForAmazonSearchedItem(String url , String searchedItem){
        System.setProperty("webdriver.chrome.driver", webDriverPath);
//        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36";
         ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");
        WebDriver driver = new ChromeDriver(options);
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys(searchedItem);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.findElement(By.id("nav-search-submit-button")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String doc = driver.getPageSource();
//        driver.close();
        return doc;
    }

}
