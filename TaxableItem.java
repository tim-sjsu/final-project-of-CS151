import java.math.BigDecimal;

/**
 * Created by Lambda on 11/15/2015.
 */
public abstract class TaxableItem implements PriceInterface{
  private final BigDecimal _LOCAL_SALE_TAX = new BigDecimal("0.08");
  protected Item _taxable_item;

  /**
   * CONSTRUCTOR
   * @param input_item A REFERENCE TO THE ITEM
   */
  public TaxableItem(Item input_item){
    this._taxable_item = input_item;
  }

  /**
   * get_price METHOD (PUBLIC)
   *
   * PROVIDES THE ACCESS TO THE PRICE OF THE ITEM
   *
   * @return THE PRICE OF THE CURRENT ITEM
   */
  @Override
  public BigDecimal get_price(){
    BigDecimal sale_tax = this._taxable_item.get_price().multiply(
        _LOCAL_SALE_TAX
    );
    return sale_tax.add(this._taxable_item.get_price());
  }
}