package weihnachtsmarkt;

import zufall.Zufall;
import weihnachtsmarkt.artikel.Artikel;
import weihnachtsmarkt.staende.Weihnachtsartikelstand;

public abstract class Stand {
    private String nameOfOwner;
    private int besucherProStunde;

    public String getNameOfOwner() {
        return nameOfOwner;
    }

    public void setNameOfOwner(String owner) {
        this.nameOfOwner = owner;
    }

    public int getVisitorsPerHour() {
        return besucherProStunde;
    }

    public void setVisitorsPerHour(int visitors) {
        besucherProStunde = visitors;
    }

    public void berechneBesucherProStunde() {
        if (this instanceof Weihnachtsartikelstand) {
            Weihnachtsartikelstand as = (Weihnachtsartikelstand)this;
            int visitors = 0;
            for (Artikel a : as.getArticles()) {
                if (a != null) {
                    visitors += Zufall.zahl(6);
                }
            }
            as.setVisitorsPerHour(visitors);
        } else {
            setVisitorsPerHour(Zufall.zahl(101));
        }

    }

    public void verkaufe() {
        System.out.println("Guten Tag!");
    }

    public String toString() {
        return "";
    }

}
