package weihnachtsmaerkte.weihnachtsmarkt.staende;

public class Gluehweinstand extends Lebensmittelstand {

    @Override
    public String toString() {
        return "Gluehweinstand:\n" +
                "Preis per 100g: " + super.getPricePer100g() + "\n" +
                "Verkaeufer: " + super.getNameOfOwner() + "\n" +
                "Besucher pro Stunde: " + super.getVisitorsPerHour() + "\n";
    }
}