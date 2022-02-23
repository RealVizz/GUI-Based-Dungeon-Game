package view.eventhandlers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//import javax.swing.*;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import controller.gui.GUIControllerInterface;

/**
 * The "Menu Bar" (Mouse) events handler class.
 */
public class MenuEvents extends MouseAdapter {
  GUIControllerInterface guiController;

  JMenuItem[] sizeOfDungeonOptions;
  JMenuItem[] interconnectivityOptions;
  JMenuItem[] warpingOptions;
  JMenuItem[] treasurePercOptions;
  JMenuItem[] monsterCountOptions;

  JMenu newGame;
  JMenu restartGame;
  JMenu quitGame;

  int[] sizeOfDungeon = new int[]{5, 5};
  int interconnectivity = 0;
  boolean warping = false;
  int treasurePerc = 50;
  int monsterCount = 1;

  /**
   * The construct initialises the data.
   *
   * @param newGame       The NewGame button object.
   * @param exit          The exit Button object.
   * @param guiController The controller object.
   * @param restartGame   The Restart button object.
   */
  public MenuEvents(JMenu newGame, JMenu exit, GUIControllerInterface guiController,
                    JMenu restartGame) {
    this.newGame = newGame;
    this.restartGame = restartGame;
    this.quitGame = exit;
    this.guiController = guiController;
  }

  @Override
  public void mousePressed(MouseEvent e) {
    JMenuItem c = (JMenuItem) e.getSource();
    for (JMenuItem item : sizeOfDungeonOptions) {
      if (e.getSource() == item) {
        String[] s = c.getText().strip().split("x");
        this.sizeOfDungeon[0] = Integer.parseInt(s[0]);
        this.sizeOfDungeon[1] = Integer.parseInt(s[1]);
        break;
      }
    }
    for (JMenuItem item : interconnectivityOptions) {
      if (c == item) {
        this.interconnectivity = Integer.parseInt(c.getText());
        break;
      }
    }
    for (JMenuItem item : warpingOptions) {
      if (c == item) {
        this.warping = c.getText().equalsIgnoreCase("true");
        break;
      }
    }
    for (JMenuItem item : treasurePercOptions) {
      if (c == item) {
        this.treasurePerc = Integer.parseInt(c.getText());
        break;
      }
    }
    for (JMenuItem item : monsterCountOptions) {
      if (c == item) {
        this.monsterCount = Integer.parseInt(c.getText());
        break;
      }
    }

    if (e.getSource() == quitGame) {
      System.out.println("Hope You Liked The Game.");
      System.exit(0);
    }

    if (e.getSource() == newGame) {
      int[] sizeOfDungeon = this.sizeOfDungeon;
      int interconnectivity = this.interconnectivity;
      boolean warping = this.warping;
      int treasurePerc = this.treasurePerc;
      int monsterCount = this.monsterCount;
      guiController.newGame(sizeOfDungeon, interconnectivity, warping, treasurePerc, monsterCount);
    }

    if (e.getSource() == restartGame) {
      guiController.restartGame(sizeOfDungeon, interconnectivity, warping, treasurePerc,
              monsterCount);
    }


  }

  //@Override
  //public void mouseClicked(MouseEvent e) {
  //}

  /**
   * Attaches the latest references of data to the current object.
   *
   * @param sizeOfDungeonOptions     The "size of dungeons options" button.
   * @param interconnectivityOptions The "interconnectivity" button.
   * @param warpingOptions           The "warping" button.
   * @param treasurePercOptions      The "treasure percentage" button.
   * @param monsterCountOptions      The "count of monsters in the dungeon" button.
   */
  public void updateItems(JMenuItem[] sizeOfDungeonOptions, JMenuItem[] interconnectivityOptions,
                          JMenuItem[] warpingOptions, JMenuItem[] treasurePercOptions,
                          JMenuItem[] monsterCountOptions) {
    this.sizeOfDungeonOptions = sizeOfDungeonOptions;
    this.interconnectivityOptions = interconnectivityOptions;
    this.warpingOptions = warpingOptions;
    this.treasurePercOptions = treasurePercOptions;
    this.monsterCountOptions = monsterCountOptions;
  }

  //  public int[] getSizeOfDungeon() {
  //    return sizeOfDungeon;
  //  }
  //
  //  public int getInterconnectivity() {
  //    return interconnectivity;
  //  }
  //
  //  public boolean isWarping() {
  //    return warping;
  //  }
  //
  //  public int getTreasurePerc() {
  //    return treasurePerc;
  //  }
  //
  //  public int getMonsterCount() {
  //    return monsterCount;
  //  }


}
