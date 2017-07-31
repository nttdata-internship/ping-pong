package money;

import java.math.BigDecimal;
import java.util.Currency;

public class MoneyProblem {
    public static void main(String... aArgs) {
        //TODO: transform first 2 arguments in big decimal values
    	
    	BigDecimal two = new BigDecimal(new String ("100"));
    	BigDecimal one = new BigDecimal(new String ("200"));
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

    private void log(String aText) {
        System.out.println(aText);
    }

    private BigDecimal getSum() {
        return this.fAmountOne.add(fAmountTwo);
    }
    
    

    private BigDecimal getDifference() {
        return this.fAmountOne.subtract(fAmountTwo);
    }

    private BigDecimal getAverage() {
        return getSum().divide(TWO);
    }

    /**
     * 
     * @return 5.25% from first value plus 17.9% from second value
     */
    private BigDecimal getPercentage() {
        return PERCENTAGE.add(fAmountOne);
    }

    /**
     * 
     * @return PercentageChange from value 1 to value 2
     */
    private BigDecimal getPercentageChange() {
        return fAmountTwo.add(getPercentage());
        //TODO
    }

    private BigDecimal rounded(BigDecimal aNumber) {
        return aNumber.setScale(DECIMALS, ROUNDING_MODE);
    }
}