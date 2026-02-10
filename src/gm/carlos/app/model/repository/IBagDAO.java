package gm.carlos.app.model.repository;

import gm.carlos.app.model.entity.Bag;

import java.util.List;

public interface IBagDAO {

    Bag save(Bag bag);
    boolean update(Bag bag);
    boolean delete(Bag bag);
    List<Bag> getAll();
    Bag getById(int bagId);
    List<Bag> getAllWithDetails();
    boolean softDeleteById(int bagId);
    boolean getByCode(String code);
    String getLastCode();

}
