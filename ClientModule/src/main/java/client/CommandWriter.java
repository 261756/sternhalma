package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Klasa wysyłająca odpowiednie polecenia na serwer
 */
public class CommandWriter {
    private final Client client;
    private PrintWriter printWriter;

    /**
     * Konstruktor
     * @param client powiązany klient
     */
    public CommandWriter(Client client) {
        this.client = client;
    }

    /**
     * Ustawia połączenie
     * @param socket socket
     */
    public void setConnection(Socket socket) {
        try {
            this.printWriter = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prosi o stan planszy
     */
    public void requestBoardState() {
        printWriter.println("requestHexes");
    }

    /**
     * Powiadamia serwer o wyjściu użytkownika
     */
    public void quit()
    {
        printWriter.println("quit");
    }

    /**
     * Prosi serwer o ruch
     * @param x1 x początkowe
     * @param y1 y początkowe
     * @param x2 x końcowe
     * @param y2 y końcowe
     */
    public void move(int x1, int y1, int x2, int y2)
    {
        printWriter.println("requestMove" + x1 + " " + y1 + " " + x2 + " " + y2);
    }

    /**
     * Powiadamia serwer o spasowaniu tury
     */
    public void passTurn() {
        printWriter.println("passTurn");
    }

    /**
     * Wysyła zapytanie o stan po ruchu z gry
     * @param number numer ruchu
     */
    public void requestPast(int number) {
        printWriter.println("requestPastHexes" + number);
    }

    public void requestGame(int number) {
        printWriter.println("requestPastGame" + number);
    }

    public void requestGameList() {printWriter.println("requestGames");}
}
