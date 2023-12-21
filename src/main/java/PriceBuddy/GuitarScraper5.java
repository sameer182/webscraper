package PriceBuddy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class GuitarScraper5 {

        private guitarDao dao;

    public GuitarScraper5(guitarDao dao) {
        this.dao = dao;
    }

    void scrape() throws IOException {
        // Loop for the pages
        for (int page = 1; page <= 20; page++) {
            // Connect to the URL and retrieve the HTML document
            Document doc = Jsoup.connect("https://guitarvillage.co.uk/categories/guitars/electric-guitars/page/" + page)
                    .userAgent("Google Chrome - Computer Science Student - Webscraping Coursework Project").get();

            // Select individual product items
            Elements mainContainer = doc.select(".products .item");

            // Iterate through each product item
            for (Element guitarSection : mainContainer) {
                // Extract and print relevant information (you can modify this part based on your needs)
                String name = guitarSection.select(".woocommerce-loop-product__title").text();

                //Get the product picture
                Elements guitarPic = guitarSection.select("source[type='image/webp']");
                String pic = guitarPic.attr("srcset");
                //Get the product price
                Elements guitarPrice = guitarSection.select(".price span");
                //Prints the first price when there are two
                String priceString1 = guitarPrice.get(0).text();
                //Remove all the non-numerical digits
                String priceString2 = priceString1.replaceAll("[^0-9]", "");
                int price = Integer.parseInt(priceString2) / 100;

                //Get the guitar product's URL
                Elements guitarUrl = guitarSection.select("a");
                String url = guitarUrl.attr("href");

                //Get the guitar details from inside the product URL
                String brand = scrapeBrand(url);
                String description = scrapeDescription(url);
                String model = scrapeModel(url);

                // Create guitar class with data
                guitar g5 = new guitar();
                g5.setName(name);  // From web scraping
                g5.setBrands(brand);  // From web scraping
                g5.setModel(model);
                g5.setDescription(description);
                g5.setPic(pic);

                // Create comparison class with data
                comparison g5Comparison = new comparison();
                g5Comparison.setGuitar(g5);
                g5Comparison.setPrice(price);
                g5Comparison.setUrl(url);

                try {
                    dao.saveAndMergeGuitar(g5, price, url);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    //Scrape the guitar brand from inside the product URL
    String scrapeBrand(String url) throws IOException {
        Document productBrand = Jsoup.connect(url).get();
        Element brandElement = productBrand.select("ul li:contains(Brand)").first();
        if (brandElement != null) {
            // Extract the brand value using the regular expression for any spaces
            return brandElement.text().replaceAll("Brand\\s*:", "");
        } else {
            System.out.println("Brand name not found");
            return "";
        }
    }

    //Scrape the guitar description from inside the product URL
    String scrapeDescription(String url) throws IOException {
        Document productDescription = Jsoup.connect(url).get();
        Element descriptionElement = productDescription.select("p").first();
        if (descriptionElement != null) {
            return descriptionElement.text();
        } else {
            return "";
        }
    }

    //Scrape the guitar model SKU from inside the product URL
    String scrapeModel(String url) throws IOException {
        Document productBrand = Jsoup.connect(url).get();
        Element modelElement = productBrand.select("span.sku").first();
        if (modelElement != null) {
            return modelElement.text().replaceAll("-", "")
                    .replaceAll("_", "").toLowerCase();
        } else {
            return "";
        }
    }

}