import hex.Hex;
import org.junit.Assert;
import org.junit.Test;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.net.Socket;
import java.io.IOException;


public class TestRecievingHexes {
    @Test
    public void TEST1() throws IOException, ClassNotFoundException
    {
        var socket = new Socket("localhost", 59898);
        ObjectInputStream inObject = new ObjectInputStream(socket.getInputStream());
        var out = new PrintWriter(socket.getOutputStream(), true);
        out.println("requestHexes");
        String serverResponse = (String) inObject.readObject();
        Hex[][] hexes = new Hex[17][17];
        System.out.println(serverResponse);
        if (serverResponse.equals("sendingHexes")) {

            hexes = (Hex[][]) inObject.readObject();

            System.out.println("xD");
        }
        Assert.assertEquals(hexes[0][0].getState(), Hex.State.PLAYER1);
        Assert.assertEquals(hexes[1][0].getState(), Hex.State.EMPTY);
        Assert.assertNotEquals(hexes[0][0].getState(), Hex.State.EMPTY);


        out.println("requestHexes");
        serverResponse = (String) inObject.readObject();
        System.out.println(serverResponse);
        if (serverResponse.equals("sendingHexes")) {

            hexes = (Hex[][]) inObject.readObject();

            System.out.println("xD");
        }
        Assert.assertEquals(hexes[0][0].getState(), Hex.State.PLAYER1);

        out.println("requestxD");
        serverResponse = (String) inObject.readObject();
        System.out.println(serverResponse);
        Assert.assertEquals(serverResponse, "unknownCommand");
    }
}