package server;

/**
 * Klasa z funkcją inicjalizującą serwer bez użycia GUI
 */
public class NoGUIInitializer {
    /**
     * Funkcja inicjalizująca serwer bez GUI
     * @param S serwer do wystartowania
     * @param port port na którym chcemy wystartować
     * @param numberOfPlayers liczba graczy serwera
     */
    public static void startServerNoGUI(Server S, String port, String numberOfPlayers) {
        try {
            int portI = Integer.parseInt(port);
            if (portI > 65535 || portI < 0)
            {
                throw new NumberFormatException();
            }
            int numberOfPlayersI = Integer.parseInt(numberOfPlayers);
            if (numberOfPlayersI > 6 || numberOfPlayersI < 2 || numberOfPlayersI == 5)
            {
                throw new NumberFormatException();
            }
            S.startServer(portI,numberOfPlayersI);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Podano nieprawidlowe dane");
        }
        catch (Exception e) {
            System.exit(0);
        }

    }
}
