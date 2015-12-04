import java.util.Objects;

/**
 * Created by Lambda on 11/16/2015.
 */
public class Pair<L, R> {
  private final L _left;
  private final R _right;

  /**
   * CONSTRUCTOR
   *
   * TAKING TWO INPUT DATA AND GENERATE A PAIR OBJECT BASED ON THE INPUT
   *
   * @param item1 DATA
   * @param item2 DATA
   */
  public Pair(L item1, R item2){
    this._left  = item1;
    this._right = item2;
  } // END CONSTRUCTOR

  /**
   * SIMMPLE GETTER METHODS
   *
   * @return THE STORED DATA
   */
  public final L get_left(){
    return this._left;
  }
  public final R get_right(){
    return this._right;
  }

  /**
   * hashCode METHOD (PUBLIC)
   *
   * GENERATE THE HASH CODE BASED ON BOTH DATA STORED
   *
   * @return HASH CODE
   */
  @Override
  public int hashCode(){
    return this._left.hashCode() ^ this._right.hashCode();
  } // END hashCode METHOD

  /**
   * equals METHOD (PUBLIC)
   *
   * OVERRIDE COMPARISON METHOD
   *
   * @param o OBJECT THAT INDICATES ANOTHER PAIR OBJECT
   * @return TRUE MEANS THE TWO PAIR OBJECTS ARE IDENTICAL ON THE DATA STORED
   *         FALSE MEANS NOT IDENTICAL
   */
  @Override
  public boolean equals(Object o){
    if(!(o instanceof Pair)){
      return false;
    }
    else{
      Pair comparee = (Pair) o;
      return this._left.equals(comparee._left) &&
             this._right.equals(comparee._right);
    }
  } // END equals METHOD

  /**
   * toString METHOD (PUBLIC)
   *
   * OVERRIDE toString METHOD TO PROVIDE FORMATTED STRING WHICH IS REPRESENTING
   * THE PAIR OBJECT
   *
   * @return FORMATTED STRING
   */
  @Override
  public String toString(){
    return this._left.toString() + "@" + this._right.toString();
  }
}