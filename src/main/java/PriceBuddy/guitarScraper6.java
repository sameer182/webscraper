package PriceBuddy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class guitarScraper6 {

    guitarScraper6() {
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
            Document doc = Jsoup.connect("https://www.bax-shop.co.uk/electric-guitars/electric-guitars?p=" + page)
                    .userAgent("Google Chrome - Computer Science Student - Webscraping Coursework Project").get();
            System.out.println(doc);


        }
    }



    public static void main (String[] args){
    new guitarScraper6();

}
}