package gm.carlos.app.model.service;

import gm.carlos.app.model.data.BagDAO;
import gm.carlos.app.model.entity.Bag;

import java.util.List;

public class BagService {

    private BagDAO bagDAO = new BagDAO();

    public Bag save(Bag bag) {
        return bagDAO.save(bag);
    }

    public boolean update(Bag bag) {
        return bagDAO.update(bag);
    }

    public boolean delete(Bag bag) {
        return bagDAO.delete(bag);
    }

    public List<Bag> getAll() {
        return bagDAO.getAll();
    }

    public Bag getById(int bagId) {
        return bagDAO.getById(bagId);
    }

    public List<Bag> getAllWithDetails(){
        return bagDAO.getAllWithDetails();
    }

    public boolean softDeleteById(int bagId){
        return bagDAO.softDeleteById(bagId);
    }

    public boolean getByCode(String code){
        return bagDAO.getByCode(code);
    }

    public String getLastCode(){
        return bagDAO.getLastCode();
    }
}
