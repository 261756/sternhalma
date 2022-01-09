package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SidePanel extends JPanel {
    private static final int WIDTH = 120;
    private JButton passButton;
    private JLabel turnInfo;
    private JLabel winnersInfo;
    private ClientFrame clientFrame;
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
        this.winnersInfo = new JLabel("<html></html>");
        this.add(Box.createVerticalGlue());
        JPanel p1 = new JPanel();
        p1.setOpaque(false);
        passButton.setPreferredSize(new Dimension(WIDTH - 10,WIDTH - 10));
        passButton.setFocusable(false);
        p1.setLayout(new FlowLayout(FlowLayout.CENTER,0,30));
        p1.add(passButton);
        p1.add(turnInfo);
        p1.add(winnersInfo);
        this.add(p1);
        this.add(Box.createVerticalGlue());
        this.setVisible(false);
    }

    public void displaySidePanel() {
        this.setVisible(true);
    }
    public void setTurnInfo(String turnInfo) {
        this.turnInfo.setText(turnInfo);
    }
    public void setWinnersInfo(String winnersInfo) {
        this.winnersInfo.setText(winnersInfo);
    }
    public String getWinnersInfo()
    {
        return winnersInfo.getText();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH,this.getParent().getHeight());
    }
}
