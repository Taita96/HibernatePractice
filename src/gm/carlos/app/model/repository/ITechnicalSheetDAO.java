package gm.carlos.app.model.repository;

import gm.carlos.app.model.entity.TechnicalSheet;

import java.util.List;

public interface ITechnicalSheetDAO {

    boolean save(TechnicalSheet technicalSheet);
    boolean update(TechnicalSheet technicalSheet);
    boolean delete(TechnicalSheet technicalSheet);
    List<TechnicalSheet> getAll();
    TechnicalSheet getById(int technicalSheetId);

}
