package gm.carlos.app.view;

import gm.carlos.app.util.Utilities;

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
        panelCenter.add(supplierView.SupplierCard);

        panelCenter.revalidate();
        panelCenter.repaint();
    }

    private void initBtn() {
        Utilities.setBorderBtn(btnWestSuppliers, Color.white,Color.BLACK,null);
        btnWestSuppliers.setActionCommand("btnWestSuppliers");

        Utilities.setBorderBtn(btnWestLocation, Color.white,Color.BLACK,null);
        btnWestLocation.setActionCommand("btnWestLocation");

        Utilities.setBorderBtn(btnWestBags, Color.white,Color.BLACK,null);
        btnWestBags.setActionCommand("btnWestBags");

        Utilities.setBorderBtn(btnWestinformation, Color.white,Color.BLACK,null);
        btnWestinformation.setActionCommand("btnWestinformation");
    }
}
