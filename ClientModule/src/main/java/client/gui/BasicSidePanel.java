package client.gui;

import javax.swing.*;
import java.awt.*;

public class BasicSidePanel extends JPanel {
    protected JLabel turnInfo;
    protected JLabel winnersInfo;
    protected ClientFrame clientFrame;
    protected static final int WIDTH = 120;

    public BasicSidePanel(ClientFrame clientFrame) {
        this.clientFrame = clientFrame;
        this.turnInfo = new JLabel();
        this.winnersInfo = new JLabel();
    }

    /**
     * Zmieni widoczność panelu na widoczny
     */
    public void displaySidePanel() {
        this.setVisible(true);
    }

    /**
     * Ustaw informację o turze
     *
     * @param turnInfo informacja
     */
    public void setTurnInfo(String turnInfo) {
        this.turnInfo.setText(turnInfo);
    }

    /**
     * Ustaw informację o zwycięzcy
     *
     * @param winnersInfo informacja
     */
    public void setWinnersInfo(String winnersInfo) {
        this.winnersInfo.setText(winnersInfo);
    }

    /**
     * Zwraca wcześniejsze informacje o zwycięzcach
     *
     * @return wcześniejsze informacje
     */
    public String getWinnersInfo() {
        return winnersInfo.getText();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SidePanel.WIDTH, this.getParent().getHeight());
    }
}
