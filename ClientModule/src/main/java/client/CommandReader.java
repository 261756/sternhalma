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
    private Client client;
    private Scanner scanner;
    public CommandReader(Client client) {
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
     * moveMade -  po otrzymaniu komunikatu że ktoś wykonał ruch prosi serwer o wysłanie jej tablicy hexów
     * assignColor - ustawia kolor gracza na kolor otrzymany z serwera
     * turnChanged - informuje gracza który gracz ma teraz ruch
     */
    public void processInstruction(String command) {
        if (command.startsWith("sendingHexes")) {
            client.updateBoard(BoardAndString.getBoardValue(command.substring(12)));
            client.updateFrameBoard();
        }
        if (command.startsWith("assignColor")) {
            client.setPegsColor(Hex.State.valueOf(command.substring(11)));
        }
        if (command.startsWith("moveMade")) {
            client.requestHexes();
        }
        if (command.startsWith("turnChanged")) {
            client.setCurrentPlayer(command.substring(11));
        }
        if (command.startsWith("won")) {
            client.updateWinners(command.substring(3));
        }
        if (command.startsWith("gameEnded"))
        {
            client.setCurrentPlayer("gameEnded");
        }
        if (command.startsWith("left"))
        {
            client.updateLeavers(command.substring(4));
        }
    }
    public void fetchInstruction()  {
        String command = scanner.nextLine();
        System.out.println(command);
        processInstruction(command);
    }
}
