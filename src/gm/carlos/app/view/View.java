package gm.carlos.app.view;

import gm.carlos.app.util.Utilities;
import gm.carlos.app.view.location.LocationView;
import gm.carlos.app.view.supplier.SupplierView;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame{

    public JPanel panel1;

    //Panel West
    public JButton btnWestSuppliers;
    public JButton btnWestLocation;
    public JButton btnWestBags;
    public JPanel panelWest;
    public JButton btnWestinformation;

    //Panel Center
    public JPanel panelCenter;
    public JPanel panelEast;

    public final SupplierView supplierView = new SupplierView();
    public final LocationView locationView = new LocationView();

    public View(){
        setTitle("WareHouse");
        setContentPane(panel1);
        setLocationRelativeTo(null);
        initComponent();
        pack();
        setVisible(true);
    }

    private void initComponent() {
        initBtn();
        initCards();
    }

    private void initCards() {

        panelCenter.removeAll();
        panelCenter.add(supplierView.SupplierCard,"supplierCard");
        panelCenter.add(locationView.locationCard,"locationCard");
        panelCenter.revalidate();
        panelCenter.repaint();
    }

    private void initBtn() {
        Color btnColor = new Color(77, 77, 77);
        Utilities.setBorderBtn(btnWestSuppliers,btnColor);
        btnWestSuppliers.setActionCommand("btnWestSuppliers");

        Utilities.setBorderBtn(btnWestLocation,btnColor);
        btnWestLocation.setActionCommand("btnWestLocation");

        Utilities.setBorderBtn(btnWestBags, btnColor);
        btnWestBags.setActionCommand("btnWestBags");

        Utilities.setBorderBtn(btnWestinformation,btnColor);
        btnWestinformation.setActionCommand("btnWestinformation");
    }
}
