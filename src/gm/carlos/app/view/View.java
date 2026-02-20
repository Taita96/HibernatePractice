package gm.carlos.app.view;

import gm.carlos.app.util.Utilities;
import gm.carlos.app.view.bag.BagView;
import gm.carlos.app.view.conecction.ConnectionView;
import gm.carlos.app.view.dashboard.DashboardView;
import gm.carlos.app.view.location.LocationView;
import gm.carlos.app.view.supplier.SupplierView;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    public JPanel panel1;

    //Panel West
    public JButton btnWestSuppliers;
    public JButton btnWestLocation;
    public JButton btnWestBags;
    public JPanel panelWest;
    public JButton btnDashboard;

    //Panel Center
    public JPanel panelCenter;
    public JPanel panelEast;

    public JMenuItem itemConectar;

    public final SupplierView supplierView = new SupplierView();
    public final LocationView locationView = new LocationView();
    public final BagView BAG_VIEW = new BagView();
    public final DashboardView DASHBOARD_VIEW = new DashboardView();
    public final ConnectionView CONNECTION_VIEW = new ConnectionView();

    public View(){
        setTitle("WareHouse");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponent();
        pack();
        setVisible(true);
    }

    private void initComponent() {
        initBtn();
        menuBar();
        initCards();
    }

    private void initCards() {
        panelCenter.removeAll();
        panelCenter.add(CONNECTION_VIEW.mainPanel,"CardConecction");
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

        Utilities.setBorderBtn(btnDashboard,btnColor);
        btnDashboard.setActionCommand("btnDashboard");
    }

    private void menuBar() {
        JMenuBar barra=new JMenuBar();
        barra.setBackground(new Color(236,237,236));

        JMenu menu = new JMenu("Menu");
        menu.setFont(new Font("Arial",Font.BOLD,14));
        menu.setForeground(Color.BLACK);

        itemConectar =new JMenuItem("Conectar");
        menu.add(itemConectar);
        menu.addSeparator();

        barra.add(menu);
        setJMenuBar(barra);
    }
}
