package serverTests;

import hex.Hex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.Cord;
import server.GameState;
import server.MoveValidator;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestMoveValidator {
    GameState game = mock(GameState.class);
    MoveValidator validator = new MoveValidator(game);
    Hex[][] array = new Hex[13][17];
    private static final ArrayList<Cord> Ncords;
    private static final ArrayList<Cord> Scords;
    static  {
        Ncords = new ArrayList<Cord>();
        Ncords.add(new Cord(6,0));
        Ncords.add(new Cord(5,1));
        Ncords.add(new Cord(6,1));
        Ncords.add(new Cord(5,2));
        Ncords.add(new Cord(6,2));
        Ncords.add(new Cord(7,2));
        Ncords.add(new Cord(4,3));
        Ncords.add(new Cord(5,3));
        Ncords.add(new Cord(6,3));
        Ncords.add(new Cord(7,3));
        Scords = new ArrayList<Cord>();
        Ncords.add(new Cord(16,6));
    }
    @Before
    public void setup() {
        validator.newTurn(Hex.State.BLUE);
        when(game.getNcords()).thenReturn(Ncords);
        when(game.getScords()).thenReturn(Scords);
        when(game.getHexes()).thenReturn(array);
        for (int i = 0; i < 17; i++) {
            for(int j = 0; j<13; j++) {
                array[j][i] = new Hex(Hex.State.EMPTY);
            }
        }
    }
    @Test
    public void testMoveIsLegalStep1() {
        array[6][0] = new Hex(Hex.State.BLUE);
        Assert.assertFalse(validator.moveIsLegal(6, 0, 10, 10));
    }
    @Test
    public void testMoveIsLegalStep2() {
        array[6][0] = new Hex(Hex.State.BLUE);
        Assert.assertTrue(validator.moveIsLegal(6, 0, 6, 1));
    }
    @Test
    public void testMoveIsLegalJump1() {
        array[6][16] = new Hex(Hex.State.BLUE);
        Assert.assertFalse(validator.moveIsLegal(6, 16, 7, 13));
    }
    @Test
    public void testMoveIsLegalJump2() {
        array[6][16] = new Hex(Hex.State.BLUE);
        array[6][15] = new Hex(Hex.State.BLUE);
        Assert.assertTrue(validator.moveIsLegal(6, 16, 7, 14));
    }
    @Test
    public void testMoveIsLegalJumpOnOccupied() {
        array[6][16] = new Hex(Hex.State.BLUE);
        array[6][15] = new Hex(Hex.State.BLUE);
        array[7][14] = new Hex(Hex.State.BLUE);
        Assert.assertFalse(validator.moveIsLegal(6, 16, 7, 14));
    }
    @Test
    public void testMoveIsLegalStepOut1() {
        validator.newTurn(Hex.State.BLUE);
        array[6][3] = new Hex(Hex.State.BLUE);
        Assert.assertFalse(validator.moveIsLegal(6, 3, 6, 4));
        Assert.assertTrue(validator.moveIsLegal(6, 3, 7, 2));
    }
    @Test
    public void testMoveIsLegalStepOut2() {
        validator.newTurn(Hex.State.RED);
        array[6][3] = new Hex(Hex.State.RED);
        Assert.assertTrue(validator.moveIsLegal(6, 3, 6, 4));
    }

}
