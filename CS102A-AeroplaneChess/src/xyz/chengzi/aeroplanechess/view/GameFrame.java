package xyz.chengzi.aeroplanechess.view;

import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.listener.GameStateListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame implements GameStateListener {
    private static final String[] PLAYER_NAMES = {"Yellow", "Blue","Green","Red"};
    private final JLabel statusLabel = new JLabel();
    public int num1;
    public int num2;



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
        NotationSelectorComponent notationSelectorComponent = new NotationSelectorComponent();
        diceSelectorComponent.setLocation(396, 585);
        diceSelectorComponent1.setLocation(396,615);
        notationSelectorComponent.setLocation(396-220,645);
        add(diceSelectorComponent);
        add(diceSelectorComponent1);
        add(notationSelectorComponent);

        JButton button = new JButton("roll");


        button.addActionListener((e) -> {
            if (diceSelectorComponent.isRandomDice()) {
                int dice1 = controller.rollDice();
                num1 = dice1 >> 16;
                num2 = dice1 & 0x00ff;
                controller.setNumberOfDiceOne(num1);
                controller.setNumberOfDiceTwo(num2);
                if (dice1 != -1) {
                    statusLabel.setText(String.format("[%s] Rolled a (%d)(%d) ",
                            PLAYER_NAMES[controller.getCurrentPlayer()], num1, num2));

                } else {
                    JOptionPane.showMessageDialog(this, "You have already rolled the dice");
                }
            } else {
//                JOptionPane.showMessageDialog(this, "You selected " + diceSelectorComponent.getSelectedDice());
                num1 = (Integer) diceSelectorComponent1.getSelectedDice();
                num2 = (Integer) diceSelectorComponent.getSelectedDice();
                statusLabel.setText(String.format("[%s] Selected a (%d)(%d) ",
                    PLAYER_NAMES[controller.getCurrentPlayer()], num1, num2));
                controller.manualDice(num1,num2);
            }
        });

        JButton button1 = new JButton("choose");
        button1.addActionListener((e) -> {
            if(notationSelectorComponent.WhichNotationToChoose() == 0){
                controller.setNotation(0);
                statusLabel.setText(String.format("[%s] Choose + , sum is %d",
                        PLAYER_NAMES[controller.getCurrentPlayer()],controller.getRolledNumber()));
            }else if(notationSelectorComponent.WhichNotationToChoose() == 1){
                controller.setNotation(1);
                if(controller.getRolledNumber() == 0){
                    JOptionPane.showMessageDialog(this, "You can't choose this!");
                }else{
                    statusLabel.setText(String.format("[%s] Choose - , sum is %d",
                            PLAYER_NAMES[controller.getCurrentPlayer()],controller.getRolledNumber()));
                }
            }else if(notationSelectorComponent.WhichNotationToChoose() == 2){
                controller.setNotation(2);
                if(controller.getRolledNumber() == 0){
                    JOptionPane.showMessageDialog(this, "You can't choose this!");
                }else{
                    statusLabel.setText(String.format("[%s] Choose x , sum is %d",
                            PLAYER_NAMES[controller.getCurrentPlayer()],controller.getRolledNumber()));
                }
            }else{
                controller.setNotation(3);
                if(controller.getRolledNumber() == 0){
                    JOptionPane.showMessageDialog(this, "You can't choose this!");
                }else{
                    statusLabel.setText(String.format("[%s] Choose / , sum is %d",
                            PLAYER_NAMES[controller.getCurrentPlayer()],controller.getRolledNumber()));
                }

            }
        });

        button1.setLocation(668,620);
        button1.setFont(button1.getFont().deriveFont(18.0f));
        button1.setSize(90,30);
        add(button1);

        button.setLocation(668, 585);
        button.setFont(button.getFont().deriveFont(18.0f));
        button.setSize(90, 30);
        add(button);


    }


    @Override
    public void onPlayerStartRound(int player) {
        statusLabel.setText(String.format("[%s] Please roll the dice", PLAYER_NAMES[player]));
    }

    @Override
    public void onPlayerEndRound(int player) {

    }
}
