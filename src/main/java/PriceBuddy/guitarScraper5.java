package PriceBuddy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.charset.StandardCharsets;


public class guitarScraper5 {

    guitarScraper5() {
        try {
            scrape();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void scrape() throws Exception {

        //Loop for the pages
        for (int page = 1; page <= 1; page++) {

            //Connect to the URL and retrieve the HTML document
            Document doc = Jsoup.connect("https://www.peachguitars.com/guitars/electric-guitars/?0_0=0&p=" + page)
                    .userAgent("Google Chrome - Computer Science Student - Webscraping Coursework Project").get();

            Elements mainContainer = doc.select("#prod_container_1");
            Elements guitarSection = mainContainer.select(".product--title");
//            System.out.println(mainContainer);
            //Go through each product
            for (Element productContainer : guitarSection) {
                //Get the product name
                String guitarName = productContainer.attr("title");

                //Get the product price
//                Elements guitarPrice = productContainer.select(".product-price-web");
//                System.out.println(guitarPrice);

                //Get the product description if it exists else print msg
                Elements guitarDes = productContainer.select(".product--title__sub");
//                System.out.println(guitarDes.isEmpty() ? "No description." : guitarDes.text());
//                Elements guitarPic = productContainer.select(".product--image img");
//                String pic = guitarPic.attr("src");
//                System.out.println(pic);


                System.out.println("Name :" + guitarName + "\nDescription :" + (guitarDes.isEmpty() ?
                                   "No description." :  guitarDes.text()));
                System.out.println("===============================================");

            }
        }
    }



public static void main (String[] args){
   new guitarScraper5();
}
}