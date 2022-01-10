package server.gui;

import javax.swing.*;

/**
 * ComboBox do wybrania liczby graczy.
 */
public class ComboNumberOfPlayers extends JComboBox<Integer> {

    private StartPopup startPopup;

    /**
     * Konstruktor
     * @param startPopup parent element
     */
    ComboNumberOfPlayers(StartPopup startPopup)
    {
        //super(new Integer[]{2, 3, 4, 5, 6});
        super(new Integer[]{2, 3, 4, 6});
        this.startPopup = startPopup;

    }

}
