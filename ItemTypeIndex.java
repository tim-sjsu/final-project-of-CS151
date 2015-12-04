/**
 * Created by Lambda on 11/22/2015.
 */

/**
 * ENUMERATE CLASS - ITEM TYPE INDEX
 *
 * THIS CLASS IS MEANT FOR REPLACING THE INDEX THAT USED FOR THE INPUT ARRAY
 * FROM DATABASE TO A MORE READABLE FORMAT.
 */
public enum ItemTypeIndex {
  ITEM_NAME    (0),
  ITEM_ID      (1),
  ITEM_PRICE   (2),
  NUM_OF_ITEMS (3),
  ICON_DIR     (4),
  BK_AUTHOR    (5),
  ISBN         (6),
  ITEM_TYPE    (7),
  CALORIES     (8)
  ;
  private final int INDEX;

  /**
   * CONSTRUCTOR FOR THE int VALUE
   *
   * @param idx ACTUAL INDEX
   */
  ItemTypeIndex(int idx){
    this.INDEX = idx;
  }

  /**
   * get_index METHOD (PUBLIC)
   *
   * SIMPLE GETTER THAT ALLOWS USER TO GET THE ACTUAL INDEX
   *
   * @return INTEGER VALUE INDICATES ACTUAL INDEX VALUE
   */
  public int get_index(){
    return this.INDEX;
  }
}
