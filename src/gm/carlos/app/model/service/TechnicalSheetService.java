package gm.carlos.app.model.service;

import gm.carlos.app.model.data.TechnicalSheetDAO;
import gm.carlos.app.model.entity.TechnicalSheet;

import java.util.List;

public class TechnicalSheetService {

    private TechnicalSheetDAO technicalSheetDAO = new TechnicalSheetDAO();

    public boolean save(TechnicalSheet technicalSheet) {
        return technicalSheetDAO.save(technicalSheet);
    }

    public boolean update(TechnicalSheet technicalSheet) {
        return technicalSheetDAO.update(technicalSheet);
    }

    public boolean delete(TechnicalSheet technicalSheet) {
        return technicalSheetDAO.delete(technicalSheet);
    }

    public List<TechnicalSheet> getAll() {
        return technicalSheetDAO.getAll();
    }

    public TechnicalSheet getById(int technicalSheetId) {
        return technicalSheetDAO.getById(technicalSheetId);
    }
}
