package gm.carlos.app.model.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Supplier {
    private int idsupplier;
    private String name;
    private String contact;
    private List<Bag> bags;

    public Supplier(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public Supplier() {
    }

    @Id
    @Column(name = "idsupplier")
    public int getIdsupplier() {
        return idsupplier;
    }

    public void setIdsupplier(int idsupplier) {
        this.idsupplier = idsupplier;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "contact")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return idsupplier == supplier.idsupplier &&
                Objects.equals(name, supplier.name) &&
                Objects.equals(contact, supplier.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idsupplier, name, contact);
    }

    @ManyToMany(mappedBy = "suppliers")
    public List<Bag> getBags() {
        return bags;
    }

    public void setBags(List<Bag> bags) {
        this.bags = bags;
    }
}
