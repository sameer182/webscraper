package PriceBuddy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class    guitarScraper3 {

    guitarDao dao;

    guitarScraper3() {
        dao = new guitarDao();
        dao.init();
    }

    void scrape() throws Exception {

        //Loop for the pages
        for (int page = 1; page <= 1; page++) {

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
                int price = Integer.parseInt(priceString2) / 100;

                //Get the product description
                Elements guitarDes = productContainer.select(".clamp.teaser");
                String description = guitarDes.text();
                //Checks if the description field is empty or not
                if(description.isEmpty()) {
                    description = "No description.";
                } else {
                   description = guitarDes.text();
                }

                //Get the product picture
                Elements guitarPic = productContainer.select(".product--image");
                String pic = guitarPic.attr("src");

                //Get the product URL
                Elements guitarUrl = productContainer.select(".product-card");
                String url = guitarUrl.attr("href");


//                System.out.println("Name :" + guitarName.text() + "\nBrand :" + brandName + "\nPrice :" + price +
//                        "\nDescription :" + guitarDes.text() + "\nPic :" + pic + "\nURL :" + url);
//                System.out.println("================================================================");

                //Create guitar class with data
                guitar g3 = new guitar();
                g3.setName(guitarName.text());//From web scraping
                g3.setBrands(brandName);//From web scraping
                g3.setDescription(description);//From web scraping

                try {
                    //Save data without checking for duplicates
                    dao.simpleSave(g3);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

//public static void main (String[] args){
//
//    guitarScraper3 scraper = new guitarScraper3();
//    try {
//        scraper.scrape();
//    } catch (Exception e) {
//        throw new RuntimeException(e);
//    }
//}
}
