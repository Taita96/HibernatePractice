package gm.carlos.app.model.repository;

import gm.carlos.app.model.entity.Location;

import java.util.List;

public interface ILocationDAO {

    boolean save(Location location);
    boolean update(Location location);
    boolean delete(Location location);
    List<Location> getAll();
    Location getById(int locationId);
    Location findByAisleAndShelf(String aisle, String shelf);
}
