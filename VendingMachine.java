import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Lambda on 11/15/2015.
 */
public class VendingMachine extends Observable{
  private static final int       _NUM_OF_TRAYS = 12;
  private static ArrayList<Tray> _tray_lists   = new ArrayList<>(_NUM_OF_TRAYS);
  private int                    _vm_num       = -1;
  private static MoneyBox        _money_box    = new MoneyBox();
  private Student                _student_ref  = null;
  private ArrayList<Transaction> _transactions = null;
  private DatabaseConnection     _sql_handler  = null;

  /**
   * itemName:itemID:price:numOfLeft:path:author:isbn:itemYpe:calories
   * 0        1      2     3         4    5      6    7       8
   */
  /**
   * _occupy_tray METHOD (PRIVATE)
   *
   * BASED ON THE INPUT STRING ARRAY, INITIALIZE THE TRAY
   *
   * @param info    AN INPUT STRING ARRAY THAT CONTAINS NECESSARY INFO FOR ITEM
   * @param tray_id TRAY IDENTIFICATION CODE
   * @return THE TRAY OBJECT
   */
  private Tray _occupy_tray(String[] info, int tray_id){
    Tray tray = new Tray(tray_id);
    int num_left = Integer.parseInt(
        info[ItemTypeIndex.NUM_OF_ITEMS.get_index()]
    );
    for(int i=0; i<num_left; i++){
      switch (ItemType.valueOf(info[ItemTypeIndex.ITEM_TYPE.get_index()])){
        case SODA:
          tray.push(new Refreshment(
              info[ItemTypeIndex.ITEM_NAME.get_index()],
              info[ItemTypeIndex.ITEM_PRICE.get_index()],
              info[ItemTypeIndex.ITEM_ID.get_index()],
              info[ItemTypeIndex.CALORIES.get_index()],
              info[ItemTypeIndex.ICON_DIR.get_index()],
              ItemType.valueOf(info[ItemTypeIndex.ITEM_TYPE.get_index()])
          ));
          break;
        case SNACK:
          tray.push(new Refreshment(
              info[ItemTypeIndex.ITEM_NAME.get_index()],
              info[ItemTypeIndex.ITEM_PRICE.get_index()],
              info[ItemTypeIndex.ITEM_ID.get_index()],
              info[ItemTypeIndex.CALORIES.get_index()],
              info[ItemTypeIndex.ICON_DIR.get_index()],
              ItemType.valueOf(info[ItemTypeIndex.ITEM_TYPE.get_index()])
          ));
          break;
        case GRADE_BOOK:
          tray.push(new HandBook(
              info[ItemTypeIndex.ITEM_NAME.get_index()],
              info[ItemTypeIndex.ITEM_PRICE.get_index()],
              info[ItemTypeIndex.ITEM_ID.get_index()],
              info[ItemTypeIndex.BK_AUTHOR.get_index()],
              info[ItemTypeIndex.ISBN.get_index()],
              info[ItemTypeIndex.ICON_DIR.get_index()],
              ItemType.valueOf(info[ItemTypeIndex.ITEM_TYPE.get_index()])
          ));
          break;
        default:
          System.out.println("[ERROR] UNKNOW ITEM TYPE");
      }
    }
    return tray;
  }

  /**
   * _init METHOD (PRIVATE)
   *
   * INITIALIZE ALL TRAYS INSIDE VENDING MACHINE, INPUT STRING WOULD LOOKS LIKE:
   * itemName:itemID:price:numOfLeft:path:author:isbn:itemYpe:calories
   * 0        1      2     3         4    5      6    7       8
   * WE'RE USING split() STRING METHOD TO SEPARATE EACH FIELD OUT.
   */
  private void _init(){
    /**
     * INITIALIZE TRAYS
     */
    int index = 0;
    try {
      ArrayList<String> all_items = this._sql_handler.getAllItemInformation();
      for(String str : all_items){
        String[] elements = str.split(":");
        _tray_lists.add(
            index,
            this._occupy_tray(elements, index)
        );
        index++;
      } // END FOR LOOP
    }
    catch (Exception e) {
      e.printStackTrace();
    }


  }

  /**
   * CONSTRUCTOR OF VENDING MACHINE CLASS
   *
   * TAKING INPUTS FROM USER AND INITIALIZE THE WHOLE VENDING MACHINE SYSTEM
   *
   * @param vm_id VENDING MACHINE ID
   * @param cnnct DATABASE REFERENCE
   */
  public VendingMachine(int vm_id, Student student, DatabaseConnection cnnct){
    this._vm_num       = vm_id;
    this._student_ref  = student;
    this._transactions = new ArrayList<>();
    this._sql_handler  = cnnct;
    this._init(); // INITIALIZE VENDING MACHINE WITH ITEMS FROM SQL
  }

  // GUI RELATED

  /**
   * log_in METHOD PUBLIC
   *
   * USER LOG IN - CREATE A STUDENT OBJECT AND SAVE IT FOR LATER USE. IF EITHER
   * ID OR PASSWORD IS INCORRECT, RETURN FALSE ELSE CREATE STUDENT OBJECT AND
   * RETURN TRUE
   *
   * @param id  STUDENT ID NUMBER (STRING)
   * @param pwd STUDENT PASSWORD (STRING)
   * @return TRUE/FALSE INDICATES IF THE ID AND PASSWORD ARE CORRECT
   */
  public boolean log_in(String id, String pwd){
    try {
      if(this._student_ref.checkID(id, pwd)){
        //this._curr_stdt_ref = this._account_hdlr.create_section(id);
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return false;
  }

  /**
   *  get_item_info MTHOD (PUBLIC)
   *
   * THIS METHOD PROVIDES GUI ACCESS TO THE ITEM INFORMATION FOR HOVER DISPLAY.
   * FOR GUI PARTS OF SYSTEM
   *
   * @param tray_id TRAY ID (12 IN TOTAL)
   * @return A STRING THAT CONTAINS CURRENT TRAY INFORMATION
   */
  public String get_item_info(int tray_id){
    Tray tray_ref = null;
    if(tray_id < _NUM_OF_TRAYS && tray_id >= 0) {
      tray_ref = this._tray_lists.get(tray_id);
      return tray_ref.get_item_info();
    }
    //throw new ArrayIndexOutOfBoundsException("TRAY LIST OUT OF BOUND.\n");
    return null;
  }

  /**
   * get_tray_item_info METHOD (PUBLIC)
   *
   * THIS METHOD PROVIDE THE NECESSARY INFORMATION ABOUT THE ITEM STORES IN
   * ALL TRAYS - FOR GUI (BUILDING ITEM LIST)
   *
   * @return THE ARRAYLIST OF ITEM-INFO-TUPLE WHICH CONTAINS NAME, PRICE AND
   *         THE DIRECTORY TO THE ICON IMAGE
   */
  public ArrayList<ItemInfoTuple> get_tray_item_info(){
    ArrayList<ItemInfoTuple> list = new ArrayList<>(_NUM_OF_TRAYS);
    int idx = 0;
    for(Tray t : _tray_lists){
      if(t != null) {
        list.add(idx, t.get_item_tuple());
      }
      else{
        list.add(idx, null);
      }
      idx++;
    }
    return list;
  }

  /**
   * calculate_total METHOD (PUBLIC)
   *
   * calculate_total IS A METHOD PROVIDED FOR GUI TO GET THE CURRENT TOTAL
   * PRICE WITH ALL THE ITEMS SELECTED BY USER
   *
   * @param item_list THE LIST OF ITEMS SELECTED
   * @return TOTAL PRICE IN BIGDECIMAL TYPE
   */
  public BigDecimal calculate_total(ArrayList<Integer> item_list){
    //System.out.println(item_list.size());
    BigDecimal total = new BigDecimal(0.0);
    for(Integer i : item_list){
      total = total.add(_tray_lists.get(i-1).get_item_price());
    }
    return total;
  }

  // TO-DO:
  // add String id to the parameter list

  /**
   * check_out METHOD (PUBLIC)
   *
   * check_out METHOD COOPERATES WITH GUI, WHEN USER CLICK PAY BUTTON AT THE
   * GUI PANEL, THE GUI WILL CALL THIS METHOD WITH THE LIST OF ITEM SELECTED
   * AND ACCORDING TO THE PRICE OF EACH ITEM SELECTED, IT WOULD ASK THE
   * PROPER AMOUNT OF MONEY FROM THE ACCOUNT CLASS AND RETURN THE LIST OF
   * ITEM TO GUI (REPRESENTING THE COMPLETION OF A TRANSACTION)
   * NOTE:
   *  THE METHOD WILL AUTOMATICALLY VALIDATE THE INVENTORY AFTER EVERY
   *  TRANSACTION. IF THE INVENTORY IS TOO LOW, IT WOULD NOTIFY THE OBSERVER
   *  CLASS AND OBSERVER CLASS WILL SEND THE ORDER REQUESTS TO THE SERVER.
   *
   * @param id             STUDENT ID
   * @param selected_items LIST OF ITEMS SELECTED( ONLY TRAY ID)
   * @return LIST OF ITEMS (PAID)
   */
  public ArrayList<Item> check_out(String id, ArrayList<Integer> selected_items){
    BigDecimal total_price = this.calculate_total(selected_items);
    /**
     * VALIDATE THE USER BALANCE
     */
    try {
      if(this._student_ref.pay(id, total_price.doubleValue())){
        // BALANCE VALIDATED
        this._money_box.collect_payment(total_price);
        // DELIVER
        ArrayList<Item> deliver_items = new ArrayList<>();
        for(Integer i : selected_items){
          deliver_items.add(_tray_lists.get(i-1).pop());
        } // END FOR LOOP

        // RECORD TRANSACTION
        this._transactions.add(new Transaction(
            id,
            CurrencyConvertor.conver_to_USD_fmt(total_price.doubleValue()),
            deliver_items
        ));

        // SELF CHECK IF INVENTORY IS LOW
        if(this._is_inventory_low()){
          this.setChanged();
          this.notifyObservers();
        }

        return deliver_items;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    // INSUFFICIENT AMOUNT
    return null;
  } // END check_out METHOD



  /**
   * _is_inventory_low METHOD (PRIVATE)
   *
   * THE METHOD WILL ITERATE THROUGH ALL TRAYS, AND RECORDS TWO SCENARIOS:
   *  (1) TRAY IS EMPTY
   *  (2) TRAY HAS LESS THAN 6 ITEMS
   * THEN, IF EMPTY TRAYS ARE MORE THAN THREE OR THE LESS THAN SIX ITEMS TRAYS
   * ARE MORE THAN SIX TRAYS, IT WILL RETURN A TRUE, VISE VERSA.
   *
   * @return TRUE: INVENTORY IS LOW - NEED TO RESTOCK
   *         FALSE: INVENTORY IS NOT LOW
   */
  private boolean _is_inventory_low(){
    int empty_counter = 0;
    int low_inv_cntr  = 0;

    for(Tray t : _tray_lists){
      switch (t.get_number_of_remaining_item()){
        case 0:
          empty_counter++;
          break;
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
          low_inv_cntr++;
          break;
        default:
      } // END SWITCH
    } // END FOR

    return (empty_counter >= 3 || low_inv_cntr >= 6)? true: false;
  } // END is_inventory_low METHOD

  /**
   * restock_check METHOD (PUBLIC)
   *
   * GENERATE THE ITEMS THAT NEEDS TO BE RESTOCKED.
   *
   * @return ARRAYLIST OF PAIR OBJECTS: PRODUCT ID + NUMBERS OF ITEMS
   */
  public ArrayList<Pair<String, Integer>> restock_check(){
    ArrayList<Pair<String, Integer>> restock_list = new ArrayList<>();
    for(Tray t : _tray_lists){
      if(t.get_number_of_remaining_item() <=5){
        restock_list.add(new Pair<>(
            t.get_containing_product_id(),
            t.get_max_tray_size()-t.get_number_of_remaining_item()
        ));
      } // END IF
    } // END FOR
    return restock_list;
  } // END restock_check METHOD

  /////////////////////////// TESTING PURPOSE /////////////////////////////////

  /**
   * _display_collection METHOD (PRIVATE)
   *
   * ITERATE THROUGH EVERY TRAYS INSIDE VENDING MACHINE AND PRINT OUT THEIR
   * BASIC INFORMATION
   */
  private void _display_collection(){
    for(Tray t : _tray_lists){
      System.out.println(t.get_tray_info());
    }
  }

  /**
   * get_usr_balance METHOD (PUBLIC)
   *
   * THIS METHOD PROVIDE GUI THE ACCESS TO THE USER BALANCE CORRESPONDING TO
   * THE STUDENT ID
   *
   * @param id STUDENT ID
   * @return BigDeciaml OBJECT
   */
  public BigDecimal get_usr_balance(String id){
    double bal = 0.0;
    try {
      bal = this._student_ref.getBal(id);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return new BigDecimal(bal);
  }
  
  ////////////////////////////// TESTING //////////////////////////////////////
  public static void main(String[] args) {
	  DatabaseConnection database = new DatabaseConnection();
    VendingMachine vm = new VendingMachine(1, new Student(database), database);
    VMGUI gui = new VMGUI(vm);
    
    //vm._display_collection();
    //System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<");
    //System.out.println("TEST get_item_info");
    //for(int i=0; i<12; i++){
    //  System.out.println(vm.get_item_info(i));
    //}
    //System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<");
    //System.out.println("TEST get_tray_item_info");
    //ArrayList<ItemInfoTuple> iif_arr = vm.get_tray_item_info();
    //for(ItemInfoTuple iit : iif_arr){
    //  System.out.println(iit.toString());
    //}
    //System.out.println("<<<<<<<<<<<<<<<<<<<<<<<");
    //System.out.println("TEST calculate_total");
    //ArrayList<Integer> selected_items = new ArrayList<>();
    //selected_items.add(0);
    //selected_items.add(1);
    //selected_items.add(2);
    //selected_items.add(3);
    //selected_items.add(4);
    //selected_items.add(5);
    //selected_items.add(6);
    //selected_items.add(7);
    //selected_items.add(10);
    //System.out.print("Total : ");
    //System.out.println(
    //    CurrencyConvertor.convert_to_USD_fmt(vm.calculate_total(selected_items))
    //);
    //System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<");
    //System.out.println("TEST check_out");
    //ServiceCenter sc = null;
    //try {
    //  sc = new ServiceCenter("localhost", 8080);
    //} catch (IOException e) {
    //  e.printStackTrace();
    //}
    //vm.addObserver(sc);
    //vm.check_out("1", selected_items);
    //System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    //System.out.println("TEST RESTOCK METHOD ");
    //ArrayList<Pair<String, Integer>> restock_list = vm.restock_check();
    //for(Pair<String, Integer> p : restock_list){
    //  System.out.println(p);
    //}
    ///////////////////// TESTING SERVER //////////////////////////////////////
    //RequestServer rs = new RequestServer("localhost", 8080);
    ServiceCenter sc = null;
    try {
      sc = new ServiceCenter("localhost", 8080);
    } catch (IOException e) {
      e.printStackTrace();
    }
    vm.addObserver(sc);
    //vm.check_out("1", selected_items);
  }
}