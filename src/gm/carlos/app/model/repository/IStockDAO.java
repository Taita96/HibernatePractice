package gm.carlos.app.model.repository;

import gm.carlos.app.model.entity.LocationBag;
import gm.carlos.app.model.entity.Stock;

import java.util.List;

public interface IStockDAO {

    boolean save(Stock stock);
    boolean update(Stock stock);
    boolean delete(Stock stock);
    List<Stock> getAll();
    Stock getById(int stockId);
}
