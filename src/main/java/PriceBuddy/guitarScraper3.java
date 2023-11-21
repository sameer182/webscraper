package PriceBuddy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class guitarScraper3 {

    guitarScraper3() {
        try {
            scrape();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void scrape() throws Exception {

        //Loop for the pages
        for (int page = 1; page <= 2; page++) {

            //Connect to the URL and retrieve the HTML document
            Document doc = Jsoup.connect("https://www.gak.co.uk/en/electric-guitars?page=" + page + "&stockonly=true&min=99&max=10349")
                    .userAgent("Google Chrome - Computer Science Student - Webscraping Coursework Project").get();

            Elements mainContainer = doc.select(".product-card");

            //Go through each products
            for (Element productContainer : mainContainer) {

                Elements guitarName = productContainer.select(".clamp.title");
                // Extract the brand name
                String brandName = productContainer.attr("data-brand");
                Elements guitarDes = productContainer.select(".clamp.teaser");
                System.out.println("Name :" + guitarName.text() + "\nBrand :" + brandName + "\nDescription :" + guitarDes.text());
                System.out.println("===========================================");


            }
        }


}
public static void main (String[] args){

    new guitarScraper3();

}
}