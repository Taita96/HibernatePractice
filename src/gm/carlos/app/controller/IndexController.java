package gm.carlos.app.controller;

import gm.carlos.app.controller.bag.BagController;
import gm.carlos.app.controller.dasboard.DashboardController;
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
    private final BagController bagController;
    private final DashboardController dashboardController;

    public IndexController(View view,Model model) {
        this.view = view;
        this.model = model;
        this.supplierController = new SupplierController(view.supplierView,model);
        this.locationController = new LocationController(view.locationView,model);
        this.bagController = new BagController(view.BAG_VIEW,model);
        this.dashboardController = new DashboardController(view.DASHBOARD_VIEW,model);
        addActionListener(this);
    }

    private void addActionListener(ActionListener actionListener) {
        view.btnDashboard.addActionListener(actionListener);
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
                showCard("bagCard");
                bagController.initComponent();
                break;
            }
            case "btnDashboard":{
                showCard("dashboardView");
                break;
            }
        }
    }

    private void showCard(String cardName) {
        CardLayout card = (CardLayout) view.panelCenter.getLayout();
        card.show(view.panelCenter, cardName);
    }

}
