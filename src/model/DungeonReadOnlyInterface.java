package model;

import java.util.List;

/**
 * This Model.DungeonInterface keeps all the public behaviours of a Model.Dungeon.
 */
public interface DungeonReadOnlyInterface {

  /**
   * Gives the node object in a particular location in dungeon.
   *
   * @param location The integer array of [row, col] of the node.
   * @return The node at specified location.
   */
  CaveTunnelInterface getCaveTunnelFromLocation(int[] location);


  /**
   * Gives the moves current Cave/Tunnel at which the player is present.
   *
   * @return The List of integer array object having possible row, column locations.
   */
  public List<int[]> getCurrentMoves();

  /**
   * Gives the players current location in the dungeon.
   *
   * @return Payers current location object.
   */
  int[] getPlayerCurrentLocation();


  /**
   * Gives the list of all the Caves in the Model.Dungeon.
   *
   * @return The list of caves present in the Model.Dungeon.
   */
  public List<CaveTunnelInterface> getAllCaves();


  /**
   * Gives the list of all the Tunnels in the Model.Dungeon.
   *
   * @return The list of Tunnels present in the Model.Dungeon.
   */
  public List<CaveTunnelInterface> getAllTunnels();


  /**
   * Gives the ending position object (int []).
   *
   * @return The ending position object.
   */
  int[] getEndPosition();


  /**
   * Gives the player object.
   *
   * @return Return the player object.
   */
  Player getPlayer();


  /**
   * Gives   0 --> No Smell  |  1 --> Mild Smell  |  2 --> Heavy Smell.
   *
   * @return Smell Intensity Integer.
   */
  int playerSmell();

  /**
   * Gives the dimensions of the grid of dungeon.
   *
   * @return The array having the size of the dungeon [rows, columns].
   */
  int[] getDungeonSize();
}
