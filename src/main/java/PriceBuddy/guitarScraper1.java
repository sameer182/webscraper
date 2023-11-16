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

        // Connect to the URL and retrieve the HTML document
        Document doc = Jsoup.connect("https://www.guitar.co.uk/guitars/electric").get();

        Elements mainContainer = doc.select(".category-products");

          //Go through each product container
          for (Element productContainer : mainContainer) {
              Elements guitarSection = productContainer.select(".product-name a[title]");
              Elements desGuitar = productContainer.select(".product-description");

              // Extract the brand name from the first word of the product name which is title.
              for (Element product : guitarSection) {
                  String productName = product.attr("title");
                  String[] words = productName.split("\\s+");
                  if (words.length > 0) {
                      String brand = words[0];
                      String description = desGuitar.text();

                      //Print the details for each product
                      System.out.println("Name :" + productName + "\nBrand :" + brand + "\nDescription :" + description);
                      System.out.println("===============================================================================");
                  }
              }
          }

    }
          public static void main (String[]args){

            new guitarScraper1();
        }
    }
