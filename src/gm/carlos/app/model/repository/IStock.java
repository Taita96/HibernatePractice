package gm.carlos.app.model.repository;

import gm.carlos.app.model.entity.Stock;

import java.util.List;

public interface IStock {

    void save(Stock stock);
    void update(Stock stock);
    void delete(Stock stock);
    List<Stock> getAll();
}
