package xyz.chengzi.aeroplanechess.view;

import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.listener.GameStateListener;

import javax.swing.*;

public class GameFrame extends JFrame implements GameStateListener {
    private static final String[] PLAYER_NAMES = {"Yellow", "Blue","Green","Red"};
    private int NumberOfRolling = 0;
    private final JLabel statusLabel = new JLabel();

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
        diceSelectorComponent.setLocation(396, 585);
        add(diceSelectorComponent);

        JButton button = new JButton("roll");
        JButton button1 = new JButton("roll");
        button.addActionListener((e) -> {
            if(NumberOfRolling <=1){
                if (diceSelectorComponent.isRandomDice()) {
                    int dice = controller.rollDice();
                    if (dice != -1) {
                        statusLabel.setText(String.format("[%s] Rolled a %c (%d)",
                                PLAYER_NAMES[controller.getCurrentPlayer()], '\u267F' + dice, dice));
                    } else {
                        JOptionPane.showMessageDialog(this, "You have already rolled the dice");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "You selected " + diceSelectorComponent.getSelectedDice());
                }
                NumberOfRolling+=1;
            }else {
                NumberOfRolling =0;
            }
        });

        button1.addActionListener((e) -> {
            if(NumberOfRolling <=1){
                if (diceSelectorComponent.isRandomDice()) {
                    int dice = controller.rollDice();
                    if (dice != -1) {
                        statusLabel.setText(String.format("[%s] Rolled a %c (%d)",
                                PLAYER_NAMES[controller.getCurrentPlayer()], '\u267F' + dice, dice));
                    } else {
                        JOptionPane.showMessageDialog(this, "You have already rolled the dice");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "You selected " + diceSelectorComponent.getSelectedDice());
                }
                NumberOfRolling+=1;
            }else {
                NumberOfRolling =0;
            }

        });

        button.setLocation(668, 585);
        button.setFont(button.getFont().deriveFont(18.0f));
        button.setSize(90, 30);
        add(button);
        button1.setLocation(668, 624);
        button1.setFont(button1.getFont().deriveFont(18.0f));
        button1.setSize(90, 30);
        add(button1);
    }


    @Override
    public void onPlayerStartRound(int player) {
        statusLabel.setText(String.format("[%s] Please roll the dice", PLAYER_NAMES[player]));
    }

    @Override
    public void onPlayerEndRound(int player) {

    }
}
