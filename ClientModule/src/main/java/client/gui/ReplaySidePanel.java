package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReplaySidePanel extends BasicSidePanel {
    public ReplaySidePanel(ClientFrame clientFrame) {
        super(clientFrame);
        JButton nextButton = new JButton("Następny");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientFrame.client.requestNext();
            }
        });
        this.setBackground(new Color(255, 197, 168));
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        JPanel p1 = new JPanel();
        p1.setOpaque(false);
        nextButton.setPreferredSize(new Dimension(WIDTH - 10,WIDTH - 10));
        nextButton.setFocusable(false);
        p1.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
        p1.add(nextButton);
        p1.add(turnInfo);
        this.add(p1);
        this.setVisible(false);
    }
}
