package client;

import hex.BoardAndString;
import hex.Hex;

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
     * OBSŁUGIWANE POLECENIA:
     * sendingHexes - metoda konwertuje przychodzący string na tablicę hexów i updateuje ją dla klienta
     * moveMade - metoda po otrzymaniu komunikatu że ktoś wykonał ruch prosi serwer o wysłanie jej tablicy hexów
     */
    public void fetchInstruction()  {
        String command = scanner.nextLine();
        System.out.println(command);
        if (command.startsWith("sendingHexes")) {
            command = scanner.nextLine();
            client.updateBoard(new BoardAndString(command).getBoardValue());
            client.updateFrameBoard();
        }
        if (command.startsWith("assignColor")) {
            client.setPegsColor(Hex.State.valueOf(command.substring(11)));
        }
        if (command.startsWith("moveMade")) {
            client.commandWriter.requestBoardState();
        }
    }
}
