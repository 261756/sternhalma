package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SidePanel extends JPanel {
    private static final int WIDTH = 80;
    private JButton passButton;
    private JLabel turnInfo;
    private ClientFrame clientFrame;
    public SidePanel(ClientFrame clientFrame) {
        this.clientFrame = clientFrame;
        this.setBackground(new Color(98, 36, 1));
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.passButton = new JButton("Pas");
        passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clientFrame.client.isMyTurn()) {
                    clientFrame.client.commandWriter.passTurn();
                    setTurnInfo("pas");
                }
            }
        });
        this.turnInfo = new JLabel();
        this.add(Box.createVerticalGlue());
        JPanel p1 = new JPanel();
        p1.setOpaque(false);
        passButton.setPreferredSize(new Dimension(WIDTH - 10,WIDTH - 10));
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
        return new Dimension(WIDTH,this.getParent().getHeight());
    }
}
