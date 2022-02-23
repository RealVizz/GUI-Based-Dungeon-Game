package controller.gui;

import model.DungeonInterface;
import view.DungeonViewInterface;

/**
 * The Interface to create a contract for the controller (for GUI) for the project.
 */
public interface GUIControllerInterface {

  /**
   * Execute a single game of Dungeon, given a Dungeon Model.
   *
   * @param d a non-null Dungeon Model.
   */
  void playGame(DungeonInterface d);

  /**
   * creates a new Game, based on user preference inputs.
   *
   * @param sizeOfDungeon The rows x columns size of dungeon.
   * @param interconnectivity The number of total paths for the dungeon.
   * @param warping  Can we go through walls or not (boolean)?
   * @param treasurePerc The amount of caves that will have items in it.
   * @param monsterCount The number of monsters in the dungeon.
   */
  void newGame(int[] sizeOfDungeon, int interconnectivity, boolean warping,
               int treasurePerc, int monsterCount);

  /**
   * Restarts the current game from inception.
   *
   * @param sizeOfDungeon The rows x columns size of dungeon.
   * @param interconnectivity The number of total paths for the dungeon.
   * @param warping Can we go through walls or not (boolean)?
   * @param treasurePerc The amount of caves that will have items in it.
   * @param monsterCount The number of monsters in the dungeon.
   */
  void restartGame(int[] sizeOfDungeon, int interconnectivity, boolean warping,
                   int treasurePerc, int monsterCount);

  /**
   * Refreshes the GUI to show the latest data.
   *
   * @param dvi The Dungeon View Interface.
   */
  void updateView(DungeonViewInterface dvi);

  /**
   * The player picks all items in the current node.
   */
  void pickAllItems();

  /**
   * Makes player move 1 step in given direction.
   *
   * @param direction The direction (N/E/W/S || R/L/U/D).
   */
  void playerMove(String direction);

  /**
   * Makes player shoot at given distance in given direction.
   *
   * @param distance The direction (1 to 5).
   * @param direction The direction (N/E/W/S || R/L/U/D).
   */
  void playerShoot(int distance, String direction);
}
