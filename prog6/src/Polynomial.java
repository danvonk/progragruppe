/**
 * Class to represent a Polynomial e.g. 3x^2 + 4x
 */
public class Polynomial {
    private Monomial summand;
    private Polynomial summands;

    /**
     * Constructor for Poly
     * @param summand first term of the poly
     * @param summands additional terms of the poly
     */
    private Polynomial(Monomial summand, Polynomial summands) {
        this.summand = summand;
        this.summands = summands;
    }

    /**
     * Copy constructor
     * @param poly the other Poly which will be copied from
     */
    private Polynomial(Polynomial poly) {
        this.summand = poly.summand;
        this.summands = poly.summands;
    }

    /**
     * The empty polynomial interpreted as 0, which is the default representation.
     */
    private static final Polynomial ZERO = new Polynomial(null, null);

    /**
     * Constructor assuming there is no following term in the Poly
     * @param monomial first term of the Poly
     */
    private Polynomial(Monomial monomial) {
        this.summand = monomial;
        this.summands = ZERO;
    }

    /**
     * @param input String representation of polynomial
     * @return the resulting polynomial
     */
    private static Polynomial parse(String input) {
        if (input == null || input.equals("")) {
            return ZERO;
        }
        String[] splitted = input.split("\\+", 2);
        if (splitted.length == 1) {
            return new Polynomial(Monomial.parse(splitted[0]));
        }
        return new Polynomial(Monomial.parse(splitted[0]), parse(splitted[1]));
    }


    /**
     * Whether the Poly is null e.g. if all the terms are 0
     * Do not call this function, rather the one with default params i.e. calculate()
     * @param isZeroSofar defaults to true
     * @param polynomial defaults to this
     * @return whether the Poly is null
     */
    private boolean isZero(boolean isZeroSofar, Polynomial polynomial) {
        if (polynomial == null || !isZeroSofar) {
            //we have reached end of the poly, return
            return isZeroSofar;
        }
        if (polynomial.summand != null) {
            if (!polynomial.summand.isZero()) {
                isZeroSofar = false;
            }
        }
        return isZero(isZeroSofar, polynomial.summands);
    }

    /**
     * Whether the Poly is null e.g. if all the terms are 0
     * @return whether the Poly is null
     */
    private boolean isZero() {
        return isZero(true, this);
    }


    /**
     * String conversion method. Do not call this function, rather the one with default params i.e. toString()
     * @param builder pass in a new StringBuilder
     * @param poly defaults to this
     * @return the string rep. of this class
     */
    private String toString(StringBuilder builder, Polynomial poly) {
        if (poly == null) {
            return builder.toString();
        }
        if (poly.summand != null) {
            builder.append(poly.summand);
            if (poly.summands.summand != null) {
                //look-ahead for this
                builder.append("+");
            }
        }
        return toString(builder, poly.summands);
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
     * Gets the degree of the Poly
     * @return degree of the Poly, i.e. the maximum degree of the terms
     */
    private int getDegree() {
        return getDegree(0, this);
    }

    /**
     * Gets the degree of the Poly
     * @param maxDegree defaults to 0
     * @param poly defaults to this
     * @return degree of the Poly, i.e. the maximum degree of the terms
     */
    private int getDegree(int maxDegree, Polynomial poly) {
        if (poly == null) {
            //we've reached the end of the chain, return
            return maxDegree;
        }
        if (poly.summand != null) {
//            maxDegree = Math.max(maxDegree, poly.summand.getDegree());
            int summandDegree = poly.summand.getDegree();
            if (summandDegree > maxDegree) {
                maxDegree = summandDegree;
            }
        }
        return getDegree(maxDegree, poly.summands);
    }


    /**
     * Substitute in a value to the variable
     * @param toSub Sub into this variable
     * @param value Sub this value into said variable
     * @return whether a substitution occurred or not
     */
    private Polynomial substitute(String toSub, double value) {
        if (substitute(false, toSub, value, this)) {
            //sub occurred, return this
            return this;
        } else {
            return new Polynomial(this);
        }
    }

    /**
     * Substitute in a value to the variable
     * Do not call this function, rather the one with default params i.e. substitute()
     * @param subHappened defaults to false
     * @param valToSub set by overloaded function. See other method definition
     * @param poly defaults to this
     * @return whether a substitution occurred or not
     */
    private boolean substitute(Boolean subHappened, String toSub, double valToSub, Polynomial poly) {
        if (poly == null) {
            return subHappened;
        }
        if (poly.summand != null) {
            if (poly.summand.substitute(toSub, valToSub)) {
                subHappened = true;
            }
        }
        return substitute(subHappened, toSub, valToSub, poly.summands);
    }

    /**
     * Calculate the value of the Poly. Substitute() must be called first
     * @return the value of the Poly
     */
    private double calculate() {
        return calculate(0, this);
    }

    /**
     * Calculate the value of the Mono. Substitute() must be called first.
     * Do not call this function, rather the one with default params i.e. calculate()
     * @param result defaults to 0
     * @param poly defaults to this
     * @return the value of the Poly
     */
    private double calculate(double result, Polynomial poly) {
        if (poly == null) {
            return result;
        }
        if (poly.summand != null) {
            result += poly.summand.calculate();
        }
        return calculate(result, poly.summands);
    }


    /**
     * In this case ALL variables will be set to:
     * @param value all Variables will be set to this val
     * @return whether a substitution occurred or not
     */
    private Polynomial substitute(double value) {
        if (substitute(false, value, this)) {
            //sub occurred, return null
            return this;
        } else {
            return new Polynomial(this);
        }
    }

    /**
     * Substitute in a value to the variable.
     * Do not call this function, rather the one with default params i.e. calculate()
     * @param subHappened defaults to false
     * @param valToSub set by overloaded function. See other method definition
     * @param poly defaults to this
     * @return whether a substitution occurred or not
     */
    private boolean substitute(Boolean subHappened, double valToSub, Polynomial poly) {
        if (poly == null) {
            return subHappened;
        }
        if (poly.summand != null) {
            if (poly.summand.substitute(valToSub)) {
                subHappened = true;
            }
        }
        return substitute(subHappened, valToSub, poly.summands);
    }

    /**
     * In this case ALL variables will be set to:
     * @param defaultValue all Variables will be set to this val
     * @return whether a substitution occurred or not
     */
    private double evaluate(double defaultValue) {
        substitute(defaultValue);
        return calculate();

    }

    public static void main(String[] args) {
        Polynomial p;
        Polynomial q;
        String[] testValues = {"0", "(-3.1415)", "(-1)*x^3+(3.0)*x*y^2", "x+(-1)^5", "3^5+2^6+(3)*(2)*(5)*(4)", "x", "x^4", "x^2*y*z+2*x+(-3)", "x^2+2*x*y+y^2", "(0.0)*x^1000+(0.0)*x*y*z^100+(0.0)^7", "(0.0)*x^1+(0.0)^0"};
        int[] expectedDegrees = {0, 0, 3, 1, 0, 1, 4, 4, 2, 0, 0};
        int i = 0;
        for (String s : testValues) {
            System.out.println("----------------------------------------------------------------");
            System.out.println("Testing polynomial read from " + s + ".");
            p = parse(s);
            System.out.println(p);
            System.out.println("isZero?: " + p.isZero());
            System.out.println("degree: " + p.getDegree());
            System.out.println("degree as expected: " + (p.getDegree() == expectedDegrees[i]));
            i++;
            q = p.substitute("x", 1.);
            System.out.println("x substituted by 1: " + q);
            System.out.println("Now it looks like : " + q);
            System.out.println("x substituted by 1, rest substituted by 0. EVALUATION= " + q.evaluate(0.0));
        }
    }
}