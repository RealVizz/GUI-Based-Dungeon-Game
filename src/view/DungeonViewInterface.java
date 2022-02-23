package view;

import java.awt.Image;

import javax.swing.JFrame;

import model.DungeonInterface;

/**
 * The Interface to create a contract for the view (for GUI) for the project.
 */
public interface DungeonViewInterface {

  /**
   * Removes the default screen message.
   */
  void removeDefaultMessage();

  /**
   * Initialises all the tiles/buttons with blank/black image.
   *
   * @param rows The rows to set up.
   * @param cols The columns to set up.
   */
  void setFrameAllBlack(int rows, int cols);

  /**
   * Updates the reference of "dungeon" to the "view".
   *
   * @param d The DungeonInterface object.
   */
  void setDungeon(DungeonInterface d);

  /**
   * Shows the players current location in the GUI (frame).
   *
   * @param playerCurrentLocation The integer array of the location of the player.
   */
  void depictPlayerPos(int[] playerCurrentLocation);

  /**
   * Refreshes the view by revalidating and repainting it.
   */
  void refreshTheView();

  /**
   * Displays the message in the information panel of the frame.
   *
   * @param s The message string.
   */
  void showMessage(String s);

  /**
   * Disables the play buttons like Move/Pick/Shoot etc.
   */
  void disablePlayButtons();

  /**
   * Enables the play buttons like Move/Pick/Shoot etc.
   */
  void enablePlayButtons();

  /**
   * Return the frame of the GUI.
   *
   * @return The frame of the GUI.
   */
  JFrame getFrame();

  /**
   * When player gets dead, ut shows all the monsters hiding in the dungeon.
   */
  void showMonsters();

  /**
   * Displays the message in the information panel of the frame and a photo along it.
   *
   * @param s The message string.
   * @param img The photo / Image.
   */
  void showMessageWithPhoto(String s, Image img);
}
