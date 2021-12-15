package client;

import client.exceptions.UnknownCommandException;
import hex.Hex;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Klasa odbierająca komendy z serwera
 */
public class CommandReader {
    Client client;
    ObjectInputStream objectInputStream;
    CommandReader(Client client) {
        this.client = client;
    }

    /**
     * Ustawia połączenie
     * @param socket socket
     */
    public void setConnection(Socket socket) {
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metoda pobiera polecenia z serwera, następnie wywołuje odpowienie metody na kliencie
     */
    public void fetchInstruction()  {
        try {
            Object obj = objectInputStream.readObject();
            if (obj instanceof String) {
                switch ((String)obj) {
                    case "sendingHexes" -> {
                        client.updateBoard((Hex[][]) objectInputStream.readObject());
                    }
                    default -> {
                        throw new UnknownCommandException();
                    }
                }
            } else {
                throw new UnknownCommandException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UnknownCommandException e) {
            e.printStackTrace();
        }

    }
}
