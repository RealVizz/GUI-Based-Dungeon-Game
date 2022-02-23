package view;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Frame;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import controller.gui.GUIControllerInterface;
import model.CaveTunnelInterface;
import model.DungeonInterface;
import model.DungeonReadOnlyInterface;
import model.MonsterInterface;
import model.PlayerInterface;
import model.Treasure;
import model.TreasureContent;
import model.WeaponEnum;
import view.eventhandlers.MPSEvents;
import view.eventhandlers.MPSKeyboardEvents;


/**
 * The most important view class, makes a base frame, and let controller decide what to do next,
 * most of the functionality for GUI lies here.
 */
public class DungeonView implements DungeonViewInterface {
  DungeonReadOnlyInterface readableDungeon;

  BaseFrame frame;
  GUIControllerInterface guiController;

  JPanel infoPanel;
  JPanel playerDetPanel;
  JPanel nodeDetPanel;

  JPanel buttonsPanel;

  JPanel mpsButtonPanel;
  JPanel directionButtonPanel;
  JPanel shootRangeButtonPanel;

  JButton moveButton;
  JButton shootButton;
  JButton pickButton;
  JButton rightDirButton;
  JButton leftDirButton;
  JButton upDirButton;
  JButton downDirButton;
  JButton dist1;
  JButton dist2;
  JButton dist3;
  JButton dist4;
  JButton dist5;

  JLabel lastOpDetailsL;

  JButton[] gridButtons;

  Image blackImage;
  ImageIcon blackImageIcon;

  MPSKeyboardEvents keyListener;

  /**
   * Uses the basic frame and initialises the reference for its controller (GUI Controller).
   *
   * @param guiController The controller object.
   */
  public DungeonView(GUIControllerInterface guiController) {
    this.guiController = guiController;
    this.frame = new BaseFrame(guiController);

    infoPanel = new JPanel(new GridLayout(1, 0));
    playerDetPanel = new JPanel(new GridLayout(1, 0));
    nodeDetPanel = new JPanel(new GridLayout(1, 0));


    initialiseBlackImage();
    setupInfoPanel();
  }

  private void initialiseBlackImage() {
    try {
      //System.out.println(getClass().getResource("dungeon-images\\bw-cells\\ES.png"));

      //String path = "\\src" + "\\View" + "\\dungeon-images" + "\\blank.png";
      //this.blackImage = ImageIO.read(new File(System.getProperty("user.dir") + path));

      //System.out.println(getClass());
      //this.blackImage = ImageIO.read(getClass().getResource("dungeon-images\\blank.png"));

      this.blackImage =
              ImageIO.read(getClass().getResourceAsStream("/view/dungeon-images/blank.png"));


      this.blackImageIcon = new ImageIcon(blackImage);
    } catch (IOException ignored) {
    }
  }

  private void setupInfoPanel() {
    lastOpDetailsL = new JLabel("MESSAGE  : ");
    infoPanel.add(lastOpDetailsL);
    infoPanel.setBounds(10, 0, 550, 30);
  }

  private void updatePlayerDetailsInView() {
    if (playerDetPanel != null) {
      frame.remove(playerDetPanel);
      playerDetPanel = new JPanel(new GridLayout(1, 0));
    }

    PlayerInterface p = readableDungeon.getPlayer();
    List<Treasure> treasures = p.getCollectedTreasures();
    List<WeaponEnum> w = p.getCollectedWeapons();

    int arrowsCount = w.size();
    int diamondsCount = 0;
    int emeraldsCount = 0;
    int rubiesCount = 0;

    for (Treasure t : treasures) {
      if (t.getTreasure() == TreasureContent.RUBIES) {
        rubiesCount += 1;
      } else if (t.getTreasure() == TreasureContent.DIAMONDS) {
        diamondsCount += 1;
      } else if (t.getTreasure() == TreasureContent.EMERALD) {
        emeraldsCount += 1;
      }
    }

    String diamondPath = "/view" + "/dungeon-images" + "/diamond.png";
    String emeraldPath = "/view" + "/dungeon-images" + "/emerald.png";
    String rubyPath = "/view" + "/dungeon-images" + "/ruby.png";
    String arrowPath = "/view" + "/dungeon-images" + "/arrow-black.png";

    try {
      Image diamondImg = ImageIO.read(getClass().getResourceAsStream(diamondPath));
      Image emeraldImg = ImageIO.read(getClass().getResourceAsStream(emeraldPath));
      Image rubyImg = ImageIO.read(getClass().getResourceAsStream(rubyPath));
      Image arrowImg = ImageIO.read(getClass().getResourceAsStream(arrowPath));

      JLabel textLabel = new JLabel("Player Info :  ");

      JLabel arrowLabel = new JLabel(new ImageIcon(arrowImg));
      arrowLabel.setText("X " + arrowsCount);
      JLabel diamondLabel = new JLabel(new ImageIcon(diamondImg));
      diamondLabel.setText("X " + diamondsCount);
      JLabel emeraldLabel = new JLabel(new ImageIcon(emeraldImg));
      emeraldLabel.setText("X " + emeraldsCount);
      JLabel rubyLabel = new JLabel(new ImageIcon(rubyImg));
      rubyLabel.setText("X " + rubiesCount);


      playerDetPanel.add(textLabel);
      playerDetPanel.add(arrowLabel);
      playerDetPanel.add(diamondLabel);
      playerDetPanel.add(emeraldLabel);
      playerDetPanel.add(rubyLabel);
      playerDetPanel.setBounds(10, 15, 580, 60);
    } catch (IOException ignored) {
      //System.out.println(ignored.toString());
    }

    frame.add(playerDetPanel, Frame.LEFT_ALIGNMENT);
    refreshTheView();
    //System.out.println(readableDungeon.getPlayer().getCollectedWeapons().size());
  }

  private void updateNodeDetailsInView() {
    if (nodeDetPanel != null) {
      frame.remove(nodeDetPanel);
      nodeDetPanel = new JPanel(new GridLayout(1, 0));
    }

    int[] currLoc = readableDungeon.getPlayerCurrentLocation();
    CaveTunnelInterface node = readableDungeon.getCaveTunnelFromLocation(currLoc);
    List<Treasure> treasures = node.getTreasureList();
    List<WeaponEnum> w = node.getWeaponsList();

    int arrowsCount = w.size();
    int diamondsCount = 0;
    int emeraldsCount = 0;
    int rubiesCount = 0;

    for (Treasure t : treasures) {
      if (t.getTreasure() == TreasureContent.RUBIES) {
        rubiesCount += 1;
      } else if (t.getTreasure() == TreasureContent.DIAMONDS) {
        diamondsCount += 1;
      } else if (t.getTreasure() == TreasureContent.EMERALD) {
        emeraldsCount += 1;
      }
    }

    String diamondPath = "/view" + "/dungeon-images" + "/diamond.png";
    String emeraldPath = "/view" + "/dungeon-images" + "/emerald.png";
    String rubyPath = "/view" + "/dungeon-images" + "/ruby.png";
    String arrowPath = "/view" + "/dungeon-images" + "/arrow-black.png";

    try {
      Image diamondImg = ImageIO.read(getClass().getResourceAsStream(diamondPath));
      Image emeraldImg = ImageIO.read(getClass().getResourceAsStream(emeraldPath));
      Image rubyImg = ImageIO.read(getClass().getResourceAsStream(rubyPath));
      Image arrowImg = ImageIO.read(getClass().getResourceAsStream(arrowPath));

      JLabel textLabel = new JLabel("Node Information :  ");

      JLabel arrowLabel = new JLabel(new ImageIcon(arrowImg));
      arrowLabel.setText("X " + arrowsCount);
      JLabel diamondLabel = new JLabel(new ImageIcon(diamondImg));
      diamondLabel.setText("X " + diamondsCount);
      JLabel emeraldLabel = new JLabel(new ImageIcon(emeraldImg));
      emeraldLabel.setText("X " + emeraldsCount);
      JLabel rubyLabel = new JLabel(new ImageIcon(rubyImg));
      rubyLabel.setText("X " + rubiesCount);


      nodeDetPanel.add(textLabel);
      nodeDetPanel.add(arrowLabel);
      nodeDetPanel.add(diamondLabel);
      nodeDetPanel.add(emeraldLabel);
      nodeDetPanel.add(rubyLabel);
      nodeDetPanel.setBounds(10, 55, 580, 70);
    } catch (IOException ignored) {
      //System.out.println(ignored.toString());
    }

    frame.add(nodeDetPanel, Frame.LEFT_ALIGNMENT);
    refreshTheView();
  }

  private void setupButtons(int rows, int cols) {
    if (buttonsPanel != null) {
      frame.remove(buttonsPanel);
      frame.repaint();
    }
    gridButtons = new JButton[rows * cols];
    buttonsPanel = new JPanel(new GridLayout(rows, cols));
    for (int i = 0; i < rows * cols; i++) {
      JButton b = new JButton();
      gridButtons[i] = b;
      setBlackImage(b);
      b.setBackground(new Color(255, 255, 255));
      b.setOpaque(true);
      buttonsPanel.add(gridButtons[i]);
    }

    int widthPix = getAppropriateWidthHeight(rows, cols)[0];
    int heightPix = getAppropriateWidthHeight(rows, cols)[1];
    buttonsPanel.setBounds(10, 200, widthPix, heightPix);
    frame.add(buttonsPanel);

  }

  private void setupPlayButtons() {
    mpsButtonPanel = new JPanel(new GridLayout(1, 0));
    directionButtonPanel = new JPanel(new GridLayout(1, 0));
    shootRangeButtonPanel = new JPanel(new GridLayout(1, 0));

    moveButton = new JButton("MOVE");
    pickButton = new JButton("PICK (All)");
    shootButton = new JButton("SHOOT");

    upDirButton = new JButton("UP/N");
    downDirButton = new JButton("DOWN/S");
    rightDirButton = new JButton("RIGHT/E");
    leftDirButton = new JButton("LEFT/W");


    dist1 = new JButton("1");
    dist2 = new JButton("2");
    dist3 = new JButton("3");
    dist4 = new JButton("4");
    dist5 = new JButton("5");

    mpsButtonPanel.add(moveButton);
    mpsButtonPanel.add(pickButton);
    mpsButtonPanel.add(shootButton);

    directionButtonPanel.add(upDirButton);
    directionButtonPanel.add(downDirButton);
    directionButtonPanel.add(rightDirButton);
    directionButtonPanel.add(leftDirButton);

    shootRangeButtonPanel.add(dist1);
    shootRangeButtonPanel.add(dist2);
    shootRangeButtonPanel.add(dist3);
    shootRangeButtonPanel.add(dist4);
    shootRangeButtonPanel.add(dist5);

    MPSEvents mpsEvents = new MPSEvents(this, guiController, moveButton, pickButton,
            shootButton, upDirButton, downDirButton, rightDirButton, leftDirButton, dist1, dist2,
            dist3, dist4, dist5);
    keyListener = new MPSKeyboardEvents(this, guiController, moveButton, pickButton,
            shootButton, upDirButton, downDirButton, rightDirButton, leftDirButton, dist1, dist2,
            dist3, dist4, dist5);

    moveButton.addMouseListener(mpsEvents);
    shootButton.addMouseListener(mpsEvents);
    pickButton.addMouseListener(mpsEvents);
    rightDirButton.addMouseListener(mpsEvents);
    leftDirButton.addMouseListener(mpsEvents);
    upDirButton.addMouseListener(mpsEvents);
    downDirButton.addMouseListener(mpsEvents);
    dist1.addMouseListener(mpsEvents);
    dist2.addMouseListener(mpsEvents);
    dist3.addMouseListener(mpsEvents);
    dist4.addMouseListener(mpsEvents);
    dist5.addMouseListener(mpsEvents);

    setKeyBoardMnemonic();

    mpsButtonPanel.setBounds(10, 110, 480, 30);
    directionButtonPanel.setBounds(10, 135, 480, 30);
    shootRangeButtonPanel.setBounds(10, 160, 480, 30);

    frame.add(mpsButtonPanel);
    frame.add(directionButtonPanel);
    frame.add(shootRangeButtonPanel);

    // So that it do not add up twice or more.
    if (frame.getKeyListeners().length < 1) {
      frame.addKeyListener(keyListener);
    }

    frame.addMouseListener(mpsEvents);
  }

  @Override
  public void removeDefaultMessage() {
    this.frame.removeDefaultMessage();
  }

  @Override
  public void setFrameAllBlack(int rows, int cols) {
    frame.setLayout(null); // new GridLayout(0, 1)
    frame.add(infoPanel, Frame.LEFT_ALIGNMENT);
    setupPlayButtons();
    setupButtons(rows, cols);

    //frame.setLocationRelativeTo(buttonsPanel);
    //frame.pack();
    int heightPix = getAppropriateWidthHeight(rows, cols)[0];
    int widthPix = getAppropriateWidthHeight(rows, cols)[1];
    frame.setSize(Math.max(600, widthPix + 50), Math.max(600, heightPix + 280));
    frame.setResizable(false);
    frame.setFocusable(true);
    //frame.requestFocus();
    frame.revalidate();
    frame.repaint();

  }

  private int[] getAppropriateWidthHeight(int rows, int cols) {
    int hPixMin = 0; // 600
    int wPixMin = 0; // 400
    int hPix = rows * 65 + 10;
    int wPix = cols * 65 + 10;

    int[] retPix = new int[2];
    retPix[0] = Math.max(hPixMin, hPix);
    retPix[1] = Math.max(wPixMin, wPix);

    return retPix;
  }

  @Override
  public void setDungeon(DungeonInterface d) {
    this.readableDungeon = d;
  }

  @Override
  public void depictPlayerPos(int[] playerCurrentLocation) {
    int totalRows = readableDungeon.getDungeonSize()[0];
    int totalCols = readableDungeon.getDungeonSize()[1];
    int index = playerCurrentLocation[0] * totalCols + playerCurrentLocation[1];

    this.updatePlayerDetailsInView();
    this.updateNodeDetailsInView();

    setColorButtonImage(this.gridButtons[index], index, totalRows, totalCols);
  }

  private void setColorButtonImage(JButton button, int index, int totRows, int totCols) {
    CaveTunnelInterface node = this.readableDungeon.getCaveTunnelFromLocation(
            new int[]{index / totCols, index % totCols});
    String path = "/view" + "/dungeon-images" + "/color-cells";
    CaveTunnelInterface eNode = node.getRightNode(); // East Node.
    CaveTunnelInterface wNode = node.getLeftNode(); // West Node.
    CaveTunnelInterface sNode = node.getDownNode(); // South Node.
    CaveTunnelInterface nNode = node.getUpNode(); // North Node.

    if (nNode != null && eNode != null && wNode != null && sNode != null) {
      path = path + "/NSEW.png";
    } else if (nNode != null && eNode != null && wNode != null) {
      path = path + "/NEW.png";
    } else if (nNode != null && eNode != null && sNode != null) {
      path = path + "/NSE.png";
    } else if (nNode != null && wNode != null && sNode != null) {
      path = path + "/NSW.png";
    } else if (eNode != null && wNode != null && sNode != null) {
      path = path + "/SEW.png";
    } else if (nNode != null && eNode != null) {
      path = path + "/NE.png";
    } else if (nNode != null && wNode != null) {
      path = path + "/NW.png";
    } else if (nNode != null && sNode != null) {
      path = path + "/NS.png";
    } else if (eNode != null && wNode != null) {
      path = path + "/EW.png";
    } else if (eNode != null && sNode != null) {
      path = path + "/SE.png";
    } else if (wNode != null && sNode != null) {
      path = path + "/SW.png";
    } else if (nNode != null) {
      path = path + "/N.png";
    } else if (eNode != null) {
      path = path + "/E.png";
    } else if (wNode != null) {
      path = path + "/W.png";
    } else if (sNode != null) {
      path = path + "/S.png";
    } else {
      path = "";
    }

    try {
      Image img = ImageIO.read(getClass().getResourceAsStream(path));
      button.setIcon(new ImageIcon(img));

      button.setText("P");
      button.setForeground(new Color(224, 3, 5));
      button.setHorizontalTextPosition(JButton.CENTER);
      button.setVerticalTextPosition(JButton.CENTER);

      //button.setContentAreaFilled(false);
    } catch (IOException ignored) {
      //System.out.println(ignored.toString());
    }

    // Code to add Black and white nodes, which player see he can go.
    for (int[] x : node.getPossibleMoves()) {
      int idx = x[0] * totCols + x[1];
      //System.out.println(idx);
      JButton btn = gridButtons[idx];
      setBWButtonImage(btn, idx, totRows, totCols);
    }

  }

  private void setBWButtonImage(JButton button, int index, int totRows, int totCols) {
    if (!button.getIcon().equals(blackImageIcon)) {
      button.setText(null);
      return;
    }

    CaveTunnelInterface node = this.readableDungeon.getCaveTunnelFromLocation(
            new int[]{index / totCols, index % totCols});
    String path = "/view" + "/dungeon-images" + "/bw-cells";
    CaveTunnelInterface eNode = node.getRightNode(); // East Node.
    CaveTunnelInterface wNode = node.getLeftNode(); // West Node.
    CaveTunnelInterface sNode = node.getDownNode(); // South Node.
    CaveTunnelInterface nNode = node.getUpNode(); // North Node.

    if (nNode != null && eNode != null && wNode != null && sNode != null) {
      path = path + "/NESW.png";
    } else if (nNode != null && eNode != null && wNode != null) {
      path = path + "/NEW.png";
    } else if (nNode != null && eNode != null && sNode != null) {
      path = path + "/NES.png";
    } else if (nNode != null && wNode != null && sNode != null) {
      path = path + "/SWN.png";
    } else if (eNode != null && wNode != null && sNode != null) {
      path = path + "/ESW.png";
    } else if (nNode != null && eNode != null) {
      path = path + "/NE.png";
    } else if (nNode != null && wNode != null) {
      path = path + "/WN.png";
    } else if (nNode != null && sNode != null) {
      path = path + "/NS.png";
    } else if (eNode != null && wNode != null) {
      path = path + "/EW.png";
    } else if (eNode != null && sNode != null) {
      path = path + "/ES.png";
    } else if (wNode != null && sNode != null) {
      path = path + "/SW.png";
    } else if (nNode != null) {
      path = path + "/N.png";
    } else if (eNode != null) {
      path = path + "/E.png";
    } else if (wNode != null) {
      path = path + "/W.png";
    } else if (sNode != null) {
      path = path + "/S.png";
    } else {
      path = "";
    }

    try {
      Image img = ImageIO.read(getClass().getResourceAsStream(path));
      button.setIcon(new ImageIcon(img));

      button.setText(null);

      //gridButtons[index].setContentAreaFilled(true);
    } catch (IOException ignored) {
      //System.out.println(path);
    }
  }

  private void setBlackImage(JButton button) {
    button.setIcon(blackImageIcon);
  }


  @Override
  public void refreshTheView() {
    frame.revalidate();
    frame.repaint();
  }

  @Override
  public void showMessage(String s) {
    String baseStr = "MESSAGE  : ";
    String displayStr = baseStr + s;

    infoPanel.remove(lastOpDetailsL);
    lastOpDetailsL = new JLabel(displayStr);
    infoPanel.add(lastOpDetailsL);

    refreshTheView();
  }

  @Override
  public void disablePlayButtons() {
    moveButton.setEnabled(false);
    pickButton.setEnabled(false);
    shootButton.setEnabled(false);

    upDirButton.setEnabled(false);
    downDirButton.setEnabled(false);
    leftDirButton.setEnabled(false);
    rightDirButton.setEnabled(false);

    dist1.setEnabled(false);
    dist2.setEnabled(false);
    dist3.setEnabled(false);
    dist4.setEnabled(false);
    dist5.setEnabled(false);

    frame.removeKeyListener(keyListener);
  }

  @Override
  public void enablePlayButtons() {
    moveButton.setEnabled(true);
    pickButton.setEnabled(true);
    shootButton.setEnabled(true);
  }

  private void setKeyBoardMnemonic() {
    moveButton.setMnemonic(KeyEvent.VK_M);
    pickButton.setMnemonic(KeyEvent.VK_P);
    shootButton.setMnemonic(KeyEvent.VK_H);

    rightDirButton.setMnemonic(KeyEvent.VK_R);
    rightDirButton.setMnemonic(KeyEvent.VK_E);

    leftDirButton.setMnemonic(KeyEvent.VK_L);
    leftDirButton.setMnemonic(KeyEvent.VK_W);

    upDirButton.setMnemonic(KeyEvent.VK_U);
    upDirButton.setMnemonic(KeyEvent.VK_N);

    downDirButton.setMnemonic(KeyEvent.VK_D);
    downDirButton.setMnemonic(KeyEvent.VK_S);


    dist1.setMnemonic(KeyEvent.VK_1);
    dist2.setMnemonic(KeyEvent.VK_2);
    dist3.setMnemonic(KeyEvent.VK_3);
    dist4.setMnemonic(KeyEvent.VK_4);
    dist5.setMnemonic(KeyEvent.VK_5);
  }


  @Override
  public JFrame getFrame() {
    return this.frame;
  }

  @Override
  public void showMonsters() {
    int totalRows = readableDungeon.getDungeonSize()[0];
    int totalCols = readableDungeon.getDungeonSize()[1];
    String path = "/view" + "/dungeon-images" + "/otyugh.png";
    Image monsterImg;
    try {
      monsterImg = ImageIO.read(getClass().getResourceAsStream(path));
      List<CaveTunnelInterface> allCaves = readableDungeon.getAllCaves();
      for (CaveTunnelInterface cave : allCaves) {
        int[] currLoc = cave.getMatrixIndex();
        if (cave.getMonsters().size() > 0) {
          List<MonsterInterface> monstersList = cave.getMonsters();
          for (MonsterInterface monster : monstersList) {
            if (!monster.isDead()) {
              int index = currLoc[0] * totalCols + currLoc[1];
              //System.out.println("Yo.!" + " "+ index);
              gridButtons[index].setIcon(null);
              //gridButtons[index].setText(null);
              gridButtons[index].setIcon(new ImageIcon(monsterImg));
              break;
            }
          }
        }
      }

      refreshTheView();
    } catch (IOException ignored) {
      //ignored.printStackTrace();
    }

  }

  @Override
  public void showMessageWithPhoto(String s, Image img) {
    String baseStr = "MESSAGE  : ";
    String displayStr = baseStr + s;

    infoPanel.remove(lastOpDetailsL);
    lastOpDetailsL = new JLabel(displayStr);
    lastOpDetailsL.setIcon(new ImageIcon(img));
    infoPanel.add(lastOpDetailsL);

    refreshTheView();
  }
}
