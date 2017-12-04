/**
 * Represents a Power type. e.g. x^3 or 3^4
 */
public class Power {
    private int exponent;
    private Literal literal;

    /**
     * Constructor for Power
     * @param exponent of the power e.g. 2 to raise to x to x^2
     * @param literal base of the power, e.g. x in x^2
     */
    public Power(int exponent, Literal literal) {
        this.literal = literal;
        if (exponent > 0) {
            this.exponent = exponent;
        } else {
            this.exponent = 0;
        }

    }

    /**
     * Constructor for Power. The exponent will be assumed to be 1
     * @param literal base of the power, e.g. x in x^1
     */
    public Power(Literal literal) {
        this.literal = literal;
        exponent = 1;
    }

    /**
     * Copy constructor for Power
     * @param p class to copy from
     */
    public Power(Power p) {
        this.exponent = p.exponent;
        this.literal = p.literal;
    }

    /**
     * Reset the base to a new one defined by the param
     * @param l the new base
     */
    public void resetLiteral(Literal l) {
        literal = l;
    }

    /**
     * Evaluation of the Power. Substitute() must have been called before!
     * @return the eval of the power
     */
    public double evaluate() {
        return Math.pow(literal.getValue(), exponent);
    }

    /**
     * Converts an input string to a power
     * Input
     *
     * @param input String representation of literal ^ exponent
     * @return the resulting power
     */
    public static Power parse(String input) {
        if (input == null || input.equals("")) {
            return new Power(Literal.parse(""));
        }
        String[] splitted = input.split("\\^", 2);
        if (splitted.length == 1) {
            return new Power(Literal.parse(splitted[0]));
        }
        int exponent = 1;
        try {
            exponent = Integer.parseInt(splitted[1]);
        } catch (NumberFormatException e) {
            exponent = 1;
        }

        return new Power(exponent, Literal.parse(splitted[0]));

    }

    /**
     * Getter for exponent field
     * @return the exponent field
     */
    public int getExponent() {
        return exponent;
    }

    /**
     * Getter for literal field
     * @return the literal field
     */
    public Literal getLiteral() {
        return literal;
    }

    /**
     * Whether the Power evaluates to 0
     * @return whether the power evals to 0
     */
    public boolean isZero() {
        return literal.isZero() || (literal.isZero() && exponent > 0);
    }

    /**
     * Whether the base is a variable
     * @param varName given name of requested variable
     * @return the existence of the variable as the base
     */
    public boolean isAVariable(String varName) {
        return literal.getType() == Typ.VAR && literal.getName().equals(varName);
    }

    /**
     * String conversion method
     * @return the String representation of the class
     */
    @Override
    public String toString() {
        if (exponent == 0) {
            return "1";
        } else if (exponent == 1) {
            return literal.toString();
        }
        return literal.toString() + "^" + exponent;
    }

    /**
     * Gets the degree of the power
     * @return the degree e.g. 0 if no base
     */
    public int getDegree() {
        if (literal == null) {
            return 0;
        }
        return literal.getDegree() * exponent;
    }

    /**
     * Calculate the value of the power
     * @return base ^ exponent
     */
    public double calculate() {
        return Math.pow(literal.getValue(), exponent);
    }
}