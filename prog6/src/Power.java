public class Power {
    private int exponent;
    private Literal literal;

    public Power(int exponent, Literal literal) {
        this.literal = literal;
        if (exponent > 0) {
            this.exponent = exponent;
        } else {
            this.exponent = 0;
        }

    }

    public Power(Literal literal) {
        this.literal = literal;
        exponent = 1;
    }

    public Power(Power p) {
        this.exponent = p.exponent;
        this.literal = p.literal;
    }

    public void resetLiteral(Literal l) {
        literal = l;
    }

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

    public int getExponent() {
        return exponent;
    }

    public Literal getLiteral() {
        return literal;
    }

    public boolean isZero() {
        return literal.isZero() || (literal.isZero() && exponent > 0);
    }

    public boolean isAVariable(String varName) {
        return literal.getType() == Typ.VAR && literal.getName().equals(varName);
    }

    public String toString() {
        if (exponent == 0) {
            return "1";
        } else if (exponent == 1) {
            return literal.toString();
        }
        return literal.toString() + "^" + exponent;
    }

    public int getDegree() {
        if (literal == null) {
            return 0;
        }
        return literal.getDegree() * exponent;
    }

    public double calculate() {
        return Math.pow(literal.getValue(), exponent);
    }
}