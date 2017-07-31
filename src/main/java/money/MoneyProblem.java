package money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import javax.swing.plaf.RootPaneUI;

public class MoneyProblem {
    public static void main(String... aArgs) {
        //TODO: transform first 2 arguments in big decimal values
    	
    	BigDecimal two = new BigDecimal(new String ("200"));
    	BigDecimal one = new BigDecimal(new String ("100"));
        MoneyProblem calc = new MoneyProblem(one, two);
        calc.doCalculations();
    }

    public MoneyProblem(BigDecimal aAmountOne, BigDecimal aAmountTwo){
        fAmountOne = rounded(aAmountOne);
        fAmountTwo = rounded(aAmountTwo);
      }

    public void doCalculations() {
        log("Amount One: " + fAmountOne);
        log("Amount Two: " + fAmountTwo);
        log("Sum : " + getSum());
        log("Difference : " + getDifference());
        log("Average : " + getAverage());
        log("5.25% of Amount One: " + getPercentage());
        log("Percent Change From Amount One to Two: " + getPercentageChange());
        log("Decimal x divided to decimal y : " + divideTwoNumbers());
        log("int c divided to int y : " + divideTwoNumbersInteger());
        log("compareto : " + compareTwoNumbers());
        log("equals : " + equals());
    }

    private BigDecimal fAmountOne;
    private BigDecimal fAmountTwo;

    /**
     * Defined centrally, to allow for easy changes to the rounding mode.
     */
    private static int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;

    /**
     * Number of decimals to retain. Also referred to as "scale".
     */
    private static int DECIMALS = 2;
    // same shit as dollar
    private static int DECIMAL_PLACES = Currency.getInstance("USD").getDefaultFractionDigits();

    private static int EXTRA_DECIMALS = 4;
    private static final BigDecimal TWO = new BigDecimal("2");
    private static BigDecimal HUNDRED = new BigDecimal("100");
    private static BigDecimal PERCENTAGE = new BigDecimal("5.25");
    private static BigDecimal PERCENTAGE2 = new BigDecimal("17.9");
    
    private void log(String aText) {
        System.out.println(aText);
    }

    private BigDecimal getSum() {
        return this.fAmountOne.add(fAmountTwo);
    }
    
    

    private BigDecimal getDifference() {
        return this.fAmountOne.subtract(fAmountTwo).abs();
    }

    private BigDecimal getAverage() {
        return getSum().divide(TWO);
    }

    /**
     * 
     * @return 5.25% from first value plus 17.9% from second value
     */
    private BigDecimal getPercentage() {
        return PERCENTAGE.multiply(fAmountOne).divide(HUNDRED).add(PERCENTAGE2.multiply(fAmountTwo).divide(HUNDRED));
    }

    /**
     * 
     * @return PercentageChange from value 1 to value 2
     */
    private BigDecimal getPercentageChange() {
        return fAmountTwo.multiply(HUNDRED).divide(fAmountOne);
        //TODO
    }

    private BigDecimal rounded(BigDecimal aNumber) {
        return aNumber.setScale(7,7);
    }
    
    private BigDecimal divideTwoNumbers() {
    	BigDecimal a = rounded(new BigDecimal("10"));
    	BigDecimal b = rounded(new BigDecimal("3"));
        return a.divide(b,RoundingMode.HALF_DOWN);
    }
    
    private float divideTwoNumbersInteger() {
    	float a = 10;
    	float b = 3;
        return  a/b;
    }
    
    private int compareTwoNumbers(){
    	BigDecimal b = divideTwoNumbers();
    	BigDecimal a = BigDecimal.valueOf(divideTwoNumbersInteger());
    	return b.compareTo(a);
    }
    
    private boolean equals(){
    	BigDecimal b = divideTwoNumbers();
    	BigDecimal a = BigDecimal.valueOf(divideTwoNumbersInteger());
    	return b.equals(a);
    	
    	
    }
    
}