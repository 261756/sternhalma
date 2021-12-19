package client.gui;

import client.Client;

import java.io.IOException;
import java.net.Socket;

public class ClientStarter {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        //client.setCommunication(new Socket("localhost", 59898));
        client.startConfiguration("Podaj adres serwera i port");
        client.play();
    }
}
