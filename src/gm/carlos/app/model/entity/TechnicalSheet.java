package gm.carlos.app.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "technical_sheet", schema = "warehouse", catalog = "")
public class TechnicalSheet {
    private int idtechnical;
    private String material;
    private BigDecimal weight;
    private String color;
    private Bag bag;

    @Id
    @Column(name = "idtechnical")
    public int getIdtechnical() {
        return idtechnical;
    }

    public void setIdtechnical(int idtechnical) {
        this.idtechnical = idtechnical;
    }

    @Basic
    @Column(name = "material")
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Basic
    @Column(name = "weight")
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TechnicalSheet that = (TechnicalSheet) o;
        return idtechnical == that.idtechnical &&
                Objects.equals(material, that.material) &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idtechnical, material, weight, color);
    }

    @OneToOne(mappedBy = "technicalSheet")
    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }
}

