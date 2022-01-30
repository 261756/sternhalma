package client.gui;

import javax.swing.*;
import java.awt.*;

public class ReplaySetupPanel extends JPanel {
    private JComboBox<String> gameCombo;
    public ReplaySetupPanel(String[] gameNumbers) {
        super(new GridLayout(0,1));
        JLabel textLabel = new JLabel("Dostępne gry");
        gameCombo = new JComboBox<>(gameNumbers);
        JLabel infoLabel = new JLabel("Czy chcesz zobaczyć powtórkę gry?");
        this.add(textLabel);
        this.add(gameCombo);
        this.add(infoLabel);
    }
    public String getSelectedGame() {
        return (String) gameCombo.getSelectedItem();
    }
}
