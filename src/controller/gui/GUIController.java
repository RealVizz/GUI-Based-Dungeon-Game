package controller.gui;

//import java.awt.*;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import model.CaveTunnelInterface;
import model.Dungeon;
import model.DungeonInterface;
import model.Player;
import model.Treasure;
import model.TreasureContent;
import model.WeaponEnum;
import view.DungeonViewInterface;

/**
 * The controller (for Graphics User Interface) class for the Dungeon Game.
 */
public class GUIController implements GUIControllerInterface {
  DungeonViewInterface view;
  DungeonInterface dungeon;
  DungeonInterface dungeonCopy;

  int rowsOfDungeon;
  int colsOfDungeon;


  @Override
  public void playGame(DungeonInterface d) {
    view.removeDefaultMessage();
    view.setDungeon(d);
    view.setFrameAllBlack(rowsOfDungeon, colsOfDungeon);

    view.depictPlayerPos(d.getPlayerCurrentLocation());
    view.enablePlayButtons();

    //System.out.println(dungeon);
    view.refreshTheView();
  }

  @Override
  public void newGame(int[] sizeOfDungeon, int interconnectivity, boolean warping,
                      int treasurePerc, int monsterCount) {

    StringBuilder sizeRxC = new StringBuilder();
    sizeRxC.append(sizeOfDungeon[0]).append("x").append(sizeOfDungeon[1]);
    rowsOfDungeon = sizeOfDungeon[0];
    colsOfDungeon = sizeOfDungeon[1];
    Player p1 = new Player();
    //System.out.println(sizeRxC + " " + treasurePerc + " " + interconnectivity + " " + warping +
    //      " " + monsterCount);
    try {
      dungeon = new Dungeon(sizeRxC.toString(), treasurePerc, interconnectivity, p1, warping,
              monsterCount);
      //dungeonCopy = new Dungeon((Dungeon) dungeon);
      dungeonCopy = (Dungeon) deepCopyObject(dungeon);
      playGame(dungeon);
    } catch (IllegalArgumentException e) { // My written.
      view.showMessage(e.getMessage());
    } catch (IOException | ClassNotFoundException e) { // Ashutosh Help.
      e.printStackTrace();
    }


  }

  @Override
  public void restartGame(int[] sizeOfDungeon, int interconnectivity, boolean warping,
                          int treasurePerc, int monsterCount) {

    if (dungeonCopy == null) {
      newGame(sizeOfDungeon, interconnectivity, warping, treasurePerc, monsterCount);
    } else {
      try {
        dungeon = (Dungeon) deepCopyObject(dungeonCopy);
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
      }
      playGame(dungeon);
    }


  }

  public void updateView(DungeonViewInterface view) {
    this.view = view;
  }


  private String generatePickMessage() {
    int[] currLoc = dungeon.getPlayer().getCurrLoc();
    CaveTunnelInterface currNode = dungeon.getCaveTunnelFromLocation(currLoc);
    StringBuilder pickSB = new StringBuilder();
    List<WeaponEnum> weaponList = currNode.getWeaponsList();
    List<Treasure> treasureList = currNode.getTreasureList();

    int arrowsCount = weaponList.size();
    int diamondsCount = 0;
    int emeraldsCount = 0;
    int rubiesCount = 0;

    for (Treasure t : treasureList) {
      if (t.getTreasure() == TreasureContent.RUBIES) {
        rubiesCount += 1;
      } else if (t.getTreasure() == TreasureContent.DIAMONDS) {
        diamondsCount += 1;
      } else if (t.getTreasure() == TreasureContent.EMERALD) {
        emeraldsCount += 1;
      }
    }

    pickSB.append("Player Picked : ");
    if (weaponList.size() == 0 && treasureList.size() == 0) {
      pickSB.append("Nothing.");
    } else {
      pickSB.append(diamondsCount).append(" Diamonds, ");
      pickSB.append(emeraldsCount).append(" Emeralds, ");
      pickSB.append(rubiesCount).append(" Rubies, ");
      pickSB.append(arrowsCount).append(" Arrows.");
    }

    return pickSB.toString();
  }

  @Override
  public void playerMove(String direction) {
    int[] currLoc = dungeon.getPlayer().getCurrLoc();
    int[] endLoc = dungeon.getEndPosition();
    CaveTunnelInterface currNode = dungeon.getCaveTunnelFromLocation(currLoc);
    CaveTunnelInterface nodeToGo = null;
    switch (direction) {
      case "R":
        nodeToGo = currNode.getRightNode();
        break;
      case "L":
        nodeToGo = currNode.getLeftNode();
        break;
      case "U":
        nodeToGo = currNode.getUpNode();
        break;
      case "D":
        nodeToGo = currNode.getDownNode();
        break;
      default:
        break;
    }

    if (nodeToGo != null) {
      String s = "";
      // If player is not dead.
      if (!dungeon.getPlayer().isDead()) {
        currLoc = dungeon.getPlayer().getCurrLoc();

        // If player has not won.
        if (endLoc[0] != currLoc[0] || endLoc[1] != currLoc[1]) {
          int[] nodeLoc = nodeToGo.getMatrixIndex();
          dungeon.movePlayer(nodeLoc);
          s = "Player moved to " + Arrays.toString(nodeLoc) + " .";
          String path = "";
          int smellIntensity = dungeon.playerSmell();
          if (smellIntensity == 0) {
            s = s + "  Player smells nothing.";
          } else if (smellIntensity == 1) {
            s = s + "  Player smells something mildly pungent.";
            path = "/view" + "/dungeon-images" + "/stench01.png";
          } else if (smellIntensity == 2) {
            s = s + "  Player smells something heavily pungent.";
            path = "/view" + "/dungeon-images" + "/stench02.png";
          }

          if (!path.equals("")) {
            try {
              //System.out.println(System.getProperty("user.dir") + path);
              Image smellImg = ImageIO.read(getClass().getResourceAsStream(path));
              view.showMessageWithPhoto(s, smellImg);
            } catch (IOException e) {
              e.printStackTrace();
            }
          } else {
            view.showMessage(s);
          }


        } else {
          view.showMessage(s + "This is the End Cave, Player Won..!! HURRAYYYY ...!!!");
          view.disablePlayButtons();
        }
      } else {
        view.showMessage(s + "ChOmP cHoMp CHOMP! Player eaten by monster.");
        view.disablePlayButtons();
        view.showMonsters();
        view.refreshTheView();
        return;
      }
    } else {
      view.showMessage("This Move Is Invalid.");
    }

    view.depictPlayerPos(dungeon.getPlayer().getCurrLoc());
  }

  @Override
  public void pickAllItems() {
    String message = generatePickMessage();

    dungeon.getPlayer().pickTreasure();
    dungeon.getPlayer().pickWeapons();

    view.depictPlayerPos(dungeon.getPlayerCurrentLocation());

    view.showMessage(message);
  }

  @Override
  public void playerShoot(int distance, String dir) {
    if (dir == null || dir.equals("")) {
      view.showMessage("Direction Not Chosen.");
      return;
    }

    if (dungeon.getPlayer().getCollectedWeapons().size() > 0) {
      dungeon.getPlayer().shootArrow(dir, distance);
      view.showMessage("Shot an Arrow in " + dir + " direction, at distance " + distance + ".");
    } else {
      view.showMessage("No Arrows To Shoot.");
    }
    view.depictPlayerPos(dungeon.getPlayerCurrentLocation());
  }

  /**
   * makes a deep copy of the object given.
   *
   * @param object The object to be copied.
   * @return The copy of Object.
   */
  public static Object deepCopyObject(Object object) throws IOException, ClassNotFoundException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(bos);
    oos.writeObject(object);
    oos.flush();
    oos.close();
    bos.close();
    byte[] byteData = bos.toByteArray();

    ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
    return new ObjectInputStream(bais).readObject();
  }
}
