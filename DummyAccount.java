/**
 * Created by Lambda on 11/15/2015.
 */

public class DummyAccount extends Student{
  String student_id = "1";
  String student_pwd = "1";
  double student_balance = 100.00;
  
  public DummyAccount(){
	  super(null);
  }
  
  @Override
  public boolean checkID(String usrID, String usrPWD){
    if(usrID.compareTo(student_id) == 0 &&
        usrPWD.compareTo(student_pwd) == 0) {
      return true;
    }
    return false;
  }

  @Override
  public boolean pay(String id, double due_amount){
    // CHANGE BALANCE HERE
    if(id.compareTo(this.student_id) == 0 &&
        this.student_balance > due_amount) {
      return true;
    }
    return false;
  }

  @Override
  public double getBal(String id){
    if(id.compareTo(this.student_id)== 0){
      return this.student_balance;
    }
    return -1;
  } // get_balance

  @Override
  public String getStudentInfo(String id, String pwd){
    return "DUMMY STUDENT INFO";
  }
}