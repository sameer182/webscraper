package PriceBuddy;

public class Main {
    public static void main(String[] args) {
//        guitarScraper2 scraper2 = new guitarScraper2();
        guitarScraper3 scraper3 = new guitarScraper3();
        try {
//            scraper2.scrape();
            scraper3.scrape();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
