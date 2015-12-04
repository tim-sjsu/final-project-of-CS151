import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lambda on 11/25/2015.
 */
public class DummyDatabase extends DatabaseConnection{
  private ArrayList<String> _dummy_item_data = null;
  private HashMap<Integer, String> _dummy_student_data = null;
  private HashMap<Integer, Double> _dummy_balance_data = null;

  public DummyDatabase(){
    this._dummy_item_data = init_item_input();
    this._dummy_student_data = init_stdt_input();
    this._dummy_balance_data = init_balance_input();
  }

  public String getStudentPassword(int inputID){
    if(this._dummy_student_data.containsKey(inputID)){
      return this._dummy_student_data.get(inputID);
    }
    return null;
  }

  public double getStudentBalance(int inputID){
    if(this._dummy_balance_data.containsKey(inputID)){
      return this._dummy_balance_data.get(inputID);
    }
    return -1;
  }

  // displays the data of Student table
  public String getStudentData(int studentID, String password){
    return "TEST DUMMY DATA";
  }

  // ----> checkItemLeave method (no need)

  public String getItemData(String itemID){
    for(String str : this._dummy_item_data){
      String[] fields = str.split(":");
      if(fields[1].compareTo(itemID) == 0){
        return str;
      }
    }
    return null;
  }

  public ArrayList<String> getAllItemInformation(){
    return this._dummy_item_data;
  }

  // getMoneyBoxData method (no need)?

  public void updateStudent(int IDnumber, double newData){
    if(this._dummy_balance_data.containsKey(IDnumber)){
      this._dummy_balance_data.replace(IDnumber, newData);
    }
  }

  // updateItems method ??

  public HashMap<Integer, Double> init_balance_input(){
    HashMap<Integer, Double> map = new HashMap<>();
    map.put(1, 100.00);
    map.put(2, 200.00);
    map.put(3, 300.00);
    return map;
  }

  public HashMap<Integer, String> init_stdt_input(){
    String pwd1 = "1234";
    String pwd2 = "2345";
    String pwd3 = "3456";
    HashMap<Integer, String> hash_table = new HashMap<>();
    hash_table.put(1, pwd1);
    hash_table.put(2, pwd2);
    hash_table.put(3, pwd3);

    return hash_table;
  }

  public ArrayList<String> init_item_input() {
    String item0  = "coke:1:1.75:6:coke.png:null:null:SODA:160:";
    String item1  = "pepsi:2:1.75:6:pepsi.png:null:null:SODA:160:";
    String item2  = "beer:3:1.75:6:beer.png:null:null:SODA:155:";
    String item3  = "fanta:4:2.25:6:fanta.png:null:null:SODA:170:";
    String item4  = "pretzel:5:1.75:6:pretzel.png:null:null:SNACK:250:";
    String item5  = "book:6:9.21:6:book.png:Smith:1111:GRADE_BOOK:null:";
    String item6  = "egg:7:1.75:6:egg.png:null:null:SNACK:160:";
    String item7  = "sandwich:8:1.75:6:sandwich.png:null:null:SNACK:140:";
    String item8  = "hamburger:9:1.25:6:hamburger.png:null:null:SNACK:110:";
    String item9  = "donut:10:1.5:10:donut.png:null:null:SNACK:200:";
    String item10 = "grade_book1:11:11.11:6:grade_book1.png:Thomas:1212:GRADE_BOOK:null:";
    String item13 = "heineken:12:1.75:12:heineken.png:::SODA:200:";

    ArrayList<String> input = new ArrayList<>(12);
    input.add(item0);
    input.add(item1);
    input.add(item2);
    input.add(item3);
    input.add(item4);
    input.add(item5);
    input.add(item6);
    input.add(item7);
    input.add(item8);
    input.add(item9);
    input.add(item10);
    input.add(item13);
    //input.add(item14);

    return input;
  }

  public void parseString(ArrayList<String> input){
    for(String str : input){
      String[] arr = str.split(":");
      System.out.println("====================================");
      System.out.println("Size : " + arr.length);
      for(String token : arr){
        System.out.print("[" + token + "], ");
      }
      System.out.println();
      System.out.println("------------------------------------");
    }
  }

  // TESTED OK!
  public static void main(String[] args) {
    DummyDatabase dd = new DummyDatabase();
    for(String str : dd.getAllItemInformation()){
      System.out.println(str);
    }
    System.out.println("-------------------------------------------");
    for(int idx=1; idx<15; idx++){
      String str = dd.getItemData(Integer.toString(idx));
      if(str != null){
        System.out.println(str);
      }
      else {
        System.out.println("ID NOT EXISTS");
      }
    }
    System.out.println("-------------------------------------------");

  }
}
