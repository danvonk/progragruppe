public class Monomial {

    Power getFactor() {
        return factor;
    }

    public Monomial getFactors() {
        return factors;
    }

    private Power factor;

    private Monomial factors;


    public Monomial(Power factor, Monomial factors) {
        this.factor = factor;
        this.factors = factors;
    }

    public Monomial(Monomial mon) {
        this.factors = mon;
        this.factor = null;
    }

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
        return isZero(true, this);
    }

    public boolean isZero(boolean isZeroSofar, Monomial monomial) {
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

    public String toString(StringBuilder builder, Monomial mono) {
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

    @Override
    public String toString() {
        return toString(new StringBuilder(), this);
    }

    public int getDegree() {
        return getDegree(0, this);
    }

    public int getDegree(int degreeSum, Monomial mono) {
        if (mono == null) {
            return degreeSum;
        }
        if (mono.factor != null) {
            degreeSum += mono.factor.getDegree();
        }
        return getDegree(degreeSum, mono.factors);
    }

    public double calculate() {
        return calculate(true, 0,this);
    }

    public double calculate(boolean firstRun, double result, Monomial mono) {
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

    public void substitute(Boolean subHappened, String varToSub, double valToSub, Monomial mono) {
        if (mono == null) {
            return;
        }
        if (mono.factor != null) {
            if (mono.factor.isAVariable(varToSub)) {
                mono.getFactor().resetLiteral(new Literal(valToSub));
                subHappened = true;
            }
        }
        substitute(subHappened, varToSub, valToSub, mono.factors);
    }

    public boolean substitute(String varToSub, double valToSub) {
        Boolean subHappened = Boolean.FALSE;
        substitute(subHappened, varToSub, valToSub, this);
        return subHappened;
    }

}