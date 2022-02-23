import controller.gui.GUIController;
import controller.gui.GUIControllerInterface;
import view.DungeonView;
import view.DungeonViewInterface;

/**
 * Run a Dungeon game interactively on the GUI.
 */
public class MainGui {
  /**
   * Run a Dungeon game interactively on the GUI.
   */
  public static void main(String[] args) {
    //DungeonReadOnlyInterface dungeon = new Dungeon();
    GUIControllerInterface guiController = new GUIController();
    DungeonViewInterface tttView = new DungeonView(guiController);
    guiController.updateView(tttView);
  }
}
