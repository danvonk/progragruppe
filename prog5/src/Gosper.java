public class Gosper {

  static void gosperLinks(Canvas c, int ordnung, int length) {
      if (ordnung == 0) {
          c.drawForward(length);
      } else {
          gosperLinks(c, ordnung - 1, length);
          c.rotate(-60);
          gosperRechts(c, ordnung - 1, length);
          c.rotate(-120);
          gosperRechts(c, ordnung - 1, length);
          c.rotate(60);
          gosperLinks(c, ordnung - 1, length);
          c.rotate(120);
          gosperLinks(c, ordnung - 1, length);
          gosperLinks(c, ordnung - 1, length);
          c.rotate(60);
          gosperRechts(c, ordnung - 1, length);
          c.rotate(-60);
      }
  }

  static void gosperRechts(Canvas c, int ordnung, int length) {
      if (ordnung == 0) {
          c.drawForward(length);
      } else {
          c.rotate(60);
          gosperLinks(c, ordnung - 1, length);
          c.rotate(-60);
          gosperRechts(c, ordnung - 1, length);
          gosperRechts(c, ordnung - 1, length);
          c.rotate(-120);
          gosperRechts(c, ordnung - 1, length);
          c.rotate(-60);
          gosperLinks(c, ordnung - 1, length);
          c.rotate(120);
          gosperLinks(c, ordnung - 1, length);
          c.rotate(60);
          gosperRechts(c, ordnung - 1, length);
      }
  }

  public static void main(String[] args) {
        int ordnung = 4;
        int length = 5;
        boolean links = true;
        switch (args.length) {
            case 3:
              if(args[2].equals("r")) {
                links = false;
              } else if (!args[2].equals("l")) {
                System.out.println(
                    "Unbekannter parameter " + args[2]);
                System.out.println(
                    "Nutze Standardwert l");
              }
            case 2:
                length = Integer.parseInt(args[1]);
                // fall-through
            case 1:
                ordnung = Integer.parseInt(args[0]);
                break;
            default:
                System.out.println(
                    "Verwende Standardwerte: Ordnung 4, Laenge 5, links.");
                System.out.println(
                    "Verwendung: java Gosper [ordnung [laenge [(l|r)]]]");
        }
        if (ordnung < 0) {
            System.out.println("Die Rekursionsordnung muss nicht-negativ sein!");
            return;
        }
        if (length < 1) {
            System.out.println("Die Laenge muss positiv sein!");
            return;
        }
        Canvas c = new Canvas();
//        c.rotate(180);
//        c.drawForward(25);
        if(links) {
          Gosper.gosperLinks(
              c,
              ordnung,
              length
          );
        } else {
          Gosper.gosperRechts(
              c,
              ordnung,
              length
          );
        }
        c.refresh();
  }
}
