package PriceBuddy;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class guitarScraper1 {

    guitarScraper1() {
        try {
            scrape();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void scrape() throws Exception {
        //Loop for the pages
        for (int page = 1; page <= 1; page++) {

            // Connect to the URL and retrieve the HTML document
            Document doc = Jsoup.connect("https://www.guitar.co.uk/guitars/electric?p=" + page).get();

            Elements mainContainer = doc.select("li.item");

            //Go through each product container
            for (Element productContainer : mainContainer) {

                Elements productNameElement = productContainer.select(".product-name");
                Elements desGuitar = productContainer.select(".product-description");
                Elements priceGuitar = productContainer.select(".regular-price .price, .special-price .price");

                // Extract the brand name from the first word of the product name which is title.
                for (Element product : productNameElement) {
                    String productName = product.select(".product-name").text();
                    String[] words = productName.split("\\s+");
                    if (words.length > 0) {
                        String brand = words[0];
                        String description = desGuitar.text();

                        String priceString = priceGuitar.text().replaceAll("[^0-9]", "");
                        int price = Integer.parseInt(priceString) / 100;

                        //Print the details for each product
                        System.out.println("Name :" + productName + "\nBrand :" + brand + "\nDescription :" + description + "\nPrice :" + price);
                        System.out.println("===============================================================================");

                    }
                }
            }
        }
    }
}
//
//          public static void main (String[]args){
//
//            new guitarScraper1();
//        }
//    }
