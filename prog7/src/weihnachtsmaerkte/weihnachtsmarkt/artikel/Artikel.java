package weihnachtsmaerkte.weihnachtsmarkt.artikel;

public class Artikel {
    private String name;
    private double price;

    public Artikel() {
        name = "";
        price = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
