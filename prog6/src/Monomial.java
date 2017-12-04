/**
 * Represents a Monomial e.g. 3x^2
 */
public class Monomial {
    /**
     * Getter for factor field
     * @return factor field
     */
    Power getFactor() {
        return factor;
    }

    /**
     * Getter for factors field
     * @return factors field (another monomial)
     */
    public Monomial getFactors() {
        return factors;
    }

    private Power factor;
    private Monomial factors;


    /**
     * Constructor for Monomial
     * @param factor first power in the chain
     * @param factors additional powers after that...
     */
    private Monomial(Power factor, Monomial factors) {
        this.factor = factor;
        this.factors = factors;
    }

    /**
     * Copy constructor for Monomial
     * @param mon the other Monomial to copy from
     */
    public Monomial(Monomial mon) {
        this.factors = mon;
        this.factor = null;
    }

    /**
     * Evaluate the value of this Mono. Substitute() must be called first
     * @return the value of the Mono
     */
    public double evaluate() {
        Monomial monos = this;
        double evaluation = 0;
        while (monos != null) {
            evaluation += this.factor.evaluate();
            monos = this.factors;
        }
        return evaluation;
    }

    /**
     * The empty monomial interpreted as 1
     */
    private static final Monomial ONE = new Monomial(null, null);

    /**
     * Constructor for Mono, with no additional terms
     * @param power the first term of the Mono
     */
    private Monomial(Power power) {
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

    /**
     * Whether the Mono is null e.g. if there is a 0 factor
     * @return whether it is zero
     */
    public boolean isZero() {
        return isZero(true, this);
    }

    /**
     * Do not call this function, rather use the one with default params i.e. isZero()
     * @param isZeroSofar defaults to true
     * @param monomial defaults to this
     * @return whether the monomial has a value of zero
     */
    private boolean isZero(boolean isZeroSofar, Monomial monomial) {
        if (monomial == null || !isZeroSofar) {
            //we have reached the end of the monomial, return whatever we have so far
            return  isZeroSofar;
        }
        if (monomial.factor != null) {
            if (!monomial.factor.isZero()) {
                isZeroSofar = false;
            }
        }
        return isZero(isZeroSofar, monomial.factors);
    }


    /**
     * String conversion method. Do not call this function, rather the one with default params i.e. toString()
     * @param builder pass in a new StringBuilder
     * @param mono defaults to this
     * @return the string rep. of this class
     */
    private String toString(StringBuilder builder, Monomial mono) {
        if (mono == null) {
            return builder.toString();
        }
        if (mono.factor != null) {
            builder.append(mono.factor);
            if (mono.factors.factor != null) {
                //look-ahead for this
                builder.append("*");
            }
        }
        return toString(builder, mono.factors);
    }

    /**
     * String conversion method
     * @return the String representation of the class
     */
    @Override
    public String toString() {
        return toString(new StringBuilder(), this);
    }

    /**
     * Gets the degree of the Mono
     * @return 1 if it is a variable (e.g. x) else 0
     */
    public int getDegree() {
        return getDegree(0, this);
    }

    /**
     *  Gets the degree of the Mono. Do not call this function, rather the one with default params i.e. getDegree()
     * @param degreeSum defaults to 0
     * @param mono defaults to this
     * @return degree of the Mono
     */
    private int getDegree(int degreeSum, Monomial mono) {
        if (mono == null) {
            return degreeSum;
        }
        if (mono.factor != null) {
            degreeSum += mono.factor.getDegree();
        }
        return getDegree(degreeSum, mono.factors);
    }

    /**
     * Calculate the value of the Mono. Substitute() must be called first
     * @return the value of the Mono
     */
    public double calculate() {
        return calculate(true, 0,this);
    }

    /**
     * Calculate the value of the Mono. Substitute() must be called first.
     * Do not call this function, rather the one with default params i.e. calculate()
     * @param firstRun defaults to true
     * @param result defaults to 0
     * @param mono defaults to this
     * @return the value of the Mono
     */
    private double calculate(boolean firstRun, double result, Monomial mono) {
        if (mono == null) {
            return result;
        }
        if (mono.factor != null) {
            if (firstRun) {
                result += mono.factor.calculate();
            } else {
                result *= mono.factor.calculate();
            }
        }
        return calculate(false, result, mono.factors);
    }

    /**
     * Substitute in a value to the variable
     * Do not call this function, rather the one with default params i.e. substitute()
     * @param subHappened defaults to false
     * @param varToSub set by overloaded function. See other method definition
     * @param valToSub set by overloaded function. See other method definition
     * @param mono defaults to this
     * @return whether a substitution occurred or not
     */
    private boolean substitute(Boolean subHappened, String varToSub, double valToSub, Monomial mono) {
        if (mono == null) {
            return subHappened;
        }
        if (mono.factor != null) {
            if (mono.factor.isAVariable(varToSub)) {
                mono.getFactor().resetLiteral(new Literal(valToSub));
                subHappened = true;
            }
        }
        return substitute(subHappened, varToSub, valToSub, mono.factors);
    }

    /**
     * Substitute in a value to the variable
     * @param varToSub Sub into this variable
     * @param valToSub Sub this value into said variable
     * @return whether a substitution occurred or not
     */
    public boolean substitute(String varToSub, double valToSub) {
        return substitute(false, varToSub, valToSub, this);

    }

    /**
     * Substitute in a value to the variable.
     * Do not call this function, rather the one with default params i.e. calculate()
     * @param subHappened defaults to false
     * @param valToSub set by overloaded function. See other method definition
     * @param mono defaults to this
     * @return whether a substitution occurred or not
     */
    private boolean substitute(Boolean subHappened, double valToSub, Monomial mono) {
        if (mono == null) {
            return subHappened;
        }
        if (mono.factor != null) {
            if (mono.factor.getLiteral().getType() == Typ.VAR) {
                mono.getFactor().resetLiteral(new Literal(valToSub));
                subHappened = true;
            }
        }
        return substitute(subHappened, valToSub, mono.factors);
    }

    /**
     * used to make the evaluate() function in Poly work. In this case ALL variables will be set to:
     * @param valToSub all Variables will be set to this val
     * @return whether a substitution occurred or not
     */
    public boolean substitute(double valToSub) {
        return substitute(false, valToSub, this);

    }

}