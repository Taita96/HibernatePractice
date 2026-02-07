package gm.carlos.app.model.repository;

import gm.carlos.app.model.entity.TechnicalSheet;

import java.util.List;

public interface ITechnicalSheet {

    void save(TechnicalSheet technicalSheet);
    void update(TechnicalSheet technicalSheet);
    void delete(TechnicalSheet technicalSheet);
    List<TechnicalSheet> getAll();

}
