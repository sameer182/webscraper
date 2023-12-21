package PriceBuddy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


/** Example code to illustrate web scraping with JSoup */
public class GuitarScraper2 {

    /**
     * Constructor
     */

    private guitarDao dao;

    public GuitarScraper2(guitarDao dao) {
        this.dao = dao;
    }


    void scrape() throws IOException {
        //Loop for the pages
        for (int page = 1; page <= 20; page++) {

            //Download HTML document from website
            Document doc = Jsoup.connect("https://www.guitarguitar.co.uk/guitars/electric/page-" + page + "/?Ordering=1&MinPrice=&MaxPrice=")
                    .userAgent("Google Chrome - Computer Science Student - Webscraping Coursework Project").get();

            //Get all the products on the page
//            Elements mainContainer = doc.select(".products");
            Elements mainContainer = doc.select(".product-list-products");

            //Go through each product
            for (Element productSection : mainContainer.select(".product")) {

                Elements guitarSection = productSection.select(".product-inner");

                //Get the product name
                Elements guitarName = guitarSection.select(".qa-product-list-item-title");

                //Get the guitar brand
                Elements brandGuitar = guitarSection.select("strong");

                //Get the guitar price
                Elements priceGuitar = guitarSection.select("span.js-pounds");
                //Remove all the non-numerical digit
                String priceString = priceGuitar.text().replaceAll("[^0-9]", "");
                int price = Integer.parseInt(priceString);

                //Get the product pic
                Elements guitarPic = guitarSection.select(".js-picture-lazy img");
                String pic = guitarPic.attr("data-src");

                //Get the guitar link and manually adding the base URL
                Elements productLinks = productSection.select("a");
                String url = "https://www.guitarguitar.co.uk" + productLinks.attr("href");

                //Get the guitar model from inside the product URL§
                String model = scrapeModel(url);


                //Get the guitar description from inside the product URL
                String description = scrapeDescription(url);

                // Create guitar class with data
                guitar g2 = new guitar();
                g2.setName(guitarName.text());
                g2.setBrands(brandGuitar.text());
                g2.setModel(model);
                g2.setDescription(description);
                g2.setPic(pic);

                // Create comparison class with data
                comparison g2Comparison = new comparison();
                g2Comparison.setGuitar(g2);
                g2Comparison.setPrice(price);
                g2Comparison.setUrl(url);

                try {
                    // Save the comparison
                    dao.saveAndMergeGuitar(g2, price, url);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }
    }

    //Scrape the guitar model from inside the product URL
    String scrapeModel(String url) throws IOException {
        Document productPage = Jsoup.connect(url).get();
        Element modelElement = productPage.select(".description-full > p strong").first();
        return modelElement != null ? modelElement.text().replace("Manufacturer's ID:", "").replaceAll("-", "")
                .replaceAll("_", "").toLowerCase() : "No model number.";

    }

    //Scrape the guitar description from inside the product URL
    String scrapeDescription(String url) throws IOException {
        Document productPage = Jsoup.connect(url).get();
        Element descriptionElement = productPage.select(".original-description p").first();
        return descriptionElement != null ? descriptionElement.text() : "No Description.";
    }



}


