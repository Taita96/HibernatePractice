package gm.carlos.app.model.service;

import gm.carlos.app.model.data.LocationBagDAO;
import gm.carlos.app.model.entity.LocationBag;

import java.util.List;

public class LocationBagService {

    private LocationBagDAO locationBagDAO = new LocationBagDAO();

    public boolean save(LocationBag locationBag) {
        return locationBagDAO.save(locationBag);
    }

    public boolean update(LocationBag locationBag) {
        return locationBagDAO.update(locationBag);
    }

    public boolean delete(LocationBag locationBag) {
        return locationBagDAO.delete(locationBag);
    }

    public List<LocationBag> getAll() {
        return locationBagDAO.getAll();
    }

    public LocationBag getById(int locationBagid) {
        return locationBagDAO.getById(locationBagid);
    }

}
