package client.gui;

import javax.swing.*;
import java.awt.*;

public class ReplaySetupPanel extends JPanel {
    private JComboBox<Integer> gameCombo;
    public ReplaySetupPanel(Integer[] gameNumbers) {
        super(new GridLayout(0,1));
        JLabel textLabel = new JLabel("Dostępne gry");
        gameCombo = new JComboBox<>(gameNumbers);
        JLabel infoLabel = new JLabel("Czy chcesz zobaczyć powtórkę gry?");
        this.add(textLabel);
        this.add(gameCombo);
        this.add(infoLabel);
    }
    public int getSelectedGame() {
        return (int) gameCombo.getSelectedItem();
    }
}
