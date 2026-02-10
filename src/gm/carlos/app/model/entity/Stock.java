package gm.carlos.app.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Stock {
    private int idstock;
    private int quantity;
    private double price;
    private Bag bag;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idstock")
    public int getIdstock() {
        return idstock;
    }

    public void setIdstock(int idstock) {
        this.idstock = idstock;
    }

    @Basic
    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return idstock == stock.idstock &&
                quantity == stock.quantity &&
                price == stock.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idstock, quantity, price);
    }

    //    @OneToOne(mappedBy = "stock")
    @OneToOne
    @JoinColumn(name = "idbag")
    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }
}
