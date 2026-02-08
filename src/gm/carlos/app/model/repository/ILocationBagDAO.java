package gm.carlos.app.model.repository;

import gm.carlos.app.model.entity.LocationBag;

import java.util.List;

public interface ILocationBagDAO {

    boolean save(LocationBag locationBag);
    boolean update(LocationBag locationBag);
    boolean delete(LocationBag locationBag);
    List<LocationBag> getAll();
    LocationBag getById(int locationBagId);
}
