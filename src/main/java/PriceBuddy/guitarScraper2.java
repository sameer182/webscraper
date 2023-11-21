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

     Elements mainContainer = doc.select(".product-listing-container");
        System.out.println(mainContainer);


}
public static void main (String[] args){

    new guitarScraper2();

}
}