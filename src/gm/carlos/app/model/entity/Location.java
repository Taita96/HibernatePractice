package gm.carlos.app.model.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Location {
    private int idlocation;
    private String aisle;
    private String shelf;
    private List<LocationBag> location;

    public Location(String aisle, String shelf) {
        this.aisle = aisle;
        this.shelf = shelf;
    }

    public Location() {

    }

    @Id
    @Column(name = "idlocation")
    public int getIdlocation() {
        return idlocation;
    }

    public void setIdlocation(int idlocation) {
        this.idlocation = idlocation;
    }

    @Basic
    @Column(name = "aisle")
    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    @Basic
    @Column(name = "shelf")
    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return idlocation == location.idlocation &&
                Objects.equals(aisle, location.aisle) &&
                Objects.equals(shelf, location.shelf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idlocation, aisle, shelf);
    }

    @OneToMany(mappedBy = "locationBags")
    public List<LocationBag> getLocation() {
        return location;
    }

    public void setLocation(List<LocationBag> location) {
        this.location = location;
    }
}
