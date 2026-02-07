package gm.carlos.app.model.repository;

import gm.carlos.app.model.entity.LocationBag;

import java.util.List;

public interface ILocationBag {

    void save(LocationBag locationBag);
    void update(LocationBag locationBag);
    void delete(LocationBag locationBag);
    List<LocationBag> getAll();
}
