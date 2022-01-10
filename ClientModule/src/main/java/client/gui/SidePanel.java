package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel boczny
 */
class SidePanel extends JPanel {
    private static final int WIDTH = 120;
    private final JButton passButton;
    private final JLabel turnInfo;
    private final JLabel winnersInfo;
    private final ClientFrame clientFrame;
    /**
     * Konstruktor
     * @param clientFrame parent element
     */
    public SidePanel(ClientFrame clientFrame) {
        this.clientFrame = clientFrame;
        this.setBackground(new Color(255, 197, 168));
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.passButton = new JButton("Pas");
        passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clientFrame.client.isMyTurn()) {
                    clientFrame.client.commandWriter.passTurn();
                    //setTurnInfo("pas");
                }
            }
        });
        this.turnInfo = new JLabel("<html><div style='text-align: center;'>Oczekiwanie na<br>pozostałych graczy</div></html>");
        this.winnersInfo = new JLabel("<html></html>"); //informacje o zwycięzcach i wychodzących
        //this.add(Box.createVerticalGlue());
        JPanel p1 = new JPanel();
        p1.setOpaque(false);
        passButton.setPreferredSize(new Dimension(WIDTH - 10,WIDTH - 10));
        passButton.setFocusable(false);
        p1.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
        p1.add(passButton);
        p1.add(turnInfo);
        p1.add(winnersInfo);
        this.add(p1);
        //this.add(Box.createVerticalGlue());
        this.setVisible(false);
    }

    /**
     * Zmieni widoczność panelu na widoczny
     */
    public void displaySidePanel() {
        this.setVisible(true);
    }

    /**
     * Ustaw informację o turze
     * @param turnInfo informacja
     */
    public void setTurnInfo(String turnInfo) {
        this.turnInfo.setText(turnInfo);
    }

    /**
     * Ustaw informację o zwycięzcy
     * @param winnersInfo informacja
     */
    public void setWinnersInfo(String winnersInfo) {
        this.winnersInfo.setText(winnersInfo);
    }

    /**
     * Zwraca wcześniejsze informacje o zwycięzcach
     * @return wcześniejsze informacje
     */
    public String getWinnersInfo()
    {
        return winnersInfo.getText();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH,this.getParent().getHeight());
    }
}
