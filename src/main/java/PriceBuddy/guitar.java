package PriceBuddy;

import javax.persistence.*;

@Entity
@Table(name="guitar")
public class guitar {
/** Class representing a guitar */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    String id;

    //Guitar name
    @Column(name = "name")
    String name;

    //Brand name
    @Column(name = "brands")
    String brands;

    //Guitar description
    @Column(name = "description")
    String description;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBrands() {
        return brands;
    }
    public void setBrands(String brands) {
        this.brands = brands;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}


