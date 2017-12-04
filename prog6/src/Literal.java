/**
 * This class holds the basic data type of the Polynomial.
 * It can either be a variable or a constant as defined by Typ.
 *
 * @author dan
 */
public class Literal {
    private final Typ type;
    private final double value;
    private final String name;


    /**
     * Constructor assuming the literal is a constant
     * @param value of the constant
     */
    public Literal(double value) {
        this.value = value;
        this.type = Typ.VALUE;
        this.name = "";
    }

    /**
     * Constructor assuming the class is a variable
     * @param name of the variable e.g. x
     */
    private Literal(String name) {
        this.name = name;
        this.type = Typ.VAR;
        this.value = 0;
    }

    /**
     * Copy constructor for the class.
     *
     * @param toCopy other class to copy from
     */
    public Literal(Literal toCopy) {
        this.type = toCopy.type;
        this.value = toCopy.value;
        this.name = toCopy.name;
    }

    /**
     * Converts an input string to a literal
     *
     * @param input String representation of literal
     * @return the resulting literal
     */
    public static Literal parse(String input) {
        if (input == null || input.equals("")) {
            return new Literal(1.);
        }
        double value = 0.;
        String name = "";
        input = input.replaceAll("[()]", "");
        try {
            value = Double.parseDouble(input);
            return new Literal(value);
        } catch (NumberFormatException e) {
            name = input;
        }
        return new Literal(name);
    }

    /**
     * Getter for Type
     * @return the field type
     */
    public Typ getType() {
        return type;
    }

    /**
     * Getter for Value
     * @return the field value
     */
    public double getValue() {
        return value;
    }

    /**
     * Getter for Name
     * @return the field name
     */
    public String getName() {
        return name;
    }

    /**
     * Determines whether the literal is a constant of 0
     * @return whether it resolves to 0
     */
    public boolean isZero() {
        return type == Typ.VALUE && value == 0;
    }

    /**
     * String conversion method
     * @return the String representation of the class
     */
    @Override
    public String toString() {
        if (type == Typ.VAR) {
            return name;
        }
        return "(" + value + ")";
    }

    /**
     * Gets the degree of the literal
     * @return 1 if it is a variable (e.g. x) else 0
     */
    public int getDegree() {
        if (type == Typ.VAR) {
            return 1;
        }
        return 0;
    }
}
