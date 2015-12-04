import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Lambda on 11/15/2015.
 */

/**
 * Tray CLASS
 *
 * TRAY REPRESENTS A SINGLE TRAY INSIDE THE VENDING MACHINE. EACH TRAY OBJECTS
 * IS ABLE TO CONTAIN UP TO 12 IDENTICAL ITEMS - EACH ITEM IN THE SAME TRAY
 * WOULD HAVE THE SAME NAME, THE SAME PRICE, AND THE SAME IMAGE.
 */
public class Tray {
  private final int   _TRAY_SIZE  = 12;
  private Queue<Item> _tray       = null;
  private int         _tray_num   = -1;
  private String      _item_name  = null;
  private String      _item_price = null;
  private String      _icon_dir   = null;
  private String      _item_id    = null;

  /**
   * CONSTRUCTOR
   *
   * @param id TRAY ID
   */
  public Tray(int id){
    this._tray_num = id;
    this._tray = new LinkedList<Item>();
  }

  /**
   * get_number_of_remaining_item METHOD (PUBLIC)
   *
   * @return THE SIZE OF THE _tray LIST MEANS HOW MANY ITEMS LEFT INSIDE THIS
   *         PARTICULAR TRAY
   */
  public final Integer get_number_of_remaining_item() {
    return this._tray.size();
  }

  /**
   * is_full METHOD (PUBLIC)
   *
   * SIMPLY VALIDATE IF THE TRAY IS FULL (EQUALS TO THE MAX_SIZE OF TRAY)
   *
   * @return TRUE  : IS FULL
   *         FALSE : NOT FULL
   */
  public final boolean is_full(){
    return (this._tray.size() == _TRAY_SIZE)? true: false;
  }

  /**
   * is_empty METHOD (PUBLIC)
   *
   * VALIDATE IF THE TRAY IS EMPTY
   *
   * @return TRUE  : EMPTY
   *         FALSE : NOT EMPTY
   */
  public final boolean is_empty(){
    return (this._tray.size() == 0)? true: false;
  }

  /**
   * push METHOD (PUBLIC)
   *
   * push METHOD IS A SETTER METHOD FOR TRAY CLASS. IT WILL ACCEPT AN ITEM FROM
   * USER AND APPEND IT INTO THE LIST AS LONG AS THE TRAY IS NOT FULL
   *
   * @param item INSERTING ITEM OBJECT
   */
  public void push(Item item){
    if(this._tray.size() == 0){
      this._item_name  = item.get_name();
      this._item_price = CurrencyConvertor.convert_to_USD_fmt(item.get_price());
      this._icon_dir   = item.get_icon_path();
      this._item_id    = String.valueOf(item.get_id());
      //System.out.println(this._item_id);
    }
    if(!this.is_full()) {
      this._tray.add(item);
      return;
    }
    //throw new IndexOutOfBoundsException("[ERROR] TRY IS FULL.\n");
  }

  /**
   * pop METHOD (PUBLIC)
   *
   * pop METHOD WILL REMOVE THE FIRST ITEM FROM THE TRAY LIST AND RETURN IT
   * TO THE OUTSIDE AS LONG AS THE TRAY IS NOT EMPTY
   *
   * @return ITEM : THE PURCHASING ITEM
   */
  public Item pop(){
    if(!this.is_empty()) {
      return this._tray.remove();
    }
    return null;
    //throw new IndexOutOfBoundsException("[ERROR] TRAY IS EMPTY.\n");
  }

  /**
   * get_item_tuple METHOD (PUBLIC)
   *
   * GENERATE THE ITEM INFORMATION TUPLE TO ENCLOSE THE INFORMATION ABOUT THE
   * ITEMS THAT STORES INSIDE THIS PARTICULAR TRAY. (CO-OPERATES WITH GUI)
   * (THIS METHOD WORKS WITH GUI TO INITIALIZE THE LIST ON GUI TO DISPLAY
   * ITEMS TO BE SOLD.
   *
   * @return ItemInfoTuple : NAME, PRICE, ICON DIRECTORY
   */
  public ItemInfoTuple get_item_tuple(){
    //Item item_ref = this._tray.peek();
    //return new ItemInfoTuple(
    //  item_ref.get_name(),
    //  CurrencyConvertor.conver_to_USD_fmt(item_ref.get_price().doubleValue()),
    //  item_ref.get_icon_path()
    //);
    return new ItemInfoTuple(
        this._item_name,
        this._item_price,
        this._icon_dir
    );
  }

  /**
   * get_item_info METHOD PUBLIC
   *
   * get_item_info WILL RETURN THE STRING, WHICH CONTAINS SHORT DESCRIPTION OF
   * PARTICULAR ITEM - FROM ITEM CLASS, TO GUI. THIS IS FOR THE MOUSE HOOVER
   * FUNCTIONALITY IN GUI
   *
   * @return FORMATTED STRING THAT CONTAINS SHORT INFO ABOUT THE ITEM
   */
  public final String get_item_info(){
    if(this._tray.size() != 0){
      return this._tray.peek().get_info();
    }
    return "";
    //throw new ArrayIndexOutOfBoundsException("EMPTY TRAY.\n");
  }

  /**
   * get_tray_info METHOD (PUBLIC)
   *
   * get_tray_info METHOD PROVIDES THE BACK-END ACCESS FOR THE OWNER TO MONITOR
   * THE CURRENT STATUS OF THIS PARTICULAR TRAY.
   *
   * @return A FORMATTED STRING THAT CONTAINS DETAIL INFORMATION ABOUT THIS
   *         TRAY
   */
  public String get_tray_info(){
    String tray_fmt =
        "Tray Number    : %d\n" +
        "Max Size       : %d\n" +
        "Item Left      : %d\n" +
        "Available Room : %d\n" +
        "Stored Item ID : %d\n";

    String return_str = String.format(
        tray_fmt, this._tray_num,
        this._TRAY_SIZE, this._tray.size(),
        this._TRAY_SIZE-this._tray.size(), this._tray.peek().get_id()
    );
    return return_str;
  }

  /**
   * get_item_price METHOD (PUBLIC)
   *
   * PROVIDE USER TO HAVE ACCESS TO THE PRICE OF SELLING ITEM
   *
   * @return PRICE IN BIGDECIAML TYPE
   */
  public final BigDecimal get_item_price(){
    //return this._tray.peek().get_price();
    PriceInterface pInterface = null;
    if(this._tray.size() == 0){
      return new BigDecimal("0");
    }
    switch(this._tray.peek().get_type()){
      case SODA:
        pInterface = new RecycleTaxableItem(this._tray.peek());
        break;
      case SNACK:
        pInterface = this._tray.peek();
        break;
      case GRADE_BOOK:
        pInterface = this._tray.peek();
        break;
      default:
        pInterface = this._tray.peek();
    }
    return pInterface.get_price();
  }

  /**
   * get_containing_product_id METHOD (PUBLIC)
   *
   * SIMPLE GETTER TO PROVIDE THE PRODUCT ID OF THE ITEM STORES INSIDE TRAY
   *
   * @return STRING CONTAINS PRODUCT ID
   */
  public final String get_containing_product_id(){
    //return new Integer(_tray.peek().get_id()).toString();
    return this._item_id;
  }

  /**
   * get_max_tray_size METHOD (PUBLIC)
   *
   * SHOW THE MAX SIZE OF THE TRAY
   *
   * @return MAX SIZE OF THE TRAY
   */
  public final Integer get_max_tray_size(){
    return _TRAY_SIZE;
  }

  @Override
  public String toString(){
    String line1 = "NAME  : " + this._item_name + "\n";
    String line2 = "PRICE : " + this._item_price + "\n";
    String line3 = "ICON  : " + this._icon_dir + "\n";
    String line4 = "INV   : " + this._tray.size() + "\n";

    return line1 + line2 + line3 + line4;
  } // END toString


  ////////////////////////// TEST /////////////////////////////////////////////
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

    Tray t1 = new Tray(1);
    Tray t2 = new Tray(2);
    Tray t3 = new Tray(3);
    t1.push(soda);
    t2.push(snack);
    t3.push(book);

    System.out.println(t1.toString());
    System.out.println(t2.toString());
    System.out.println(t3.toString());
    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<");
    System.out.println("MAX SIZE : " + t1.get_max_tray_size());
    System.out.println("MAX SIZE : " + t2.get_max_tray_size());
    System.out.println("MAX SIZE : " + t3.get_max_tray_size());
    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    System.out.println("T1 item id : " + t1.get_containing_product_id());
    System.out.println("T2 item id : " + t2.get_containing_product_id());
    System.out.println("T3 item id : " + t3.get_containing_product_id());
    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    System.out.println("TEST get_tray_info METHOD : ");
    System.out.println(t1.get_tray_info());
    System.out.println(t2.get_tray_info());
    System.out.println(t3.get_tray_info());
    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    System.out.println("TEST get_item_info METHOD : ");
    System.out.println(t1.get_item_info());
    System.out.println(t2.get_item_info());
    System.out.println(t3.get_item_info());
    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    System.out.println("TEST PUSH AND POP");
    if(!t1.is_empty()){
      System.out.println("Not Empty : ");
      System.out.println(t1.get_tray_info());
    }
    Item temp = t1.pop();
    System.out.println("POP");
    System.out.println(temp.get_info());
    if(t1.is_empty()){
      System.out.println("EMPTY");
    }
    t1.push(soda);
    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    System.out.println("TEST get_item_price METHOD : ");
    System.out.println(t1.get_item_price());
    System.out.println(t2.get_item_price());
    System.out.println(t3.get_item_price());

    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    System.out.println("TESTING ItemInfoTuple");
    ItemInfoTuple iit = t1.get_item_tuple();
    System.out.println(iit.get(Index.NAME));
    System.out.println(iit.get(Index.PRICE));
    System.out.println(iit.get(Index.ICON_DIRECTORY));
  }

  /**
   * TO-DO:
   * FOR push METHOD, WHEN INSERTING AN ITEM INTO A TRAY, WE MIGHT NEED TO
   * VALIDATE IF IT IS A SAME ITEM AS THE ONE BEFORE
   */
}