package server.gui;

import javax.swing.*;

/**
 * TextField do wpisania numeru portu.
 */
public class PortField extends JTextField {
    StartPopup startPopup;
    PortField(StartPopup startPopup)
    {
        this.setText("59898");
        this.startPopup = startPopup;

    }
}
