package client;

import java.io.IOException;

/**
 * Klasa tworząca instancję klienta.
 */
public class ClientStarter {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        //client.setCommunication(new Socket("localhost", 59898));
        client.display(800,800);
        client.startConfiguration("Podaj adres serwera i port");
        client.play();
    }
}
