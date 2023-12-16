    package PriceBuddy;

    import javax.persistence.*;

    @Entity
    @Table(name="guitar")
    public class guitar {
       //Class representing a guitar
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        int id;

        //Guitar name
        @Column(name = "name")
        String name;

        //Brand name
        @Column(name = "brands")
        String brands;

        @Column(name = "model")
        String model;

        //Guitar description
        @Column(name = "description")
        String description;

        @Column(name = "pic")
        String pic;

        public int getId() {
            return id;
        }
        public void setId(int id) {
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
        public String getModel() {
            return model;
        }
        public void setModel(String model) {
            this.model = model;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public String getPic(){ return pic; }
        public void setPic(String pic) {this.pic = pic;}


    }


