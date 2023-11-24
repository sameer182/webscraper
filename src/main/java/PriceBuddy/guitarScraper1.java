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

            //Go through each product
            for (Element guitarSection : mainContainer.select(".product-inner")){
                //Get the product name
                Elements guitarName = guitarSection.select(".qa-product-list-item-title");
                //Get the guitar brand
                Elements brandGuitar = guitarSection.select("strong");

                //Get the guitar price
                Elements priceGuitar = guitarSection.select("span.js-pounds");
                //Remove all the non-numerical digit
                String priceString = priceGuitar.text().replaceAll("[^0-9]", "");
                int price = Integer.parseInt(priceString);

                //Get the product description
//                Elements guitarPic = mainContainer.select(".js-picture-lazy img");
//                String pic = guitarPic.attr("data-src");
//                System.out.println(pic);

                System.out.println("Name: " + guitarName.text() + "\nBrand: " + brandGuitar.text() + "\nPrice: " + price);
                System.out.println("==========================");

            }

        }
    }

}


