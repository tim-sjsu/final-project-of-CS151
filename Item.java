import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by Lambda on 11/13/2015.
 */
public abstract class Item implements PriceInterface{
  protected String     _name      = null;
  protected BigDecimal _price     = null;
  protected int        _item_id   = -1;
  protected String     _icon_path = null;
  protected ItemType   _type      = null;

  /**
   * CONSTRUCTOR
   *
   * TAKE INPUT AND CONSTRUCT AN ITEM OBJECT
   * NOTE: PRICE IS IN BIGDECIMAL FORMAT
   *
   * @param item_name ITEM NAME (STRING)
   * @param price     ITEM PRICE (STRING)
   * @param id        PRODUCT ID (STRING)
   */
  public Item(String item_name, String price,
              String id, String dir, ItemType type){
    this._name      = (item_name != null)? new String(item_name): "N/A";
    this._price     = (price != null)? new BigDecimal(price): new BigDecimal(1);
    this._item_id   = (id != null)? Integer.parseInt(id): 0;
    this._icon_path = (dir != null)? dir: "/default.png";
    this._type      = (type != null)? type: ItemType.DEFAULT;
  } // END CONSTRUCTOR

  /**
   *
   * @return
   */
  public final String get_icon_path(){
    return this._icon_path;
  }

  /**
   * get_name METHOD PUBLIC
   *
   * SIMPLE GETTER FOR ITEM NAME
   *
   * @return _name ITEM NAME
   */
  public final String get_name(){
    return this._name;
  } // END get_name METHOD

  /**
   * get_price METHOD PUBLIC (OVERRIDE)
   *
   * SIMPLE GETTER FOR ITEM PRICE
   *
   * @return _price ITEM PRICE
   */
  @Override
  public final BigDecimal get_price(){
    return this._price;
  } // END get_price METHOD

  /**
   * get_id METHOD PUBLIC
   *
   * SIMPLE GETTER FOR PRODUCT ID
   *
   * @return _item_id PRODUCT ID
   */
  public final int get_id(){
    return this._item_id;
  } // END get_id METHOD

  //public String get_price_in_usd_format(){
  //  NumberFormat usd_format = NumberFormat.getCurrencyInstance(Locale.US);
  //  return usd_format.format(this._price.doubleValue());
  //}

  //protected String convertTo_currencyFormat(BigDecimal total){
  //  NumberFormat usd_format = NumberFormat.getCurrencyInstance(Locale.US);
  //  return usd_format.format(total.doubleValue());
  //}

  protected final ItemType get_type(){
    return this._type;
  }

  /**
   * get_info METHOD PUBLIC - ABSTRACT
   *
   * DECLARE AN ABSTRACT METHOD AND LET CHILD CLASSES TO IMPLEMENT IT.
   *
   * @return INFORMATION ABOUT THIS TIME (STRING)
   */
  public abstract String get_info();

  //////////////////////////// TEST ///////////////////////////////////////////
  public static void main(String[] args) {
    Item soda = new Refreshment(
        "SODA", "2.99", "1", "160", "/PATH.png",ItemType.valueOf("SODA")
    );
    Item snack = new Refreshment(
        "SNACK", "3.99", "2", "250", "/snack.png", ItemType.valueOf("SNACK")
    );
    Item book = new HandBook(
        "BOOK", "4.5", "3", "AUTHOR", "ISBN123",
        "/book.png", ItemType.valueOf("GRADE_BOOK")
    );

    ArrayList<Item> list = new ArrayList<>();
    list.add(soda);
    list.add(snack);
    list.add(book);

    for(Item i : list){
      System.out.println(i.get_info());
      System.out.println("----------------------------");
      System.out.println("price : " + i.get_price());
      System.out.println("----------------------------");
      System.out.println("name : " + i.get_name());
      System.out.println("id : " + i.get_id());
      System.out.println("type : " + i.get_type().toString());
      System.out.println("icon_path : " + i.get_icon_path());
      System.out.println("----------------------------");
      System.out.println(">>>>>>>>>>>>>>>>>>");
    }
  }
}