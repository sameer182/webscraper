package PriceBuddy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class guitarScraper2 {

    guitarScraper2() {
        try {
            scrape();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void scrape() throws Exception {

     Document doc = Jsoup.connect("https://www.gak.co.uk/en/electric-guitars")
             .userAgent("Google Chrome - Computer Science Student - Webscraping Coursework Project").get();

     Elements mainContainer = doc.select(".product-card");

     for (Element productContainer : mainContainer) {

         Elements guitarName = productContainer.select(".product-card .clamp.title");
         Elements guitarDes = productContainer.select(".product-card .clamp.teaser");
         System.out.println("Name :" + guitarName.text() + "\nDescription :" + guitarDes.text());
         System.out.println("===========================================");

     }



}
public static void main (String[] args){

    new guitarScraper2();

}
}