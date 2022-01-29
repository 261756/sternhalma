package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel boczny
 */
class SidePanel extends BasicSidePanel {
    private final JButton passButton;
    /**
     * Konstruktor
     * @param clientFrame parent element
     */
    public SidePanel(ClientFrame clientFrame) {
        super(clientFrame);
        this.setBackground(new Color(255, 197, 168));
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.turnInfo = new JLabel("<html><div style='text-align: center;'>Oczekiwanie na<br>pozostałych graczy</div></html>");
        this.winnersInfo = new JLabel("<html></html>"); //informacje o zwycięzcach i wychodzących
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


}
