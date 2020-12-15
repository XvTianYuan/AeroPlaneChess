package xyz.chengzi.aeroplanechess.view;

import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.listener.GameStateListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
        NotationSelectorComponent notationSelectorComponent = new NotationSelectorComponent();
        diceSelectorComponent.setLocation(396, 585);
        notationSelectorComponent.setLocation(396 - 220, 620);
        add(diceSelectorComponent);
        add(notationSelectorComponent);

        JButton button = new JButton("roll");
        button.addActionListener((e) -> {
            if (diceSelectorComponent.isRandomDice()) {
                int dice1 = controller.rollDice();
                num1 = dice1 >> 16;
                num2 = dice1 & 0x00ff;
                if (dice1 != -1) {
                    statusLabel.setText(String.format("[%s] Rolled a (%d)(%d) , please to choose:",
                            PLAYER_NAMES[controller.getCurrentPlayer()], num1, num2));

                } else {
                    JOptionPane.showMessageDialog(this, "You have already rolled the dice");
                }
            } else {
                JOptionPane.showMessageDialog(this, "You selected " + diceSelectorComponent.getSelectedDice());
            }
        });

//        JButton button1 = new JButton("choose");
//        button1.addActionListener((e) -> {
//            String s;
//            switch (notationSelectorComponent.WhichNotationToChoose()) {
//                case 0:
//                    s = "+";
//                    break;
//                case 1:
//                    s = "-";
//                    break;
//                case 2:
//                    s= "x";
//                    break;
//                default:
//                    s="/";
//            }
//            System.out.println(s);
//            statusLabel.setText(String.format("You choose: ",s));
//        });


        button.setLocation(668, 585);
        button.setFont(button.getFont().deriveFont(18.0f));
        button.setSize(90, 30);
        add(button);
//        button1.setLocation(668, 615);
//        button1.setFont(button1.getFont().deriveFont(18.0f));
//        button1.setSize(90, 30);
//        add(button1);
    }


    @Override
    public void onPlayerStartRound(int player) {
        statusLabel.setText(String.format("[%s] Please roll the dice", PLAYER_NAMES[player]));
    }

    @Override
    public void onPlayerEndRound(int player) {

    }
}
