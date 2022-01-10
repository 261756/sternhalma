package server.boardTools;

import server.Cord;

import java.util.ArrayList;

/**
 * Klasa abstrakcyjna tworząca definicję strefy
 */
public abstract class AbstractRegionFactory {

    /**
     * Zwraca listę punktów należących do danej strefy
     * @param region narożnik, który chcemy otrzymać
     * @return lista punktów należących do danej strefy
     */
    public abstract ArrayList<Cord> getRegion(Region region);
}
