package weihnachtsmarkt.staende;

public class Flammkuchenstand extends Lebensmittelstand {

    @Override
    public String toString() {
        return "Flammkuchenstand:\n" +
                "Preis per 100g: " + super.getPricePer100g() + "\n" +
                "Verkaeufer: " + super.getNameOfOwner() + "\n" +
                "Besucher pro Stunde: " + super.getVisitorsPerHour() + "\n";
    }

}