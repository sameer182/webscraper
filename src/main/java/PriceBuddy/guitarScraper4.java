package PriceBuddy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class guitarScraper4 {

    guitarScraper4() {
        try {
            scrape();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void scrape() throws Exception {

        //Loop for the pages
        for (int page = 1; page <= 1; page++) {

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
                    String guitarName = product.select(".card-title").text();
                    String[] words = guitarName.split("\\s+");
                    if (words.length > 0) {
                        String brand = words[0];

                        //Get the product description
                        Elements guitarDes = productContainer.select(".summary, hide-grid");
                        //Get the product price
                        Elements guitarPrice = productContainer.select(".price--withTax");
                        //Remove all the non-numerical digit
                        String priceString = guitarPrice.text().replaceAll("[^0-9]", "");
                        int price = Integer.parseInt(priceString) / 100;

                        //Get the product picture
                        Elements guitarPic = productContainer.select(".card-img-container img");
                        String pic = guitarPic.attr("src");

                        //Get the product URL
                        Elements guitarUrl = productContainer.select(".card-figure a");
                        String url = guitarUrl.attr("href");


                        System.out.println("Name :" + guitarName + "\nBrand :" + brand + "\nPrice :" + price +
                                "\nDescription :" + guitarDes.text() + "\nPic :" + pic + "\nURL :" + url);
                        System.out.println("=================================================================");
                    }
                }

                }
            }
        }

        public static void main (String[]args){
            new guitarScraper4();

        }
    }