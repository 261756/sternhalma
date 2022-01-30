package server.boardTools;

import hex.Hex;
import server.sql.QuerySQL;

public class ReplayStateCreator {
    private int numberOfMoves;
    private int currentMove;
    private int gameId;
    private Hex[][] hexes;

    public ReplayStateCreator(AbstractStateBuilder boardBuilder, int gameId) {
        this.gameId = gameId;
        this.currentMove = 0;
        int numberOfPlayers = QuerySQL.getNumberOfPlayers(gameId);
        this.numberOfMoves = QuerySQL.getNumberOfMoves(gameId);
        boardBuilder.createEmptyBoard();
        for (Region region : Region.values()) {
            boardBuilder.initRegion(Hex.State.EMPTY, region);
        }
        switch(numberOfPlayers) {
            case 2 -> {
                boardBuilder.initRegion(Hex.State.RED, Region.NORTH);
                boardBuilder.initRegion(Hex.State.BLUE, Region.SOUTH);
            }
            case 3 -> {
                boardBuilder.initRegion(Hex.State.RED, Region.NORTH);
                boardBuilder.initRegion(Hex.State.GREEN, Region.SOUTHEAST);
                boardBuilder.initRegion(Hex.State.BLACK, Region.SOUTHWEST);
            }
            case 4 -> {
                boardBuilder.initRegion(Hex.State.RED, Region.NORTH);
                boardBuilder.initRegion(Hex.State.BLUE, Region.SOUTH);
                boardBuilder.initRegion(Hex.State.YELLOW, Region.NORTHWEST);
                boardBuilder.initRegion(Hex.State.GREEN, Region.SOUTHEAST);
            }
            case 6 -> {
                boardBuilder.initRegion(Hex.State.RED, Region.NORTH);
                boardBuilder.initRegion(Hex.State.BLUE, Region.SOUTH);
                boardBuilder.initRegion(Hex.State.YELLOW, Region.NORTHWEST);
                boardBuilder.initRegion(Hex.State.GREEN, Region.SOUTHEAST);
                boardBuilder.initRegion(Hex.State.BLACK, Region.SOUTHWEST);
                boardBuilder.initRegion(Hex.State.WHITE, Region.NORTHEAST);
            }
        }
        hexes = boardBuilder.getHexes();
    }
    public boolean setState(int moveNumber) {
        if (currentMove < numberOfMoves) {
            while (moveNumber >= currentMove) {
                swap(QuerySQL.getMove(this.gameId,currentMove));
                currentMove++;
            }
            return true;
        } else {
            return false;
        }

    }
    public Hex[][] getHexes() {
        return hexes;
    }
    private void swap(int[] cords) {
        Hex.State temp = hexes[cords[2]][cords[3]].getState();
        hexes[cords[2]][cords[3]] = new Hex(hexes[cords[0]][cords[1]].getState());
        hexes[cords[0]][cords[1]] = new Hex(temp);
    }
}
