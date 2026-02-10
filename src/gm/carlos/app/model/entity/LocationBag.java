package gm.carlos.app.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "location_bag", schema = "warehouse", catalog = "")
public class LocationBag {
    private int id;
    private Bag bag;
    private Location locationBags;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationBag that = (LocationBag) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "idbag", referencedColumnName = "idbag",unique = false,nullable = false)
    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    @ManyToOne
    @JoinColumn(name = "idlocation", referencedColumnName = "idlocation",unique = false,nullable = false)
    public Location getLocationBags() {
        return locationBags;
    }

    public void setLocationBags(Location locationBags) {
        this.locationBags = locationBags;
    }
}
