package gm.carlos.app.model.repository;

import gm.carlos.app.model.entity.Bag;

import java.util.List;

public interface IBagDAO {

    void save(Bag bag);
    void update(Bag bag);
    void delete(Bag bag);
    List<Bag> getAll();

}
