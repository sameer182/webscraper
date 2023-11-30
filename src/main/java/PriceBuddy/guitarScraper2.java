package PriceBuddy;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class guitarScraper2 {

    guitarDao dao;

    guitarScraper2() {
        dao = new guitarDao();
        dao.init();
    }

    public void scrape() throws Exception {
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
                        Elements guitarPic = productContainer.select(".item img");
                        String pic = guitarPic.attr("src");

                        //Get the product URL
                        Elements guitarUrl = productContainer.select(".item a");
                        String url = guitarUrl.attr("href");


//                        //Print the details for each product
//                        System.out.println("Name :" + productName + "\nBrand :" + brand + "\nPrice :" + price + "\nDescription :" + description +
//                                           "\nPic :" + pic + "\nURL :" + url);
//                        System.out.println("===============================================================================");

                        //Create guitar class with data
                        guitar g2 = new guitar();
                        g2.setName(productName);//From web scraping
                        g2.setBrands(brand);//From web scraping
                        g2.setDescription(description);


                        try{
                            //Save data without checking for duplicates
                            dao.simpleSave(g2);
                        }
                        catch(Exception ex){
                            ex.printStackTrace();
                        }

                    }
                }
            }
        }
    }
    }
