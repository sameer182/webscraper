package PriceBuddy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class guitarScraper3 {

    guitarScraper3() {
        try {
            scrape();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void scrape() throws Exception {

        //Loop for the pages
        for (int page = 1; page <= 2; page++) {

            //Connect to the URL and retrieve the HTML document
            Document doc = Jsoup.connect("https://www.gak.co.uk/en/electric-guitars?page=" + page + "&stockonly=true&min=99&max=10349")
                    .userAgent("Google Chrome - Computer Science Student - Webscraping Coursework Project").get();

            Elements mainContainer = doc.select(".product-card");

            //Go through each product
            for (Element productContainer : mainContainer) {
                //Get the product name
                Elements guitarName = productContainer.select(".clamp.title");
                //Extract the brand name
                String brandName = productContainer.attr("data-brand");

                //Get the product price
                Elements guitarPrice = productContainer.select(".bold .d-price");
                //Prints the first price when there are two
                String priceString1 = guitarPrice.first().text();
                //Remove all the non-numerical digits
                String priceString2 = priceString1.replaceAll("[^0-9]", "");
                int price = Integer.parseInt(priceString2)/100;

                //Get the product description
                Elements guitarDes = productContainer.select(".clamp.teaser");

                //Get the product picture
                Elements guitarPic = productContainer.select(".product--image");
                String pic = guitarPic.attr("src");

                //Get the product URL
                Elements guitarUrl = productContainer.select(".product-card");
                String url = guitarUrl.attr("href");


                System.out.println("Name :" + guitarName.text() + "\nBrand :" + brandName + "\nPrice :" + price +
                        "\nDescription :" + guitarDes.text() + "\nPic :" + pic + "\nURL :" + url);
                System.out.println("================================================================");


            }
        }


}
public static void main (String[] args){

    new guitarScraper3();

}
}