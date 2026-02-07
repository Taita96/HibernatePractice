package gm.carlos.app.model.service;

import gm.carlos.app.model.data.LocationDAO;
import gm.carlos.app.model.entity.Location;

import java.util.List;

public class LocationService {

    private LocationDAO locationDAO = new LocationDAO();

    public void save(Location location){
        locationDAO.save(location);
    }

    public void delete(Location location){
        locationDAO.delete(location);
    }
    public void update(Location location){
        locationDAO.update(location);
    }

    public List<Location> getAll(){
        return locationDAO.getAll();
    }

    public Location getById(int supplierId ){
        return locationDAO.getById(supplierId);
    }
}
