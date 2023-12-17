    package PriceBuddy;

    import org.jsoup.Jsoup;
    import org.jsoup.nodes.Document;
    import org.jsoup.nodes.Element;
    import org.jsoup.select.Elements;

    import java.io.IOException;


    public class GuitarScraper4 {

        private guitarDao dao;

        public GuitarScraper4(guitarDao dao) {
            this.dao = dao;
        }

        void scrape() throws IOException {

            //Loop for the pages
            for (int page = 1; page <= 20; page++) {

                //Connect to the URL and retrieve the HTML document
                Document doc = Jsoup.connect("https://www.pmtonline.co.uk/products/guitar/electricguitars?p=" + page)
                        .userAgent("Google Chrome - Computer Science Student - Webscraping Coursework Project").get();

                Elements mainContainer = doc.select("li.item");

                //Go through each product container
                for (Element guitarSection : mainContainer) {
                    //Get the guitar name
                    Elements guitarName = guitarSection.select("h3.product-item-name a.product-item-link");
                    // Check if the product name element is present
                    if (!guitarName.isEmpty()) {
                        String fullGuitarName = guitarName.text().trim();
                        //Remove the unnecessary name after the comma
                        String name = fullGuitarName.split(",")[0].trim();

                        //Extract the brand name from the first word of the product name which is guitar title.
                        String[] words = name.split("\\s+");
                        if (words.length > 0) {
                            String brand = words[0];

                        //Get the guitar price
                        Elements guitarPrice = guitarSection.select("span.price-wrapper span.price");

                        //Check if the product price is empty and remove all the non-numerical digits
                        if (!guitarPrice.isEmpty()) {
                            String priceSting = guitarPrice.text().trim().replaceAll("[^0-9]", "");
                            int price = Integer.parseInt(priceSting) / 100;

                            //Get the guitar pic
                            Elements guitarPic = guitarSection.select("a.product-item-photo img");
                            String pic = guitarPic.attr("src");

                            //Get the product URL
                            Elements guitarURL = guitarSection.select("h3.product-item-name a.product-item-link");
                            String url = guitarURL.attr("href");

                            //Get the guitar model from inside the product URL
                            String model = scrapeModel(url);

                            //Get the product description from inside the product URL
                            String description = scrapeDescription(url);

                            // Create guitar class with data
                            guitar g4 = new guitar();
                            g4.setName(name);
                            g4.setBrands(brand);
                            g4.setModel(model);
                            g4.setDescription(description);
                            g4.setPic(pic);

                            // Create comparison class with data
                            comparison g4Comparison = new comparison();
                            g4Comparison.setGuitar(g4);
                            g4Comparison.setPrice(price);
                            g4Comparison.setUrl(url);

                            try {
                                // Save the comparison
                                dao.saveAndMergeGuitar(g4, price, url);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }
                    }

                    }
                }
            }
        }
        String scrapeModel(String url) throws IOException {
            Document productPage = Jsoup.connect(url).get();
            Element modelElement = productPage.select("table#product-attribute-specs-table tr:contains(MPN)").first();
            String modelText = modelElement != null ? modelElement.select("td.data").text().trim() : "";
            //Remove the hyphens and converts to lowercase
            return modelText.replace("-", "").toLowerCase();
        }
        String scrapeDescription(String url) throws IOException {
            Document productPage = Jsoup.connect(url).get();
            Element descriptionElement = productPage.select("div.value p").first();
            return descriptionElement != null ? descriptionElement.text().trim() : "";

        }


    }