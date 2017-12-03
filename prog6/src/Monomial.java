public class Monomial{
  
  private final Power factor;
  
  private final Monomial factors;


  public Monomial(Power factor, Monomial factors){
//      this.factor = factor;
//      this.factors = factors; //pointer to the next monomial
  }
  
  public Monomial(Monomial mon){
//      this.factor = null;
//      this.factors = mon;
  }
  /**
  * The empty monomial interpreted as 1
  */
  public static final Monomial ONE = new Monomial(null,null);

  //make the power a monomial by giving it a neutral factor...
  public Monomial(Power power){
//      this.factor = power;
//      this.factors = ONE;
  }
  
  /**
  * @param input String representation of monomial
  * @return the resulting monomial
  */
  public static Monomial parse(String input){
    if(input==null||input.equals("")){
      return ONE;
    }
    String[] splitted = input.split("\\*",2);
    if(splitted.length==1){
      return new Monomial(Power.parse(splitted[0]));
    }
    return new Monomial(Power.parse(splitted[0]),parse(splitted[1]));
  }
    
}