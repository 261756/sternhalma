package clientTests;

import hex.BoardAndString;
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
   /* @Test
    public void TEST1() throws IOException, ClassNotFoundException
    {
        var socket = new Socket("localhost", 59898);
        Scanner scan = new Scanner(socket.getInputStream());
        var out = new PrintWriter(socket.getOutputStream(), true);
        String serverResponse = scan.nextLine();
        out.println("requestHexes");
        serverResponse = scan.nextLine();
        Hex[][] hexes = new Hex[25][17];
        System.out.println(serverResponse);
        System.out.println("lol");
        if (serverResponse.equals("sendingHexes")) {

            System.out.println("lol");
            hexes = new BoardAndString(scan.nextLine()).getBoardValue();
            System.out.println("xD");
        }
        Assert.assertEquals(hexes[0][0].getState(), Hex.State.NULL);
        Assert.assertEquals(hexes[1][0].getState(), Hex.State.NULL);
        Assert.assertNotEquals(hexes[0][0].getState(), Hex.State.RED);


        out.println("requestxD");
        serverResponse = scan.nextLine();
        System.out.println(serverResponse);
        Assert.assertEquals(serverResponse, "unknownCommand");
    }*/
    /*@Test
    public void TEST1looped() throws IOException, ClassNotFoundException {
        var socket = new Socket("localhost", 59898);

        var in = socket.getInputStream();
        ObjectInputStream inObject = new ObjectInputStream(socket.getInputStream());
        var out = new PrintWriter(socket.getOutputStream(), true);
            out.println("requestHexes");
            String serverResponse = (String) inObject.readObject();
            Hex[][] hexes = new Hex[25][17];
            System.out.println(serverResponse);
            if (serverResponse.equals("sendingHexes")) {

                hexes = (Hex[][]) inObject.readObject();

                System.out.println("xD");
            }
            Assert.assertEquals(hexes[0][0].getState(), Hex.State.RED);
            Assert.assertEquals(hexes[1][0].getState(), Hex.State.EMPTY);
            Assert.assertNotEquals(hexes[0][0].getState(), Hex.State.EMPTY);


            out.println("requestHexes");
            serverResponse = (String) inObject.readObject();
            System.out.println(serverResponse);
            if (serverResponse.equals("sendingHexes")) {

                hexes = (Hex[][]) inObject.readObject();

                System.out.println("xD");
            }
            Assert.assertEquals(hexes[0][0].getState(), Hex.State.RED);

            out.println("requestxD");
            serverResponse = (String) inObject.readObject();
            System.out.println(serverResponse);
            Assert.assertEquals(serverResponse, "unknownCommand");
        }*/


}
