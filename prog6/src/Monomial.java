public class Monomial {

    private final Power factor;

    private final Monomial factors;


    public Monomial(Power factor, Monomial factors) {
        this.factor = factor;
        this.factors = factors;
    }

    public Monomial(Monomial mon) {
        this.factors = mon;
        this.factor = null;
    }

    /**
     * The empty monomial interpreted as 1
     */
    public static final Monomial ONE = new Monomial(null, null);

    //make the power a monomial by giving it a neutral factor...
    public Monomial(Power power) {
        this.factor = power;
        this.factors = ONE;
    }

    /**
     * @param input String representation of monomial
     * @return the resulting monomial
     */
    public static Monomial parse(String input) {
        if (input == null || input.equals("")) {
            return ONE;
        }
        String[] splitted = input.split("\\*", 2);
        if (splitted.length == 1) {
            return new Monomial(Power.parse(splitted[0]));
        }
        return new Monomial(Power.parse(splitted[0]), parse(splitted[1]));
    }

    public boolean isZero() {
        //TODO: Make recursive
        Monomial entry = this;
        while (entry != null) {
            if (entry.isZero()) {
                return true;
            }
            entry = factors;
        }
        return false;
    }

    @Override
    public String toString() {
        //TODO: Make recursive
        if (isZero()) {
            return "1";
        }
        StringBuilder builder = new StringBuilder();

        Monomial entry = this;
        while (entry != null) {
            builder.append(entry.factor).append("*");
            entry = entry.factors;
        }
        return builder.toString();
    }

    public int getDegree() {
        if (isZero()) {
            return 0;
        }
        int degreeSum = 0;
        Monomial entry = this;
        while (entry != null) {
            degreeSum += entry.getDegree();
            entry = entry.factors;
        }
        return degreeSum;
    }

}