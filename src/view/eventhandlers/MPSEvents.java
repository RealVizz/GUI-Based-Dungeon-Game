package view.eventhandlers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import controller.gui.GUIControllerInterface;
import view.DungeonView;

/**
 * The "Move" "Pick" "Shoot" (Mouse) events handler class.
 */
public class MPSEvents extends MouseAdapter {
  DungeonView view;
  GUIControllerInterface guiController;

  JButton moveButton;
  JButton pickButton;
  JButton shootButton;

  boolean isMoveButtonClicked = false;
  boolean isShootButtonClicked = false;

  //boolean isRightDirClicked = false;
  //boolean isLeftDirClicked = false;
  //boolean isUpDirClicked = false;
  //boolean isDownDirClicked = false;

  String chosenDirection;

  JButton upDirButton;
  JButton downDirButton;
  JButton rightDirButton;
  JButton leftDirButton;

  JButton dist1;
  JButton dist2;
  JButton dist3;
  JButton dist4;
  JButton dist5;

  /**
   * The construct initialises the data.
   *
   * @param view           The view object.
   * @param guiController  The controller object.
   * @param moveButton     The move button object.
   * @param pickButton     The pick button object.
   * @param shootButton    The shoot button object.
   * @param upDirButton    The UP/North button object.
   * @param downDirButton  The DOWN/South button object.
   * @param rightDirButton The RIGHT/East button object.
   * @param leftDirButton  The LEFT/West button object.
   * @param dist1          The distance 1 button object.
   * @param dist2          The distance 2 button object.
   * @param dist3          The distance 3 button object.
   * @param dist4          The distance 4 button object.
   * @param dist5          The distance 5 button object.
   */
  public MPSEvents(DungeonView view, GUIControllerInterface guiController, JButton moveButton,
                   JButton pickButton, JButton shootButton, JButton upDirButton,
                   JButton downDirButton, JButton rightDirButton, JButton leftDirButton,
                   JButton dist1, JButton dist2, JButton dist3, JButton dist4, JButton dist5) {
    this.view = view;
    this.guiController = guiController;

    this.moveButton = moveButton;
    this.pickButton = pickButton;
    this.shootButton = shootButton;

    this.upDirButton = upDirButton;
    this.downDirButton = downDirButton;
    this.rightDirButton = rightDirButton;
    this.leftDirButton = leftDirButton;

    this.dist1 = dist1;
    this.dist2 = dist2;
    this.dist3 = dist3;
    this.dist4 = dist4;
    this.dist5 = dist5;

    disableDirectionButtons();
    disableShootRangeButtons();
  }

  @Override
  public void mousePressed(MouseEvent e) {
    Object pressedButton = e.getSource();
    if (pressedButton == moveButton) {
      moveButton.setEnabled(false);
      pickButton.setEnabled(true);
      shootButton.setEnabled(true);

      isMoveButtonClicked = true;
      isShootButtonClicked = false;

      enableDirectionButtons();
      disableShootRangeButtons();
    } else if (pressedButton == shootButton) {
      chosenDirection = null;
      moveButton.setEnabled(true);
      pickButton.setEnabled(true);
      shootButton.setEnabled(false);

      isMoveButtonClicked = false;
      isShootButtonClicked = true;

      enableDirectionButtons();
      enableShootRangeButtons();
    } else if (pressedButton == pickButton) {
      moveButton.setEnabled(true);
      pickButton.setEnabled(true);
      shootButton.setEnabled(true);

      disableDirectionButtons();
      disableShootRangeButtons();

      guiController.pickAllItems();
    } else if (pressedButton == rightDirButton) {
      //setNoDirIsClicked();
      //isRightDirClicked = true;
      selfDisableRestEnable(rightDirButton, new JButton[]{leftDirButton, upDirButton,
          downDirButton});
      chosenDirection = "R";
      if (this.isMoveButtonClicked) {
        guiController.playerMove("R");
      }
    } else if (pressedButton == leftDirButton) {
      //setNoDirIsClicked();
      //isLeftDirClicked = true;
      selfDisableRestEnable(leftDirButton, new JButton[]{rightDirButton, upDirButton,
          downDirButton});
      chosenDirection = "L";
      if (this.isMoveButtonClicked) {
        guiController.playerMove("L");
      }
    } else if (pressedButton == upDirButton) {
      //setNoDirIsClicked();
      //isUpDirClicked = true;
      selfDisableRestEnable(upDirButton, new JButton[]{rightDirButton, leftDirButton,
          downDirButton});
      chosenDirection = "U";
      if (this.isMoveButtonClicked) {
        guiController.playerMove("U");
      }
    } else if (pressedButton == downDirButton) {
      //setNoDirIsClicked();
      //isDownDirClicked = true;
      selfDisableRestEnable(downDirButton, new JButton[]{rightDirButton, leftDirButton,
          upDirButton});
      chosenDirection = "D";
      if (this.isMoveButtonClicked) {
        guiController.playerMove("D");
      }
    } else if (pressedButton == dist1) {
      guiController.playerShoot(1, chosenDirection);
    } else if (pressedButton == dist2) {
      guiController.playerShoot(2, chosenDirection);
    } else if (pressedButton == dist3) {
      guiController.playerShoot(3, chosenDirection);
    } else if (pressedButton == dist4) {
      guiController.playerShoot(4, chosenDirection);
    } else if (pressedButton == dist5) {
      guiController.playerShoot(5, chosenDirection);
    } else {
      view.getFrame().requestFocus();
    }

  }

  private void enableDirectionButtons() {
    upDirButton.setEnabled(true);
    downDirButton.setEnabled(true);
    leftDirButton.setEnabled(true);
    rightDirButton.setEnabled(true);
  }

  private void disableDirectionButtons() {
    upDirButton.setEnabled(false);
    downDirButton.setEnabled(false);
    leftDirButton.setEnabled(false);
    rightDirButton.setEnabled(false);
  }

  private void enableShootRangeButtons() {
    dist1.setEnabled(true);
    dist2.setEnabled(true);
    dist3.setEnabled(true);
    dist4.setEnabled(true);
    dist5.setEnabled(true);
  }

  private void disableShootRangeButtons() {
    dist1.setEnabled(false);
    dist2.setEnabled(false);
    dist3.setEnabled(false);
    dist4.setEnabled(false);
    dist5.setEnabled(false);
  }


  private void selfDisableRestEnable(JButton disableBtn, JButton[] enabledBtns) {
    disableBtn.setEnabled(false);
    for (JButton btn : enabledBtns) {
      btn.setEnabled(true);
    }
  }

}
