package server.boardTools;

import server.Cord;

import java.util.ArrayList;

/**
 * Klasa tworząca definicję strefy
 */
public class RegionFactory extends AbstractRegionFactory {
    /**
     * Zwraca listę punktów należących do danej strefy
     * @param region narożnik, który chcemy otrzymać
     * @return lista punktów należących do danej strefy
     */
    @Override
    public ArrayList<Cord> getRegion(Region region) {
        switch(region) {
            case CENTER -> {
                return getCcords();
            }
            case NORTH -> {
                return getNcords();
            }
            case NORTHEAST -> {
                return getNEcords();
            }
            case NORTHWEST -> {
                return getNWcords();
            }
            case SOUTH -> {
                return getScords();
            }
            case SOUTHEAST -> {
                return getSEcords();
            }
            case SOUTHWEST -> {
                return getSWcords();
            }
            default -> {
                return null;
            }
        }
    }
    /**
     * Uzupełnia tablicę koordynatów środka
     */
    private ArrayList<Cord> getCcords() {
        ArrayList<Cord> cords = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            cords.add(new Cord(4+i,4));
            cords.add(new Cord(4+i,12));
        }
        for (int i = 0; i < 6; i++) {
            cords.add(new Cord(3+i,5));
            cords.add(new Cord(3+i,11));
        }
        for (int i = 0; i < 7; i++) {
            cords.add(new Cord(3+i,6));
            cords.add(new Cord(3+i,10));
        }
        for (int i = 0; i < 8; i++) {
            cords.add(new Cord(2+i,7));
            cords.add(new Cord(2+i,9));
        }
        for (int i = 0; i < 9; i++) {
            cords.add(new Cord(2+i,8));
        }
        return cords;
    }

    /**
     * Uzupełnia tablicę koordynatów północnego trójkąta
     */
    private ArrayList<Cord> getNcords()
    {
        ArrayList<Cord> cords = new ArrayList<>();
        cords.add(new Cord(6,0));
        cords.add(new Cord(5,1));
        cords.add(new Cord(6,1));
        cords.add(new Cord(5,2));
        cords.add(new Cord(6,2));
        cords.add(new Cord(7,2));
        cords.add(new Cord(4,3));
        cords.add(new Cord(5,3));
        cords.add(new Cord(6,3));
        cords.add(new Cord(7,3));
        return cords;
    }

    /**
     * Uzupełnia tablicę koordynatów północno-wschodniego trójkąta
     */
    private ArrayList<Cord> getNEcords()
    {
        ArrayList<Cord> cords = new ArrayList<>();
        cords.add(new Cord(9,4));
        cords.add(new Cord(10,4));
        cords.add(new Cord(11,4));
        cords.add(new Cord(12,4));
        cords.add(new Cord(9,5));
        cords.add(new Cord(10,5));
        cords.add(new Cord(11,5));
        cords.add(new Cord(10,6));
        cords.add(new Cord(11,6));
        cords.add(new Cord(10,7));
        return cords;
    }
    /**
     * Uzupełnia tablicę koordynatów północno-zachodniego trójkąta
     */
    private ArrayList<Cord> getNWcords()
    {
        ArrayList <Cord> cords = new ArrayList<>();
        cords.add(new Cord(0,4));
        cords.add(new Cord(1,4));
        cords.add(new Cord(2,4));
        cords.add(new Cord(3,4));
        cords.add(new Cord(0,5));
        cords.add(new Cord(1,5));
        cords.add(new Cord(2,5));
        cords.add(new Cord(1,6));
        cords.add(new Cord(2,6));
        cords.add(new Cord(1,7));
        return cords;
    }
    /**
     * Uzupełnia tablicę koordynatów południowo-wschodniego trójkąta
     */
    private ArrayList<Cord> getSEcords()
    {

        ArrayList <Cord> cords = new ArrayList<>();
        cords.add(new Cord(9,12));
        cords.add(new Cord(10,12));
        cords.add(new Cord(11,12));
        cords.add(new Cord(12,12));
        cords.add(new Cord(9,11));
        cords.add(new Cord(10,11));
        cords.add(new Cord(11,11));
        cords.add(new Cord(10,10));
        cords.add(new Cord(11,10));
        cords.add(new Cord(10,9));
        return cords;
    }
    /**
     * Uzupełnia tablicę koordynatów południowo-zachodniego trójkąta
     */
    private ArrayList<Cord> getSWcords()
    {
        ArrayList <Cord> cords = new ArrayList<>();
        cords.add(new Cord(0,12));
        cords.add(new Cord(1,12));
        cords.add(new Cord(2,12));
        cords.add(new Cord(3,12));
        cords.add(new Cord(0,11));
        cords.add(new Cord(1,11));
        cords.add(new Cord(2,11));
        cords.add(new Cord(1,10));
        cords.add(new Cord(2,10));
        cords.add(new Cord(1,9));
        return cords;
    }
    /**
     * Uzupełnia tablicę koordynatów południowego trójkąta
     */
    private ArrayList<Cord> getScords()
    {
        ArrayList <Cord> cords = new ArrayList<>();
        cords.add(new Cord(6,16));
        cords.add(new Cord(5,15));
        cords.add(new Cord(6,15));
        cords.add(new Cord(5,14));
        cords.add(new Cord(6,14));
        cords.add(new Cord(7,14));
        cords.add(new Cord(4,13));
        cords.add(new Cord(5,13));
        cords.add(new Cord(6,13));
        cords.add(new Cord(7,13));
        return cords;
    }
}
