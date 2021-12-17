package client;

import hex.BoardAndString;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Klasa odbierająca komendy z serwera
 */
public class CommandReader {
    Client client;
    Scanner scanner;
    CommandReader(Client client) {
        this.client = client;
    }

    /**
     * Ustawia połączenie
     * @param socket socket
     */
    public void setConnection(Socket socket) {
        try {
            scanner = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public boolean hasNext() {
        return scanner.hasNextLine();
    }
    /**
     * Metoda pobiera polecenia z serwera, następnie wywołuje odpowienie metody na kliencie
     */
    public void fetchInstruction()  {
        String command = scanner.nextLine();
        if (command.startsWith("sendingHexes")) {
            command = scanner.nextLine();
            client.updateBoard(new BoardAndString(command).getBoardValue());
        }
    }
}
