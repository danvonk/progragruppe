package weihnachtsmaerkte.weihnachtsmarkt.staende;

import util.io.SimpleIO;
import util.zufall.Zufall;
import weihnachtsmaerkte.weihnachtsmarkt.Stand;
import weihnachtsmaerkte.weihnachtsmarkt.artikel.Artikel;

public class Weihnachtsartikelstand extends Stand {
    private final Artikel[] articles;

    public Weihnachtsartikelstand() {
        int rand = Zufall.zahl(21);
        articles = new Artikel[rand];
        for (int i = 0; i < rand; i++) {
            articles[i] = new Artikel();
            articles[i].setName(Zufall.artikel());
            double price = Zufall.zahl(11);
            if (price == 0) {
                price += 0.01;
            }
            articles[i].setPrice(price);
        }

    }

    public void verschiebe(int i) {
        System.out.println("Stand " + (i + 1) + " wurde verschoben and wird jetzt von " + getVisitorsPerHour() +
                " Passanten pro Stunde besucht");

    }

    public Artikel[] getArticles() {
        return articles;
    }

    @Override
    public String toString() {
        return "Weihnachtsartikelstand:\n" +
                "Verkaeufer: " + super.getNameOfOwner() + "\n" +
                "Besucher pro Stunde: " + super.getVisitorsPerHour() + "\n";
    }

    @Override
    public void verkaufe() {
        boolean done = false;
        double bill = 0;

        while (!done) {
            System.out.println("Unsere Artikel sind:");
            for (int i = 0; i < articles.length; i++) {
                Artikel article = articles[i];
                System.out.printf("%d: %s (%.2f Euro)\n", (i + 1), article.getName(), article.getPrice());
            }
            System.out.println("Welechen Artikel moechten Sie kaufen?");
            int purchaseIdx = SimpleIO.getInt("Welchen Artikel moechten Sie kaufen?") - 1;
            if (articles[purchaseIdx] != null) {
                //item is available
                System.out.printf("%s wird eingepackt. Viel Spass damit!\n", articles[purchaseIdx].getName());
                bill += articles[purchaseIdx].getPrice();
                articles[purchaseIdx] = null; //remove item from array
            } else {
                System.err.println("Nicht gefunden.");
            }
            System.out.println("Darf es sonst noch etwas sein?");
            done = !SimpleIO.getBoolean("Darf es sonst noch etwas sein?");
        }

        bill /= 100;
        System.out.printf("%.2f Euro, bitte.\n", bill);

    }
}
