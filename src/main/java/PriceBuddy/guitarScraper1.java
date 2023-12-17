package PriceBuddy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class GuitarScraper1 {

    private guitarDao dao;

    public GuitarScraper1(guitarDao dao) {
        this.dao = dao;
    }

    void scrape() throws IOException {

        //Loop for the pages
        for (int page = 1; page <= 20; page++) {

            //Connect to the URL and retrieve the HTML document
            Document doc = Jsoup.connect("https://richtonemusic.co.uk/electric-guitars/?page=" + page)
                    .userAgent("Google Chrome - Computer Science Student - Webscraping Coursework Project").get();

            Elements mainContainer = doc.select("li.product");
            //Go through each product
            for (Element productContainer : mainContainer) {
                //Get the product name
                Elements guitarNameElement = productContainer.select(".card-title");

                // Extract the brand name from the first word of the product name which is title.
                for (Element product : guitarNameElement) {
                    //Remove the unnecessary name after hyphen
                    String name = product.select(".card-title").text().split("-")[0].trim();
                    String[] words = name.split("\\s+");
                    if (words.length > 0) {
                        String brand = words[0];

                        //Get the product price
                        Elements guitarPrice = productContainer.select(".price--withTax");
                        //Remove all the non-numerical digit
                        String priceString = guitarPrice.text().replaceAll("[^0-9]", "");
                        int price = Integer.parseInt(priceString) / 100;

                        //Get the product description
                        Elements guitarDes = productContainer.select(".summary, hide-grid");

                        //Get the product picture
                        Elements guitarPic = productContainer.select(".card-img-container img");
                        String pic = guitarPic.attr("src");

                        //Get the product URL
                        Elements guitarUrl = productContainer.select(".card-figure a");
                        String url = guitarUrl.attr("href");

                        // Get the product model SKU
                        String model = scrapeModel(url);
                        System.out.println(model);


                        // Create guitar class with data
                        guitar g1 = new guitar();
                        g1.setName(name);
                        g1.setBrands(brand);
                        g1.setModel(model);
                        g1.setDescription(guitarDes.text());
                        g1.setPic(pic);

                        // Create comparison class with data
                        comparison g1Comparison = new comparison();
                        g1Comparison.setGuitar(g1);
                        g1Comparison.setPrice(price);
                        g1Comparison.setUrl(url);

                        try {
                            // Save data with checking for duplicates
                            dao.saveAndMergeGuitar(g1, price, url);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

            }
        }
    }
    //Scrape the guitar model from inside the product URL
    String scrapeModel(String url) throws IOException {
        Document productPage = Jsoup.connect(url).get();
        Element modelElement = productPage.select("dd.productView-info-value").first();
        // Remove dashes and convert to lowercase
        if (modelElement != null) {
            String modelName = modelElement.text().replace("SKU: ", "")
                    .replaceAll("-", "").toLowerCase();

            // Check if the first three characters are "gib" and trim them
            if (modelName.length() >= 3 && modelName.substring(0, 3).equals("gib")) {
                modelName = modelName.substring(3);
            }
            // Check if the first four characters are "fend" and trim them
            if (modelName.length() >= 4 && modelName.substring(0, 4).equals("fend")) {
                modelName = modelName.substring(4);
            }
            if (modelName.length() >= 3 && modelName.substring(0, 3).equals("epi")) {
                modelName = modelName.substring(3);
            }
            if (modelName.length() >= 6 && modelName.substring(0, 6).equals("squier")) {
                modelName = modelName.substring(6);
            }
            if (modelName.length() >= 4 && modelName.substring(0, 4).equals("jack")) {
                modelName = modelName.substring(4);
            }
            if (modelName.length() >= 7 && modelName.substring(0, 7).equals("gretsch")) {
                modelName = modelName.substring(7);
            }
            if (modelName.length() >= 4 && modelName.substring(0, 4).equals("char")) {
                modelName = modelName.substring(4);
            }


            return modelName;
        } else {
            return "";
        }
    }

}

