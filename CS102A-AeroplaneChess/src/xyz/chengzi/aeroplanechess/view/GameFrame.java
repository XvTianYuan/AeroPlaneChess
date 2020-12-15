package xyz.chengzi.aeroplanechess.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.listener.GameStateListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;
import xyz.chengzi.aeroplanechess.model.ChessPiece;

public class GameFrame extends JFrame implements GameStateListener {
  private static final String[] PLAYER_NAMES = {"Yellow", "Blue", "Green", "Red"};
  private final JLabel statusLabel = new JLabel();
  public int num1;
  public int num2;

  public int getNum1() {
    return num1;
  }

  public int getNum2() {
    return num2;
  }


  public GameFrame(GameController controller) {
    controller.registerListener(this);

    setTitle("2020 CS102A Project Demo");
    setSize(772, 825);
    setLocationRelativeTo(null); // Center the window
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLayout(null);

    statusLabel.setLocation(0, 585);
    statusLabel.setFont(statusLabel.getFont().deriveFont(18.0f));
    statusLabel.setSize(400, 20);
    add(statusLabel);

    DiceSelectorComponent diceSelectorComponent = new DiceSelectorComponent();
    DiceSelectorComponent diceSelectorComponent1 = new DiceSelectorComponent();
    diceSelectorComponent.setLocation(396, 585);
    diceSelectorComponent1.setLocation(396, 615);
    add(diceSelectorComponent);
    add(diceSelectorComponent1);

    JButton button = new JButton("roll");
    button.addActionListener((e) -> {
      if (diceSelectorComponent.isRandomDice()) {
        int dice1 = controller.rollDice();
        num1 = dice1 >> 16;
        num2 = dice1 & 0x00ff;
        if (dice1 != -1) {
          statusLabel.setText(String.format("[%s] Rolled a (%d)(%d) , sum is %d",
              PLAYER_NAMES[controller.getCurrentPlayer()], num1, num2, num1 + num2));

        } else {
          JOptionPane.showMessageDialog(this, "You have already rolled the dice");
        }
      } else {
//                JOptionPane.showMessageDialog(this, "You selected " + diceSelectorComponent.getSelectedDice());
        num1 = (Integer) diceSelectorComponent1.getSelectedDice();
        num2 = (Integer) diceSelectorComponent.getSelectedDice();
        statusLabel.setText(String.format("[%s] Selected a (%d)(%d) , sum is %d",
            PLAYER_NAMES[controller.getCurrentPlayer()], num1, num2, num1 + num2));
        controller.manualDice(num1, num2);
      }
    });


    button.setLocation(668, 585);
    button.setFont(button.getFont().deriveFont(18.0f));
    button.setSize(90, 30);
    add(button);

    JButton save = new JButton("Save");
    save.setLocation(600, 150);
    save.setFont(button.getFont().deriveFont(18.0f));
    save.setSize(90, 30);
    save.addActionListener((e -> {
      ChessBoard board = controller.getModel();
      ChessBoardLocation lists[][] = new ChessBoardLocation[4][4];
      int turn = controller.getCurrentPlayer();
      for (int player = 0; player < 4; player++) {
        for (int index = 0; index < 24; index++) {
          ChessPiece piece;
          if ((piece = board.getChessPieceAt(new ChessBoardLocation(player, index))) != null) {
            int color = piece.getPlayer();
            int number = piece.getNumber();
            lists[color][number] = new ChessBoardLocation(player, index);
          }
        }
      }
      SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");//设置日期格式
      String filename = df.format(new Date());// new Date()为获取当前系统时间
      File dir = new File("Archive");
      if (!dir.exists()) {
        dir.mkdir();
      }
      filename = "Archive/" + filename + ".txt";
      try {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        String str = "";
        for (int i = 0; i < lists.length; i++) {
          String line = "";
          for (int j = 0; j < lists[i].length; j++) {
            line += lists[i][j].toString() + " ";
          }
          str += line + "\n";
        }
        str += turn;
        bw.write(str);
        bw.close();
      } catch (IOException err) {
        err.printStackTrace();
      }
    }));
    add(save);

    JButton load = new JButton("load");
    load.setLocation(600, 300);
    load.setFont(button.getFont().deriveFont(18.0f));
    load.setSize(90, 30);
    load.addActionListener(e -> {
      String choose = chooseArchive();
      File file = new File(choose);
      ChessBoardLocation locations[][] = new ChessBoardLocation[4][4];
      String loadedList[] = new String[4];
      int turn = 0;
      try {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = null;
        int index = 0;
        while ((line = br.readLine()) != null) {
          System.out.println(line);
          if (index<4)
            loadedList[index++] = line;
          else
            turn = Integer.parseInt(line);
        }

        System.out.println("turn:"+turn);

        br.close();
      } catch (FileNotFoundException ex) {
        ex.printStackTrace();
      } catch (IOException ex) {
        ex.printStackTrace();
      }

      for (int player = 0; player < 4; player++) {
        String[] splitedStr = loadedList[player].split(" ");
        for (int num = 0; num < 4; num++) {
          String leftNum = splitedStr[num].split(",")[0].substring(1);
          String rightNum = splitedStr[num].split(",")[1]
              .substring(0, splitedStr[num].split(",")[1].length() - 1);
          locations[player][num] =
              new ChessBoardLocation(Integer.parseInt(leftNum), Integer.parseInt(rightNum));
        }
      }
      controller.loadGame(locations,turn);
    });
    add(load);
  }

  private String chooseArchive() {
    JFileChooser fileChooser = new JFileChooser("Archive\\");

    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

    int returnVal = fileChooser.showOpenDialog(fileChooser);
    String filePath = "";
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      filePath = fileChooser.getSelectedFile().getAbsolutePath();//这个就是你选择的文件夹的
    }
    return filePath;
  }

  @Override
  public void onPlayerStartRound(int player) {
    statusLabel.setText(String.format("[%s] Please roll the dice", PLAYER_NAMES[player]));
  }

  @Override
  public void onPlayerEndRound(int player) {

  }
}
