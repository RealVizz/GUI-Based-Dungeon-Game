package model;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The treasure class represents a treasure it can have, from the given strict,
 * which is complied by the Model.TreasureContent Enum.
 */
public class Treasure implements Serializable {
  private final TreasureContent treasure;

  /**
   * Initialises the value of the newly created Model.Treasure object.
   *
   * @param t The Model.TreasureContent object.
   */
  public Treasure(TreasureContent t) {
    this.treasure = t;
  }


  /**
   * Randomly Initialises the value of the newly created Model.Treasure object.
   * Also, Randomly Initialises how many of the items will be present in that object.
   */
  public Treasure() {
    List<TreasureContent> treasureContentList = List.of(TreasureContent.values());

    // treasureContentList.size() -1  --> so that last item "NONE" does not get selected.
    int randNum = ThreadLocalRandom.current().nextInt(treasureContentList.size() - 1);
    this.treasure = treasureContentList.get(randNum);
  }


  /**
   * The treasure object.
   *
   * @return The treasure object.
   */
  public TreasureContent getTreasure() {
    return this.treasure;
  }

  @Override
  public String toString() {
    return treasure.toString();
  }
}
