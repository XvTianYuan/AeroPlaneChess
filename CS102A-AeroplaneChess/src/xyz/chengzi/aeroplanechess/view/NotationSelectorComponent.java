package xyz.chengzi.aeroplanechess.view;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class NotationSelectorComponent extends JComponent implements ItemListener {
    private JRadioButton manualDiceRadio;
    private JRadioButton randomDiceRadio;
    private JRadioButton AddButton;
    private JRadioButton SubButton;
    private JRadioButton MulButton;
    private JRadioButton DevButton;
    private JComboBox<Integer> diceComboBox;
//    private boolean randomDice = true;
//    private int numberOfChoose = 0;

    public NotationSelectorComponent() {
        setLayout(null); // Use absolute layout
        setSize(540, 25);

        diceComboBox = new JComboBox<>();

        diceComboBox.setLocation(0, 0);
        diceComboBox.setSize(80, 25);
        diceComboBox.setVisible(false);
        add(diceComboBox);


        AddButton = new JRadioButton("Add");
        SubButton = new JRadioButton("Sub");
        MulButton = new JRadioButton("Mul");
        DevButton = new JRadioButton("Dev");
        //TODO ：fdsfdsfsdf
        AddButton.setLocation(90,0);
        AddButton.setSize(100,25);
        AddButton.setFont(AddButton.getFont().deriveFont(18.0f));
        AddButton.addItemListener(this);
        add(AddButton);

        SubButton.setLocation(200,0);
        SubButton.setSize(100,25);
        SubButton.setFont(SubButton.getFont().deriveFont(18.0f));
        SubButton.addItemListener(this);
        add(SubButton);

        MulButton.setLocation(310,0);
        MulButton.setSize(100,25);
        MulButton.setFont(AddButton.getFont().deriveFont(18.0f));
        MulButton.addItemListener(this);
        add(MulButton);

        DevButton.setLocation(420,0);
        DevButton.setSize(100,25);
        DevButton.setFont(AddButton.getFont().deriveFont(18.0f));
        DevButton.addItemListener(this);
        add(DevButton);

        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(AddButton);
        btnGroup.add(SubButton);
        btnGroup.add(MulButton);
        btnGroup.add(DevButton);
    }

    public int WhichNotationToChoose(){
        if(AddButton.isSelected()){
            return 0;
        }else if(SubButton.isSelected()){
            return 1;
        }else if(MulButton.isSelected()){
            return 2;
        }else{
            return 3;
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        diceComboBox.setVisible(false);
    }
}
