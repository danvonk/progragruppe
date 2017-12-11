package weihnachtsmaerkte.weihnachtsmarkt.staende;

import util.io.SimpleIO;
import util.zufall.Zufall;
import weihnachtsmaerkte.weihnachtsmarkt.Stand;

public abstract class Lebensmittelstand extends Stand {
    private double pricePer100g;

    public Lebensmittelstand() {
        double price = Zufall.zahl(4);
        if (price == 0) {
            price += 0.01;
        }
        pricePer100g = price;
    }


    public double getPricePer100g() {
        return pricePer100g;
    }

    public void setPricePer100g(double pricePer100g) {
        this.pricePer100g = pricePer100g;
    }

    @Override
    public void verkaufe() {
        boolean done = false;
        double bill = 0;
        while (!done) {
            System.out.println("Wie viele Gramm moechten Sie besuchen?");
            int grammes = SimpleIO.getInt("Wie viele Gramm moechten Sie besuchen?");
            System.out.println(grammes + " Gramm fuer Sie. Lassen Sie es schmecken!");

            System.out.println("Darf es sonst noch etwas sein?");
            done = !SimpleIO.getBoolean("Darf es sonst noch etwas sein?");

            bill += grammes * getPricePer100g();
        }

        bill /= 100;
        System.out.printf("%.2f Euro, bitte.\n", bill);
    }
}
