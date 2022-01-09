package client;

import client.gui.ClientFrame;
import hex.Hex;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Klasa klienta commandReader odczytuje komendy, commandWriter wysyła komendy
 */
public class Client {
    private static Socket socket;
    private Hex.State pegsColor;
    private boolean myTurn;
    private ClientFrame clientFrame;
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

    public Client() {
        this.clientFrame = new ClientFrame(this);
        this.gameState = new ClientGameState();
        this.commandReader = new CommandReader(this);
        this.commandWriter = new CommandWriter(this);
        clientFrame.setSize(800,800);//temp
        clientFrame.setVisible(true);//temp
    }


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
        }

    catch (Exception e) {
        e.printStackTrace();
        } finally {
            socket.close();
            clientFrame.dispose();
        }
    }

    /**
     * @return Hex.State przypisany do tego klienta
     */
    public Hex.State getPegsColor()
    {
        return  pegsColor;
    }

    /**
     * Ustawia Hex.State przypisany do klienta
     * @param color - Hex.State mający zostać przypisany
     */
    public void setPegsColor(Hex.State color)
    {
        pegsColor = color;
    }
    public void setMyTurn(boolean bool) {
        this.myTurn = bool;
    }
    public boolean isMyTurn() {
        return this.myTurn;
    }
    public void setCurrentPlayer(String substring) {
        if (substring.equals(pegsColor.name())) {
            setMyTurn(true);
            this.clientFrame.notify(new MessageFactory().myTurnMsg());
        } else {
            setMyTurn(false);
            if (substring.equals("gameEnded"))
            {
                this.clientFrame.notify("Koniec gry.");
            }
            else {
                this.clientFrame.notify(new MessageFactory().opponentTurnMsg(substring));
            }
        }
    }
    public void updateWinners(String substring)
    {
        int size = substring.length();
        String numberS = substring.substring(size-1);
        int number = Integer.parseInt(numberS);
        String color = substring.substring(0,size-1);
        this.clientFrame.updateWinners(new MessageFactory().winnerMsg(clientFrame.getWinnerMsg(),color,number));
    }
    public void updateLeavers(String substring)
    {
        this.clientFrame.updateWinners(new MessageFactory().leftMsg(clientFrame.getWinnerMsg(),substring));
    }
}
