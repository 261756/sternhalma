package server.gui;

import javax.swing.*;

/**
 * TextField do wpisania numeru portu.
 */
public class PortField extends JTextField {
    /**
     * parent element
     */
    StartPopup startPopup;

    /**
     * Konstruktor
     * @param startPopup parent element
     */
    PortField(StartPopup startPopup)
    {
        this.setText("59898");
        this.startPopup = startPopup;

    }
}
