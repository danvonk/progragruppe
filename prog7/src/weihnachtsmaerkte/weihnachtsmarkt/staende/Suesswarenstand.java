package weihnachtsmaerkte.weihnachtsmarkt.staende;

import util.zufall.Zufall;

public class Suesswarenstand extends Lebensmittelstand {
    private final String typeOfSweets;

    public Suesswarenstand() {
        typeOfSweets = Zufall.suessware();
    }

    @Override
    public String toString() {
        return "Suesswarenstand (" + typeOfSweets + " )\n" +
                "Preis per 100g: " + super.getPricePer100g() + "\n" +
                "Verkaeufer: " + super.getNameOfOwner() + "\n" +
                "Besucher pro Stunde: " + super.getVisitorsPerHour() + "\n";
    }


}