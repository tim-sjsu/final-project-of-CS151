/**
 * Created by Lambda on 11/15/2015.
 */

/**
 * ItemInfoTuple
 *
 * THIS CLASS IS PROVIDING BASIC COLLECTION FUNCTIONALITY TO THE SYSTEM. IT WILL
 * STORE THREE STRING OBJECTS: NAME, PRICE, DIRECTORY TO THE ICON IMAGE
 */
public class ItemInfoTuple {
  private String _name  = null;
  private String _price = null;
  private String _dir   = null;

  /**
   * CONSTRUCTOR
   *
   * @param nm ITEM NAME
   * @param pc ITEM PRICE
   * @param dr ITEM IMAGE DIRECTORY
   */
  public ItemInfoTuple(String nm, String pc, String dr){
    this._name  = nm;
    this._price = pc;
    this._dir   = dr;
  } // END CONSTRUCTOR

  /**
   * get METHOD (PUBLIC)
   *
   * SIMPLE GETTER METHOD THAT ALLOWS USER TO ACCESS THE SPECIFIC FIELD
   *
   * @param idx INDEX OF THE FIELD
   * @return A STRING THAT CORRESPONDING TO THE INDEX USER PROVIDED
   */
  public String get(Index idx){
    switch(idx){
      case NAME:
        return this._name;
      case PRICE:
        return this._price;
      case ICON_DIRECTORY:
        return this._dir;
      default:
        return null;
    } // END SWITCH STATEMENT
  } // END get METHOD

  @Override
  public String toString(){
    String temp = "";
    temp += "NAME     : " + this._name + "\n";
    temp += "PRICE    : " + this._price + "\n";
    temp += "ICON DIR : " + this._dir + "\n";
    return temp;
  }
}