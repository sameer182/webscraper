package PriceBuddy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/** Example code to illustrate web scraping with JSoup */
public class guitarScraper1 {

    /**
     * Constructor
     */
    guitarScraper1() {
        try {
            scrape();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void scrape() throws Exception {
        //Loop for the pages
        for (int page = 1; page <=1; page++) {

            //Download HTML document from website
            Document doc = Jsoup.connect("https://www.guitarguitar.co.uk/guitars/page-" + page + "/?Ordering=1&MinPrice=&MaxPrice=").get();

            //Get all the products on the page
            Elements mainContainer = doc.select(".products");
            Elements guitarSection = mainContainer.select(".product-inner");

            //Get all the products on the current page
            for (int i = 0; i <=39; i++) {
                Elements nameGuitar = guitarSection.get(i).select(".qa-product-list-item-title");
                Elements brandGuitar = guitarSection.get(i).select("strong");
                Elements priceGuitar = guitarSection.get(i).select("span.js-pounds");

                Element productLink = guitarSection.get(i).select(".qa-product-list-item-title").first();

                String priceString = priceGuitar.text().replaceAll("[^0-9]", "");
                int price = Integer.parseInt(priceString);

                System.out.println("Name: " + nameGuitar.text() + "\nBrand: " + brandGuitar.text() + "\nPrice: " + price);
                System.out.println("==========================");

            }
        }
    }

}


