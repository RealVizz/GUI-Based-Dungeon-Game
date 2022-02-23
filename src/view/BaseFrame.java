package view;

//import java.awt.*;
import java.awt.GridLayout;
import java.awt.FlowLayout;

//import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


import controller.gui.GUIControllerInterface;
import view.eventhandlers.MenuEvents;


/**
 * This class creates a frame with default starter messages, which the view later on uses
 * to do work on to show the data in a graphical manner.
 */
public class BaseFrame extends JFrame {
  GUIControllerInterface guiController;
  JMenuBar menuBar;

  JMenu gameSettings; // settings (grid size and all),
  JMenu newGame;
  JMenu restartGame;
  JMenu quitGame;

  JMenu sizeOfDungeonSUBMENU;
  JMenu interConnectivitySUBMENU;
  JMenu warpingSUBMENU;
  JMenu treasurePercSUBMENU;
  JMenu monsterCountSUBMENU;

  JMenuItem[] sizeOfDungeonOptions;
  JMenuItem[] interconnectivityOptions;
  JMenuItem[] warpingOptions;
  JMenuItem[] treasurePercOptions;
  JMenuItem[] monsterCountOptions;

  MenuEvents menuEvents;// = new MenuEvents();

  JPanel textPanel;


  /**
   * Sets up the basic frame and initialises the reference for its controller (GUI Controller).
   *
   * @param guiController The controller object.
   */
  public BaseFrame(GUIControllerInterface guiController) {
    this.setTitle("DUNGEON GAME : VISHESHANK MISHRA ");
    this.guiController = guiController;
    this.menuBar = new JMenuBar();

    this.gameSettings = new JMenu("Settings");
    this.newGame = new JMenu("New Game");
    this.restartGame = new JMenu("Restart Game");
    this.quitGame = new JMenu("Quit");

    this.sizeOfDungeonSUBMENU = new JMenu("Size Of Dungeon");
    this.interConnectivitySUBMENU = new JMenu("Inter-Connectivity");
    this.warpingSUBMENU = new JMenu("Is-Warping");
    this.treasurePercSUBMENU = new JMenu("Treasure Percentage");
    this.monsterCountSUBMENU = new JMenu("Monsters Count");

    basicOptionsSetup();
    setDefaultMessage();
    initialSetup();
  }

  private void basicOptionsSetup() {
    this.sizeOfDungeonOptions = new JMenuItem[6];
    this.interconnectivityOptions = new JMenuItem[6];
    this.warpingOptions = new JMenuItem[2];
    this.treasurePercOptions = new JMenuItem[7];
    this.monsterCountOptions = new JMenuItem[6];

    menuEvents = new MenuEvents(this.newGame, this.quitGame, this.guiController, this.restartGame);

    // Setting Up Values for options of "The Size Of Dungeon".
    for (int i = 0; i < this.sizeOfDungeonOptions.length; i++) {
      JMenuItem c = new JMenuItem((i + 5) + "x" + (i + 5));
      c.addMouseListener(this.menuEvents);
      sizeOfDungeonOptions[i] = c;
      sizeOfDungeonSUBMENU.add(c);
    }
    // Setting Up Values for options of "Interconnectivity".
    for (int i = 0; i < this.interconnectivityOptions.length; i++) {
      JMenuItem c = new JMenuItem(i + "");
      c.addMouseListener(this.menuEvents);
      interconnectivityOptions[i] = c;
      interConnectivitySUBMENU.add(c);
    }
    // Setting Up Values for options of "Warping options".
    for (int i = 0; i < this.warpingOptions.length; i++) {
      JMenuItem c = new JMenuItem(i % 2 == 0 ? "True" : "False");
      c.addMouseListener(this.menuEvents);
      warpingOptions[i] = c;
      warpingSUBMENU.add(c);
    }
    // Setting Up Values for options of "Treasure Percentage".
    for (int i = 0; i < this.treasurePercOptions.length; i++) {
      JMenuItem c = new JMenuItem(i * 10 + "");
      c.addMouseListener(this.menuEvents);
      treasurePercOptions[i] = c;
      treasurePercSUBMENU.add(c);
    }
    // Setting Up Values for options of "Monster Counts".
    for (int i = 0; i < this.monsterCountOptions.length; i++) {
      JMenuItem c = new JMenuItem(i + "");
      c.addMouseListener(this.menuEvents);
      monsterCountOptions[i] = c;
      monsterCountSUBMENU.add(c);
    }

    this.menuEvents.updateItems(this.sizeOfDungeonOptions, interconnectivityOptions, warpingOptions,
            treasurePercOptions, monsterCountOptions);

    this.newGame.addMouseListener(menuEvents);
    this.restartGame.addMouseListener(menuEvents);
    this.quitGame.addMouseListener(menuEvents);

  }

  private void initialSetup() {
    this.gameSettings.add(this.sizeOfDungeonSUBMENU);
    this.gameSettings.add(this.interConnectivitySUBMENU);
    this.gameSettings.add(this.warpingSUBMENU);
    this.gameSettings.add(this.treasurePercSUBMENU);
    this.gameSettings.add(this.monsterCountSUBMENU);

    this.menuBar.add(gameSettings);
    this.menuBar.add(newGame);
    this.menuBar.add(restartGame);
    this.menuBar.add(quitGame);

    this.setJMenuBar(this.menuBar);


    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(800, 400);
    this.setLayout(new FlowLayout());

    this.setVisible(true);
  }

  private void setDefaultMessage() {
    int c = SwingConstants.CENTER;
    int l = SwingConstants.LEFT;

    textPanel = new JPanel();

    textPanel.setLayout(new GridLayout(0, 1));

    textPanel.add(new JLabel("Hello there.", c));
    textPanel.add(new JLabel("Please read below for basic instructions", c));
    textPanel.add(new JLabel("Click on the top left 'Settings' menu for game options.", c));
    textPanel.add(new JLabel("Game options include 'Size of Dungeon', 'interconnectivity', "
            + "'Warping allowed', 'Treasure Percentage' & 'Monster Count'.", c));
    textPanel.add(new JLabel("                                                ", c));

    textPanel.add(new JLabel("SIZE --> The amount of rows and columns in the dungeon.", l));
    textPanel.add(new JLabel("INTERCONNECTIVITY --> The total paths to reach the end.", l));
    textPanel.add(new JLabel("WARPING --> Will boundaries interconnect.              ", l));
    textPanel.add(new JLabel("TREASURE PERCENTAGE --> The number of areas having arrow or "
            + "treasures in the dungeon.", l));
    //basicPanel.add(new JLabel("MONSTER COUNT --> How many monsters will be there.", l));

    textPanel.add(new JLabel("MONSTER COUNT --> How many monsters will be there.", l));
    textPanel.add(new JLabel("                                                ", c));

    textPanel.add(new JLabel("If not customised, the default values will be chosen.", c));
    textPanel.add(new JLabel("DungeonSize--5x5 | Interconnectivity--0 | Warping--False | "
            + "Treasue Percentage--50 | Monsters--1", c));
    textPanel.add(new JLabel("                                                ", c));
    textPanel.add(new JLabel("After you select them, hit 'New Game', game will start.", c));
    textPanel.add(new JLabel("Once the game is started, look for underscore on text of a "
            + "button, that is the shortcut key for that button.", c));
    textPanel.add(new JLabel("More shortcut keys are there, read 'README' file for that.", c));
    textPanel.add(new JLabel("Hit 'Restart Game' if you want to start from beginning.", c));
    textPanel.add(new JLabel("Hit 'New Game' if you want to play on fresh dungeon.", c));
    textPanel.add(new JLabel("Hit 'Quit' if you want to close the game.", c));


    this.add(textPanel);
  }

  public void removeDefaultMessage() {
    this.remove(textPanel);
    this.repaint();
  }

}
