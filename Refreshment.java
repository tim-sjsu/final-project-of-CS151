import java.math.BigDecimal;

/**
 * Created by Lambda on 11/14/2015.
 */
public class Refreshment extends Item {
  private BigDecimal _calories;
  /**
   * CONSTRUCTOR
   *
   * TAKE INPUT AND CONSTRUCT A REFRESHMENT OBJECT
   * NOTE: PRICE IS IN BIGDECIMAL FORMAT
   *
   * @param item_na```\me ITEM NAME (STRING)
   * @param price     ITEM PRICE (STRING)
   * @param id        PRODUCT ID (STRING)
   * @param cal       CALORIES (STRING)
   */
  public Refreshment(String item_name, String price, String id,
                     String cal, String path, ItemType it) {
    super(item_name, price, id, path, it);
    this._calories = (cal != null)? new BigDecimal(cal): new BigDecimal(100);
  }

  /**
   * get_info METHOD PUBLIC
   *
   * RETURN A STRING THAT CONTAINS THE INFORMATION ABOUT THIS ITEM
   *
   * @return INFORMATION STRING
   */
  @Override
  public String get_info() {
    String name_fmt  = "Name : %s. ";
    String price_fmt = "Price : %s. ";
    String cal_fmt   = "Calories : %s. ";
    String name_str = String.format(name_fmt, this._name);
    String price_str = String.format(price_fmt,
        CurrencyConvertor.convert_to_USD_fmt(this._price));
    String cal_str = String.format(cal_fmt, this._calories.toString());
    return name_str+price_str+cal_str;
  }
}


/**
 import java.math.BigDecimal;
 import java.text.NumberFormat;
 import java.util.Locale;
 public class HelloWorld{
 public static void main(String []args){
 BigDecimal num1 = new BigDecimal("2.99");
 BigDecimal num2 = new BigDecimal("5.49");
 BigDecimal sum = num1.add(num2);
 BigDecimal tax = sum.multiply(new BigDecimal("0.10"));
 BigDecimal total = sum.add(tax);
 total.setScale(1, BigDecimal.ROUND_HALF_UP);
 NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
 nf.setMinimumFractionDigits(2);
 System.out.println(nf.format(total.doubleValue()));
 }
 }
 */