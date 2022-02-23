package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Model.Player class implement the Model.PlayerInterface, in order to provide all the publicly
 * usable functionalities.
 * It represents players and its details like current location and treasure it have and moves he
 * can take.
 */
public class Player implements PlayerInterface, Serializable {
  private int[] currLocation;
  private final List<Treasure> treasureList;
  private CaveTunnelInterface currCaveCumTunnel;
  private boolean isDead = false;
  private final List<WeaponEnum> weaponsList;
  private DungeonInterface dungeon;

  /**
   * The player constructor initialises the player object, on the basis of certain given inputs.
   *
   * @param currLocation      The current location object.
   * @param treasureList      The treasure List object if it has anything in the beginning.
   * @param currCaveCumTunnel The cave or tunnel he is in object.
   */
  public Player(int[] currLocation, List<Treasure> treasureList,
                CaveTunnelInterface currCaveCumTunnel) {
    this.currLocation = currLocation;
    this.treasureList = treasureList;
    this.currCaveCumTunnel = currCaveCumTunnel;
    weaponsList = new ArrayList<>(Arrays.asList(WeaponEnum.CROOKED_ARROW, WeaponEnum.CROOKED_ARROW,
            WeaponEnum.CROOKED_ARROW));
    this.dungeon = null;
  }

  /**
   * The constructor for Model.Player Class.
   * Sets the defaults values for the class.
   */
  public Player() {
    int[] currLocation = {-1, -1};
    this.currLocation = currLocation;

    this.treasureList = new ArrayList<>();

    CaveTunnelInterface currCaveCumTunnel = new CaveTunnel();
    currCaveCumTunnel.setMatrixIndex(currLocation);
    this.currCaveCumTunnel = currCaveCumTunnel;
    weaponsList = new ArrayList<>(Arrays.asList(WeaponEnum.CROOKED_ARROW, WeaponEnum.CROOKED_ARROW,
            WeaponEnum.CROOKED_ARROW));
    this.dungeon = null;
  }

  private boolean haveTreasure() {
    List<Treasure> caveTreasureList = currCaveCumTunnel.getTreasureList();
    return caveTreasureList.size() > 0;
  }

  @Override
  public void pickTreasure() {
    if (this.haveTreasure()) {
      List<Treasure> caveTreasureList = currCaveCumTunnel.getTreasureList();
      this.treasureList.addAll(caveTreasureList);
      currCaveCumTunnel.clearTreasureList();
    }
  }

  @Override
  public void pickWeapons() {
    if (this.currCaveCumTunnel.getWeaponsList().size() > 0) {
      List<WeaponEnum> weaponList = currCaveCumTunnel.getWeaponsList();
      this.weaponsList.addAll(weaponList);
      currCaveCumTunnel.clearWeaponList();
    }
  }

  @Override
  public void moveInDungeon(int[] newLocation, CaveTunnelInterface newCaveCumTunnel) {
    this.currLocation = newLocation;
    this.currCaveCumTunnel = newCaveCumTunnel;
  }

  @Override
  public List<int[]> getPossibleMoves() {
    return currCaveCumTunnel.getPossibleMoves();
  }

  @Override
  public int[] getCurrLoc() {
    return currLocation;
  }

  @Override
  public List<Treasure> getCollectedTreasures() {
    return new ArrayList<>(this.treasureList);
  }


  @Override
  public List<WeaponEnum> getCollectedWeapons() {
    return new ArrayList<>(this.weaponsList);
  }


  @Override
  public boolean isDead() {
    return this.isDead;
  }

  @Override
  public void killPlayer() {
    this.isDead = true;
  }

  /**
   * Returns the cave which the arrow has to land.
   *
   * @param direction   The direction in which the arrow is in.
   * @param distInCaves The distance of caves remaining it has to travel.
   * @param currNode    The current node.
   * @param comingFrom  The node it is coming from.
   * @return the calculated cave which the arrow has to land.
   */
  public CaveTunnelInterface getTargetCave(String direction, int distInCaves,
                                           CaveTunnelInterface currNode, int[] comingFrom) {
    if (distInCaves == 0) {
      return currNode;
    }

    CaveTunnelInterface nodeInDirection;
    int[] currLoc = currNode.getMatrixIndex();
    switch (direction) {
      case "R":
        nodeInDirection = currNode.getRightNode();
        break;
      case "L":
        nodeInDirection = currNode.getLeftNode();
        break;
      case "U":
        nodeInDirection = currNode.getUpNode();
        break;
      case "D":
        nodeInDirection = currNode.getDownNode();
        break;
      default:
        nodeInDirection = null;
        break;
    }

    if (currNode.isCave()) {
      if (nodeInDirection != null) {
        if (nodeInDirection.isCave()) {
          return getTargetCave(direction, distInCaves - 1, nodeInDirection, currLoc);
        } else {
          return getTargetCave(direction, distInCaves, nodeInDirection, currLoc);
        }
      } else {
        return null;
      }
    } else {
      if (nodeInDirection != null) {
        if (nodeInDirection.isCave()) {
          return getTargetCave(direction, distInCaves - 1, nodeInDirection, currLoc);
        } else {
          return getTargetCave(direction, distInCaves, nodeInDirection, currLoc);
        }
      } else {
        List<int[]> possibleLocations = currNode.getPossibleMoves();
        possibleLocations.remove(comingFrom);
        int[] nextNodeLoc = possibleLocations.get(0);
        String newDirection;
        nodeInDirection = this.dungeon.getCaveTunnelFromLocation(nextNodeLoc);

        if (currNode.getDownNode() == nodeInDirection) {
          newDirection = "D";
        } else if (currNode.getUpNode() == nodeInDirection) {
          newDirection = "U";
        } else if (currNode.getRightNode() == nodeInDirection) {
          newDirection = "R";
        } else if (currNode.getLeftNode() == nodeInDirection) {
          newDirection = "L";
        } else {
          newDirection = direction;
        }

        if (nodeInDirection.isCave()) {
          return getTargetCave(newDirection, distInCaves - 1, nodeInDirection, currLoc);
        } else {
          return getTargetCave(newDirection, distInCaves, nodeInDirection, currLoc);
        }
      }
    }
  }

  @Override
  public void shootArrow(String direction, int distanceInCaves) {
    CaveTunnelInterface targetCave = this.getTargetCave(direction, distanceInCaves,
            this.currCaveCumTunnel, this.getCurrLoc());
    if (this.weaponsList.size() > 0) {
      if (targetCave != null) {
        List<MonsterInterface> monsterList = targetCave.getMonsters();
        for (MonsterInterface m : monsterList) {
          if (!m.isDead()) {
            m.slayBy(this.weaponsList.get(0));
            this.weaponsList.remove(0);
            break;
          }
        }
      } else {
        this.weaponsList.remove(0);
      }
    }
  }

  void setDungeon(DungeonInterface dungeon) {
    this.dungeon = dungeon;
  }


  @Override
  public String toString() {
    return "Model.Player{" +
            "currLocation=" + Arrays.toString(currLocation) +
            ", playerTreasureList=" + treasureList +
            //", currCaveCumTunnelDetails=" + currCaveCumTunnel +
            ", isDead=" + isDead +
            ", weaponsList=" + weaponsList +
            '}';
  }
}
