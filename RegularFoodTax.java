import java.math.BigDecimal;

/**
 * Created by Lambda on 11/25/2015.
 */
public class RegularFoodTax extends TaxableItem{
  /**
   * CONSTRUCTOR
   *
   * GENERATE THE OBJECT BASED ON THE USER INPUT
   *
   * @param input_item A REFERENCE TO THE ITEM OBJECT
   */
  public RegularFoodTax(Item input_item){
    super(input_item);
  }

  /**
   * get_price METHOD (PUBLIC)
   *
   * OVERRIDE METHOD THAT WILL RETURN THE PRICE OF THE ITEM
   *
   * @return PRICE IN BIGDECIMAL TYPE
   */
  @Override
  public BigDecimal get_price(){
    return super.get_price();
  }
}