package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//TODO: to jest bardzo słabe
class SidePanel extends JPanel {
    JButton passButton;
    JLabel turnInfo;
    ClientFrame clientFrame;
    public SidePanel(ClientFrame clientFrame) {
        this.clientFrame = clientFrame;
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.passButton = new JButton("Pas");
        passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientFrame.client.commandWriter.passTurn();
                setTurnInfo("pas");
            }
        });
        this.turnInfo = new JLabel("text");
        this.add(Box.createVerticalGlue());
        JPanel p1 = new JPanel();
        p1.setOpaque(false);
        passButton.setPreferredSize(new Dimension(70,70));
        passButton.setFocusable(false);
        p1.setLayout(new FlowLayout(FlowLayout.CENTER,0,30));
        p1.add(passButton);
        p1.add(turnInfo);
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

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(80,this.getParent().getHeight());
    }
}
