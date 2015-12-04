import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Lambda on 11/16/2015.
 */
public class Transaction {
  private static int _transaction_num = 0;
  private String          _usr_id          = null;
  private String          _total           = null;
  private ArrayList<Item> _purchased_items = null;
  private String          _curr_date       = null;

  public Transaction(String id, String total, ArrayList<Item> items){
    _transaction_num++;
    this._usr_id          = id;
    this._total           = total;
    this._purchased_items = items;
    //this._curr_date       = (new SimpleDateFormat("yyyy/mm/dd")).format(
    //    Calendar.getInstance()
    //);
    this._curr_date = "today";
  }

  @Override
  public String toString(){
    String items_str = null;
    int counter = 1;
    for(Item item : _purchased_items){
      items_str += "  [";
      items_str += new Integer(counter).toString();
      items_str += "] - ";
      items_str += item.get_name();
      items_str += "\n";
    }

    String out_fmt =
        "[DATE]     : %s\n[CUSTOMER] : %s\n[TOTAL]    : %s\n[ITEMS]    : \n%s";
    return String.format(out_fmt,
        this._curr_date,
        this._usr_id,
        this._total,
        items_str
        );
  } // END toString Override METHOD

  public final ArrayList<Item> get_purchased_items(){
    return this._purchased_items;
  }
}
