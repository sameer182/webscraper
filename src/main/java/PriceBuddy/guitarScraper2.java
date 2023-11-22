package PriceBuddy;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class guitarScraper2 {

    guitarScraper2() {
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
//                        Elements guitarPic = productContainer.select(".product-image");
//                        String pic = guitarPic.attr("src");
//                        System.out.println(pic);

                        //Get the product URL
//                        Elements guitarUrl = productContainer.select(".item.first a");
//                        String url = guitarUrl.attr("href");
//                        System.out.println(url);

                        //Print the details for each product
                        System.out.println("Name :" + productName + "\nBrand :" + brand + "\nPrice :" + price + "\nDescription :" + description);
                        System.out.println("===============================================================================");

                    }
                }
            }
        }
    }

          public static void main (String[]args){

            new guitarScraper2();
        }
    }
