package weihnachtsmaerkte.weihnachtsmarkt;

import util.io.SimpleIO;
import util.zufall.Zufall;
import weihnachtsmaerkte.weihnachtsmarkt.staende.Flammkuchenstand;
import weihnachtsmaerkte.weihnachtsmarkt.staende.Gluehweinstand;
import weihnachtsmaerkte.weihnachtsmarkt.staende.Suesswarenstand;
import weihnachtsmaerkte.weihnachtsmarkt.staende.Weihnachtsartikelstand;

public class Weihnachtsmarkt {
    private final Stand[] stands;

    public Weihnachtsmarkt(int numStands) {
        stands = new Stand[numStands];
        for (int i = 0; i < numStands; i++) {
            int rand = Zufall.zahl(101);
            if (rand >= 0 && rand < 25) {
                Weihnachtsartikelstand as = new Weihnachtsartikelstand();
                as.berechneBesucherProStunde();
//                int visitors = 0;
//                for (Artikel a : as.getArticles()) {
//                    if (a != null) {
//                        visitors += Zufall.zahl(6);
//                    }
//                }
//                as.setVisitorsPerHour(visitors);
                stands[i] = as;

            } else if (rand >= 25 && rand < 50) {
                stands[i] = new Suesswarenstand();
                stands[i].berechneBesucherProStunde();

            } else if (rand >= 50 && rand < 75) {
                stands[i] = new Gluehweinstand();
                stands[i].berechneBesucherProStunde();

            } else {
                stands[i] = new Flammkuchenstand();
                stands[i].berechneBesucherProStunde();

            }
            stands[i].setNameOfOwner(Zufall.name());
        }

    }

    public static void main(String[] args) {
        Weihnachtsmarkt wm = new Weihnachtsmarkt(5);
        boolean done = false;
        while (!done) {
            System.out.println("Der Weihnachtsmarkt besteht aus folgenden Staenden:\n");
            for (int i = 0; i < wm.stands.length; i++) {
                Stand s = wm.stands[i];
                System.out.println((i + 1) + ": " + s);
            }
            System.out.println("Welchen Stand moechten Sie besuchen?\n");
            int stand = SimpleIO.getInt("Welchen Stand moechten Sie besuchen?");
            wm.stands[stand - 1].verkaufe();

            //calculate number of passersby for all stands
            //if <30/hour, verschiebe()
            for (Stand s : wm.stands) {
                s.berechneBesucherProStunde();
                if (s instanceof Weihnachtsartikelstand) {
                    if (s.getVisitorsPerHour() < 30) {
                        ((Weihnachtsartikelstand) s).verschiebe(0);
                    }
                }
            }

            System.out.println("Moechten Sie den Weihnachtsmarkt verlassen?");
            done = SimpleIO.getBoolean("Moechten Sie den Weihnachtsmarkt verlassen?");
        }

    }
}

