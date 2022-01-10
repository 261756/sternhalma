package client;

import client.gui.ClientFrame;
import hex.Hex;

import java.io.IOException;
import java.net.Socket;

/**
 * Klasa klienta commandReader odczytuje komendy, commandWriter wysyła komendy
 */
public class Client {
    private static Socket socket;
    private Hex.State pegsColor;
    private boolean myTurn;
    private final ClientFrame clientFrame;
    /**
     * CommandReader przypisany do klienta, pisze do serwera.
     */
    public CommandReader commandReader;
    /**
     * CommandWriter przypisany do klienta, czyta z serwera.
     */
    public CommandWriter commandWriter;
    /**
     * Klasa przechowująca informacje o stanie planszy pobrane z serwera
     */
    public ClientGameState gameState;

    /**
     * Konstruktor
     */
    public Client() {
        this.clientFrame = new ClientFrame(this);
        this.gameState = new ClientGameState();
        this.commandReader = new CommandReader(this);
        this.commandWriter = new CommandWriter(this);
    }

    /**
     * Ustawia wielkość okienka klienta i zmienia stan okienka na widoczny
     * @param width szerokość okienka
     * @param height wysokość okienka
     */
    public void display(int width, int height) {
        clientFrame.setSize(width,height);
        clientFrame.setVisible(true);
    }

    /**
     * Wywołuje odpowiednie metody z okienka odpowiedzialne z wyświetlenie dialogu,
     * aby pobrać dane do połączenia z serwerem. Następnie próbuje się połączyć z serwerem.
     * @param message komunikat wyświetlany na dialogu
     */
    public void startConfiguration(String message) {
        try {
            String[] array = clientFrame.showSetupOptions(message);
            if (Integer.parseInt(array[1]) > 65535 || Integer.parseInt(array[1]) < 0) {
                throw new NumberFormatException();
            }
            setCommunication(new Socket(array[0],Integer.parseInt(array[1])));
        } catch (IOException e) {
            startConfiguration("Błąd połączenia. Spróbuj ponownie.");
        } catch (NumberFormatException e) {
            startConfiguration("Zły format danych.");
        } catch (Exception e) {
            System.exit(0);
        }

    }
    /**
     * konfiguracja commandWriter, commandReader
     * @param socket socket
     */
    public void setCommunication(Socket socket) {
        Client.socket = socket;
        commandWriter.setConnection(socket);
        commandReader.setConnection(socket);
    }

    /**
     * Odświerza stan lokalnej planszy
     * @param array tablica Hex[][]
     */
    public void updateBoard(Hex[][] array) {
        gameState.setHexes(array);
    }

    /**
     * Odświerza stan wyświetlanej planszy
     */
    public void updateFrameBoard(){
        clientFrame.updateBoard();
    }
    /**
     * Główna metoda. Najpierw prosi o stan planszy, potem przyjmuje polecenia
     * @throws IOException od socket.close() nie wiem czy to będzie działać
     */
    public void play() throws IOException {
        commandReader.fetchInstruction(); // przyjmowanie przypisanego koloru
        clientFrame.setTitle("Gracz " + pegsColor.name());
        commandWriter.requestBoardState();
        if (commandReader.hasNext())
            commandReader.fetchInstruction();
        clientFrame.updateBoard();
        try {
            while (commandReader.hasNext()) {
                commandReader.fetchInstruction();
            }
            commandWriter.quit();
        } catch (Exception e) {
        e.printStackTrace();
        } finally {
            socket.close();
            clientFrame.dispose();
        }
    }

    /**
     * Zwraca Kolor tego klienta
     * @return Hex.State przypisany do tego klienta
     */
    public Hex.State getPegsColor()
    {
        return  pegsColor;
    }

    /**
     * Ustawia Kolor przypisany do klienta
     * @param color - Hex.State mający zostać przypisany
     */
    public void setPegsColor(Hex.State color)
    {
        pegsColor = color;
    }

    /**
     * Zmienia flagę tury tego gracza
     * @param bool true- tura tego gracza, false- w przeciwnym wypadku
     */
    public void setMyTurn(boolean bool) {
        this.myTurn = bool;
    }

    /**
     * Zwraca czy teraz tura tego gracza
     * @return true- jeśli tura tego gracza
     */
    public boolean isMyTurn() {
        return this.myTurn;
    }

    /**
     * Powiadamia gracza czyja teraz jest tura
     * @param substring kolor gracza, który teraz ma turę
     */
    public void setCurrentPlayer(String substring) {
        if (substring.equals(pegsColor.name())) {
            setMyTurn(true);
            this.clientFrame.notify(MessageFactory.myTurnMsg());
        } else {
            setMyTurn(false);
            if (substring.equals("gameEnded"))
            {
                this.clientFrame.notify("<html><div style='text-align: center;'>Koniec gry.</div><br></html>");
            }
            else {
                this.clientFrame.notify(MessageFactory.opponentTurnMsg(substring));
            }
        }
    }

    /**
     * Prosi serwer o obecny stan gry
     */
    public void requestHexes() {
        commandWriter.requestBoardState();
    }

    /**
     * Informuje gracza o zwycięstwie jednego z graczy
     * @param substring kolor + miejsce
     */
    public void updateWinners(String substring)
    {
        int size = substring.length();
        String numberS = substring.substring(size-1);
        int number = Integer.parseInt(numberS);
        String color = substring.substring(0,size-1);
        this.clientFrame.updateWinners(MessageFactory.winnerMsg(clientFrame.getWinnerMsg(),color,number));
    }

    /**
     * Informuje gracza o wyjściu z gry jednego z graczy
     * @param substring kolor gracza, który wyszedł
     */
    public void updateLeavers(String substring)
    {
        this.clientFrame.updateWinners(MessageFactory.leftMsg(clientFrame.getWinnerMsg(),substring));
    }
}
