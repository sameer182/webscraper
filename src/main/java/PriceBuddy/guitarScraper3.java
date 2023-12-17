package PriceBuddy;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class GuitarScraper3 {
    private guitarDao dao;

    public GuitarScraper3(guitarDao dao) {
        this.dao = dao;
    }

    public void scrape() throws IOException {
        //Loop for the pages
        for (int page = 1; page <= 20; page++) {

            // Connect to the URL and retrieve the HTML document
            Document doc = Jsoup.connect("https://www.guitar.co.uk/guitars/electric?p=" + page)
                    .userAgent("Google Chrome - Computer Science Student - Webscraping Coursework Project").get();

            Elements mainContainer = doc.select("li.item");

            //Go through each product container
            for (Element productContainer : mainContainer) {
                //Get the product name
                Elements productNameElement = productContainer.select(".product-name");
                //Get the product description
                Elements desGuitar = productContainer.select(".product-description");
                //Get the product price
                Elements priceGuitar = productContainer.select(".regular-price .price, .special-price .price");

                // Extract the brand name from the first word of the product name which is title.
                for (Element product : productNameElement) {
                    String productName = product.select(".product-name").text();
                    String[] words = productName.split("\\s+");
                    if (words.length > 0) {
                        String brand = words[0];
                        String description = desGuitar.text();

                        //Remove all the non-numerical digit
                        String priceString = priceGuitar.text().replaceAll("[^0-9]", "");
                        int price = Integer.parseInt(priceString) / 100;


                        //Get the product picture
                        Elements guitarPic = productContainer.select(".item img");
                        String pic = guitarPic.attr("src");

                        //Get the product URL
                        Elements guitarUrl = productContainer.select(".item a");
                        String url = guitarUrl.attr("href");

                        // Get the product model SKU
                        String model = scrapeModel(url);


                        // Create guitar class with data
                        guitar g3 = new guitar();
                        g3.setName(productName);
                        g3.setBrands(brand);
                        g3.setModel(model);
                        g3.setDescription(description);
                        g3.setPic(pic);

                        // Create comparison class with data
                        comparison g3Comparison = new comparison();
                        g3Comparison.setGuitar(g3);
                        g3Comparison.setPrice(price);
                        g3Comparison.setUrl(url);

                        try {
                            // Save the comparison
                            dao.saveAndMergeGuitar(g3, price, url);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    //Scrape the guitar model SKU from inside the product URL
    String scrapeModel(String url) throws IOException {
        Document productPage = Jsoup.connect(url).get();
        Element modelElement = productPage.select(".product-collateral p").first();
//        System.out.println(skuElement);
        return modelElement != null ? modelElement.text().replace("SKU: ", "") : "";
    }

}
