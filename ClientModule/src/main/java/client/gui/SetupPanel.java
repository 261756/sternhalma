package client.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Panel z dwoma TextField, wykorzystywany, aby pobrać adres serwera i port
 */
public class SetupPanel extends JPanel {
    private final JTextField hostAddress;
    private final JTextField portField;
    private final JLabel hostAddressLabel;
    private final JLabel portLabel;
    private final JLabel infoLabel;

    /**
     * Konstruktor
     */
    public SetupPanel() {
        super(new GridLayout(0,1));
        this.hostAddress = new JTextField("localhost");
        this.portField = new JTextField("59898");
        this.hostAddressLabel = new JLabel("Adres serwera");
        this.portLabel = new JLabel("Port");
        this.infoLabel = new JLabel();

        this.add(hostAddressLabel);
        this.add(hostAddress);
        this.add(portLabel);
        this.add(portField);
        this.add(infoLabel);
    }

    /**
     * Zwraca adres serwera jako String
     * @return adres serwera
     */
    public String getAddressText() {
        return hostAddress.getText();
    }

    /**
     * Zwraca port jako String
     * @return port
     */
    public String getPortText() {
        return  portField.getText();
    }

    /**
     * Ustawia wiadomość dla użytkownika
     * @param message wiadomość dla użytkownika
     */
    public void setInfoLabel(String message) {
        infoLabel.setText(message);
    }

}