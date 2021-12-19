package server.gui;

import server.Server;
import server.ServerStarter;

import javax.swing.*;
import java.awt.*;

public class StartPopup extends JFrame {

    Server server;
    PortField portField = new PortField(this);
    ComboNumberOfPlayers comboNumberOfPlayers = new ComboNumberOfPlayers(this);
    public StartPopup(Server server)
    {
        this.setTitle("Wybierz parametry");
        this.getContentPane().setLayout(new GridLayout(3,1));
        this.server = server;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,200);
        this.setLocationRelativeTo(null);
        //JPanel contain = new JPanel(new GridLayout(1,2));
        this.getContentPane().add(portField);
        this.getContentPane().add(comboNumberOfPlayers);
        //this.getContentPane().add(contain);
        this.getContentPane().add(new AcceptButton(this));
        this.setVisible(true);
    }

    /**
     * TODO: Weryfikacja inputu
     * @throws Exception
     */
    public void sendInput() throws Exception {
        try {
            int port = Integer.parseInt(portField.getText());
            int numberOfPlayers = (int) comboNumberOfPlayers.getSelectedItem();
            this.setVisible(false);
            Thread serverThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        server.startServer(port, numberOfPlayers);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            serverThread.start();


        }
        catch (Exception e)
        {
            System.out.println("Zły format danych");
        }

    }

}
