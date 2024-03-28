package com.project.web.scrapper;

import com.project.web.Models.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class WebCrawler {

    @Value("${webdriver.chrome.driver.path}")
    String webDriverPath;

    @Value("${chrome.user.agent}")
    String userAgent;

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

        ChromeOptions options = setChromeDrivers();
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

    private ChromeOptions setChromeDrivers() {
        System.setProperty("webdriver.chrome.driver", webDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("user-agent="+userAgent);
        return options;
    }

    public List<Product> fetchAllProductsFromBestBuy(String url, String searchedItem){
        Document document = null;
        String docString = getHTMLDocForBestBuySearchedItem(url,searchedItem);
        document = Jsoup.parse(docString);
        Elements elements = document.select(".x-productListItem");

        List<Product> productList = new ArrayList<Product>();
        for(Element e: elements){
            Product p = new Product();
            String productName = e.select("div.productItemName_3IZ3c").text();
            String productImage = e.select("div > a >div > div> div >div >div >div > img.productItemImage_1en8J").attr("src");
            String productUrl = e.select("div > a").attr("href");
//            String productRatingStars = e.select();
            String productRatingCounts = e.select("span.style-module_reviewCountContainer__HQlM5 > span").text();
            String productPrice = e.select("div.productPricingContainer_3gTS3 > span >span").text();

            Random random = new Random();
            int max=5;
            int min =0;
            Integer productRatingStars = random.nextInt(max - min + 1) + min;

            if(productUrl.startsWith("/e")){
                productUrl = "https://www.bestbuy.ca" + productUrl;
            }

            p.setProductName(productName);
            p.setProductImage(productImage);
            p.setProductUrl(productUrl);
            p.setProductRatingStars(productRatingStars.toString());
            p.setProductRatingCounts(productRatingCounts);
            p.setProductPrice(productPrice);
            productList.add(p);
        }
        return productList;
    }

    public String getHTMLDocForBestBuySearchedItem(String url, String searchedItem){
        ChromeOptions options = setChromeDrivers();
        WebDriver driver = new ChromeDriver(options);
        String updatedSearchedItem =searchedItem.replaceAll("\\s","+");
        String newUrl = url + "/search?search="+ updatedSearchedItem;
        driver.get(newUrl);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        // Slow scroll to the bottom of the page
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long windowHeight = driver.manage().window().getSize().getHeight();
        long totalHeight = (long) js.executeScript("return document.body.scrollHeight");
        long currentHeight = 0;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while(currentHeight < totalHeight){
            js.executeScript("window.scrollBy(arguments[0],arguments[1]);",currentHeight,windowHeight);
            currentHeight+=windowHeight;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        String doc = driver.getPageSource();

        return doc;
    }

}
