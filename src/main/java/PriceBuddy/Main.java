package PriceBuddy;

public class Main {
    public static void main(String[] args) {
        guitarDao dao = new guitarDao();
        dao.init();

        GuitarScraper1 scraper1 = new GuitarScraper1(dao);
        GuitarScraper2 scraper2 = new GuitarScraper2(dao);
        GuitarScraper3 scraper3 = new GuitarScraper3(dao);
        GuitarScraper4 scraper4 = new GuitarScraper4(dao);
        GuitarScraper5 scraper5 = new GuitarScraper5(dao);


        try {
            scraper1.scrape();
            scraper2.scrape();
            scraper3.scrape();
            scraper4.scrape();
            scraper5.scrape();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
