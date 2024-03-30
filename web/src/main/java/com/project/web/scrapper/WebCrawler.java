package com.project.web.scrapper;

import com.project.web.Models.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
//import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class WebCrawler {

    @Value("${webdriver.chrome.driver.path}")
    String webDriverPath = "C:\\\\Users\\\\admin\\\\Downloads\\\\chromedriver-win64\\\\chromedriver-win64\\\\chromedriver.exe";

    @Value("${chrome.user.agent}")
    String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36";

    @Value("${resources.path}")
    String resourcesPath;
//    boolean nextPageAvailableFlagForVisions = true;

    ChromeOptions options = setChromeDrivers();
    WebDriver driver = new ChromeDriver(options);
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
            p.setProductRegularPrice(productPrice);
            productList.add(p);
        }
        return productList;
    }

    public List<Product> fetchAllProductsFromVisions(String url, String searchedItem){
        List<Product> productList = new ArrayList<Product>();
            Document document = null;

            String docString = getHTMLDocForVisionsSearchedItem(url, searchedItem);
            document = Jsoup.parse(docString);

            Elements elements = document.select(".dvv982c-container-item");


            for (Element e : elements) {
                Product p = new Product();
                String productName = e.select(".dvv982c-description > a ").text();
                String productImage = e.select(" .dvv982c-imgbox > a > img").attr("src");
                String productUrl = url + e.select(".dvv982c-description > a ").attr("href");
                String productRatingStars = e.select(".pr-snippet-rating-decimal").text();
                String productRatingCounts = "0";
                String productSalePrice = e.select(".dvv982c-saleprice").text();
                String productRegularPrice = e.select(".dvv982c-lineout").text();
                p.setProductName(productName);

                p.setProductImage(productImage);
                p.setProductUrl(productUrl);
                p.setProductRatingStars(productRatingStars);
                p.setProductRatingCounts(productRatingCounts);
                p.setProductRegularPrice(productRegularPrice);
                p.setProductSalePrice(productSalePrice);


                productList.add(p);
            }

        return productList;

    }


    public String getHTMLDocForVisionsSearchedItem(String url , String searchedItem){


        driver.get(url);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            driver.findElement(By.id("searchbar-keyword")).sendKeys(searchedItem);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.findElement(By.id("searchbar-magbtn")).click();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//            if(!driver.findElements(By.id("ContentPlaceHolder1_ctrlResultBarPagerUC2_lnkNextpage")).isEmpty()){
//                nextPageAvailableFlagForVisions = true;
//            }else{
//                nextPageAvailableFlagForVisions = false;
//            }

        }catch (NoSuchElementException e){
            System.out.println("No element found");
        }
        String doc = driver.getPageSource();
//        driver.close();
        createHTMLFile("visions",searchedItem,doc);
        return doc;
    }

    public String getHTMLDocForAmazonSearchedItem(String url , String searchedItem){


        driver.get(url);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            driver.findElement(By.id("twotabsearchtextbox")).sendKeys(searchedItem);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.findElement(By.id("nav-search-submit-button")).click();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }catch (NoSuchElementException e){
            System.out.println("No element found");
        }
        String doc = driver.getPageSource();
//        driver.close();
        createHTMLFile("amazon",searchedItem,doc);
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
            p.setProductRegularPrice(productPrice);
            productList.add(p);
        }
        return productList;
    }

    public String getHTMLDocForBestBuySearchedItem(String url, String searchedItem){

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
        createHTMLFile("bestbuy",searchedItem,doc);

        return doc;
    }

    private void createHTMLFile(String origin, String searchedItem, String doc){
        try {
            String dirName = resourcesPath +"\\"+origin;

            String fileName = searchedItem+"_"+origin+".html";
            File dir = new File (dirName);
            File actualFile = new File (dir, fileName);
            FileWriter myWriter = new FileWriter(actualFile, StandardCharsets.UTF_8);
            myWriter.write(doc);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
