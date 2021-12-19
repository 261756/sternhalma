package server.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AcceptButton extends JButton {
    StartPopup startPopup;
    AcceptButton(StartPopup startPopup)
    {
        super("Akceptuj");
        this.startPopup = startPopup;
        this.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    startPopup.sendInput();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


}
