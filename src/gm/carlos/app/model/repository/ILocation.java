package gm.carlos.app.model.repository;

import gm.carlos.app.model.entity.Location;

import java.util.List;

public interface ILocation {

    void save(Location location);
    void update(Location location);
    void delete(Location location);
    List<Location> getAll();
}
