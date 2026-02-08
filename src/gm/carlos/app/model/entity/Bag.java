package gm.carlos.app.model.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Bag {
    private int idbag;
    private String code;
    private String model;
    private String status;
    private Date entryDate;
    private TechnicalSheet technicalSheet;
    private Stock stock;
    private List<LocationBag> locationBags = new ArrayList<LocationBag>();
    private List<Supplier> suppliers = new ArrayList<Supplier>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idbag")
    public int getIdbag() {
        return idbag;
    }

    public void setIdbag(int idbag) {
        this.idbag = idbag;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "entry_date")
    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bag bag = (Bag) o;
        return idbag == bag.idbag &&
                Objects.equals(code, bag.code) &&
                Objects.equals(model, bag.model) &&
                Objects.equals(status, bag.status) &&
                Objects.equals(entryDate, bag.entryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idbag, code, model, status, entryDate);
    }

    @OneToOne(mappedBy = "bag", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "idbag", referencedColumnName = "idbag", nullable = false)
    public TechnicalSheet getTechnicalSheet() {
        return technicalSheet;
    }

    public void setTechnicalSheet(TechnicalSheet technicalSheet) {
        this.technicalSheet = technicalSheet;
        if (technicalSheet != null) {
            technicalSheet.setBag(this);
        }
    }

    @OneToOne(mappedBy = "bag", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "idbag", referencedColumnName = "idbag", nullable = false)
    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
        if (stock != null) {
            stock.setBag(this);
        }
    }

    //    @OneToMany(mappedBy = "bag")
    @OneToMany(mappedBy = "bag", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<LocationBag> getLocationBags() {
        return locationBags;
    }

    public void setLocationBags(List<LocationBag> locationBags) {
        this.locationBags = locationBags;
    }

    @ManyToMany
    @JoinTable(name = "bag_supplier", catalog = "", schema = "warehouse", joinColumns = @JoinColumn(name = "idbag", referencedColumnName = "idbag", nullable = false), inverseJoinColumns = @JoinColumn(name = "idsupplier", referencedColumnName = "idsupplier", nullable = false))
    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }


}
