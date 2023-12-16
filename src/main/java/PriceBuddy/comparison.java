package PriceBuddy;

import javax.persistence.*;

@Entity
@Table(name = "comparison")
public class comparison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guitar_id", nullable = false)
    guitar guitar;

    @Column(name = "price")
    int price;

    @Column(name = "url")
    String url;

    public comparison() {
        // default constructor
    }

    public comparison(guitar guitar, int price, String url) {
        this.guitar = guitar;
        this.price = price;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public guitar getGuitar() {
        return guitar;
    }

    public void setGuitar(guitar guitar) {
        this.guitar = guitar;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
