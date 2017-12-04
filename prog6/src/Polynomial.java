public class Polynomial {
    private Monomial summand;
    private Polynomial summands;

    /**
     * create a new polynomial from these
     *
     * @param summand
     * @param summands
     */
    public Polynomial(Monomial summand, Polynomial summands) {
        this.summand = summand;
        this.summands = summands;
    }

    /**
     * copy constructor
     *
     * @param poly
     */
    public Polynomial(Polynomial poly) {
        this.summand = poly.summand;
        this.summands = poly.summands;
    }

    /**
     * The empty polynomial interpreted as 0, which is the default representation.
     */
    public static final Polynomial ZERO = new Polynomial(null, null);

    /**
     * raise monomial to a polynomial with no other parts i.e. x + null
     *
     * @param monomial
     */
    public Polynomial(Monomial monomial) {
        this.summand = monomial;
        this.summands = ZERO;
    }

    /**
     * @param input String representation of polynomial
     * @return the resulting polynomial
     */
    public static Polynomial parse(String input) {
        if (input == null || input.equals("")) {
            return ZERO;
        }
        String[] splitted = input.split("\\+", 2);
        if (splitted.length == 1) {
            return new Polynomial(Monomial.parse(splitted[0]));
        }
        return new Polynomial(Monomial.parse(splitted[0]), parse(splitted[1]));
    }


    public boolean isZero(boolean isZeroSofar, Polynomial polynomial) {
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

    public boolean isZero() {
        return isZero(true, this);
    }


    public String toString(StringBuilder builder, Polynomial poly) {
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

    @Override
    public String toString() {
        return toString(new StringBuilder(), this);
    }


    public int getDegree() {
        return getDegree(0, this);
    }

    public int getDegree(int maxDegree, Polynomial poly) {
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


    public Polynomial substitute(String toSub, double value) {
        if (substitute(false, toSub, value, this)) {
            //sub occurred, return this
            return this;
        } else {
            return new Polynomial(this);
        }
    }

    public boolean substitute(Boolean subHappened, String toSub, double valToSub, Polynomial poly) {
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

    public double calculate() {
        return calculate(0, this);
    }

    public double calculate(double result, Polynomial poly) {
        if (poly == null) {
            return result;
        }
        if (poly.summand != null) {
            result += poly.summand.calculate();
        }
        return calculate(result, poly.summands);
    }

    public Polynomial substitute(double value) {
        if (substitute(false, value, this)) {
            //sub occurred, return null
            return this;
        } else {
            return new Polynomial(this);
        }
    }

    public boolean substitute(Boolean subHappened, double valToSub, Polynomial poly) {
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

    public double evaluate(double defaultValue) {
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