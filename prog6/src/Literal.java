public class Literal {
    private final Typ type;
    private final double value;
    private final String name;


    public Literal(double value) {
        this.value = value;
        this.type = Typ.VALUE;
        this.name = "";
    }

    public Literal(String name) {
        this.name = name;
        this.type = Typ.VAR;
        this.value = 0;
    }

    //copy constructor
    public Literal(Literal toCopy) {
        this.type = toCopy.type;
        this.value = toCopy.value;
        this.name = toCopy.name;
    }

    /**
     * change to constant
     * @param value
     */
    public void changeToConstant(double value) {
    }


    /**
     * Converts an input string to a literal
     * @param input  String representation of literal
     * @return the resulting literal
     */
  public static Literal parse(String input){
    if(input==null || input.equals("")){
      return new Literal(1.);
    }
    double value = 0.;
    String name = "";
    input = input.replaceAll("[()]","");
    try{
      value = Double.parseDouble(input);
      return new Literal(value);
    }
    catch(NumberFormatException e){
      name = input;
    }
    return new Literal(name);
  }

    public Typ getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public boolean isZero() {
        return type == Typ.VALUE && value == 0;
    }

    public String toString() {
      if (type == Typ.VAR) {
          return name;
      }
      return "(" + value + ")";
    }

    public int getDegree() {
      if (type == Typ.VAR) {
          return 1;
      }
      return 0;
    }
}
