package gm.carlos.app.model.service;

import gm.carlos.app.model.data.StockDAO;
import gm.carlos.app.model.entity.Stock;

import java.util.List;

public class StockService {

    private StockDAO stockDAO = new StockDAO();

    public boolean save(Stock stock) {
        return stockDAO.save(stock);
    }

    public boolean update(Stock stock) {
        return stockDAO.update(stock);
    }

    public boolean delete(Stock stock) {
        return stockDAO.delete(stock);
    }

    public List<Stock> getAll() {
        return stockDAO.getAll();
    }

    public Stock getById(int stockId) {
        return stockDAO.getById(stockId);
    }

}
