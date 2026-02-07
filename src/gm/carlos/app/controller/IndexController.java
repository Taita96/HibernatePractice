package gm.carlos.app.controller;

import gm.carlos.app.controller.location.LocationController;
import gm.carlos.app.controller.supplier.SupplierController;
import gm.carlos.app.model.Model;
import gm.carlos.app.view.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class IndexController implements ActionListener{

    private View view;
    private Model model;

    private final SupplierController supplierController;
    private final LocationController locationController;

    public IndexController(View view,Model model) {
        this.view = view;
        this.model = model;
        this.supplierController = new SupplierController(view.supplierView,model);
        this.locationController = new LocationController(view.locationView,model);
        addActionListener(this);
    }

    private void addActionListener(ActionListener actionListener) {
        view.btnWestinformation.addActionListener(actionListener);
        view.btnWestBags.addActionListener(actionListener);
        view.btnWestLocation.addActionListener(actionListener);
        view.btnWestSuppliers.addActionListener(actionListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String evt = e.getActionCommand();

        switch (evt){
            case "btnWestSuppliers":{
                showCard("supplierCard");
                break;
            }
            case "btnWestLocation":{
                showCard("locationCard");
                break;
            }
            case "btnWestBags":{
                System.out.println("VIEW BAGS");
                break;
            }
            case "btnWestinformation":{
                System.out.println("VIEW INFORMATION");
                break;
            }
        }

    }

    private void showCard(String cardName) {
        CardLayout card = (CardLayout) view.panelCenter.getLayout();
        card.show(view.panelCenter, cardName);
    }


    private SupplierController getSupplierController(){
        return this.supplierController;
    }
}
