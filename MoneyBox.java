import java.math.BigDecimal;

/**
 * Created by Lambda on 11/15/2015.
 */
public class MoneyBox {
  private BigDecimal _balance = null;

  public MoneyBox(){
    this._balance = new BigDecimal("0.0");
  }

  public void collect_payment(BigDecimal payment){
    this._balance.add(payment);
  }

  public final BigDecimal get_balance(){
    return this._balance;
  }

  public BigDecimal collect_money(){
    BigDecimal temp = this._balance;
    this._balance = new BigDecimal("0.0");
    return temp;
  }
}
