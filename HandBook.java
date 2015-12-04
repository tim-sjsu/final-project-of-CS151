/**
 * Created by Lambda on 11/15/2015.
 */
public class HandBook extends Item {
  private String _author;
  private String _isbn;

  /**
   * CONSTRUCTOR
   *
   * TAKE INPUT AND CONSTRUCT A HANDBOOK OBJECT
   *
   * @param item_name ITEM NAME (STRING)
   * @param price     ITEM PRICE (STRING)
   * @param id        PRODUCT ID (STRING)
   * @param author    AUTHOR NAME (STRING)
   * @param isbn      ISBN NUMBER (STRING)
   */
  public HandBook(String item_name, String price, String id,
                  String author,String isbn, String path, ItemType it){
    super(item_name, price, id, path, it);
    this._author = (author != null)? author: "ANONYMOUS";
    this._isbn = (isbn != null)? isbn: "DEFAULT ISBN";
  } // END CONSTRUCTOR

  /**
   * get_info METHOD PUBLIC
   *
   * RETURN A STRING THAT CONTAINS THE INFORMATION ABOUT THIS ITEM
   *
   * @return INFORMATION STRING
   */
  @Override
  public String get_info(){
    String name_fmt   = "Name : %s. ";
    String price_fmt  = "Price : %s. ";
    String author_fmt = "Author : %s. ";
    String isbn_fmt   = "ISBN : %s. ";

    return
        String.format(name_fmt, this._name)+
        String.format(price_fmt,
            CurrencyConvertor.convert_to_USD_fmt(this._price))+
        String.format(author_fmt, this._author)+
        String.format(isbn_fmt, this._isbn);
  }
}