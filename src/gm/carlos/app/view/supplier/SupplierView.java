package gm.carlos.app.view.supplier;

import gm.carlos.app.util.Utilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SupplierView extends JPanel {

    public JPanel SupplierCard;
    public JTable tableSuppliers;
    public JButton btnTableGoHome;
    public JButton btnTableSupplierDelete;
    public JTextField txtSupplierName;
    public JTextField txtSupplierContact;
    public JButton btnFormSupplierSave;
    public JButton btnFormSupplierUpdate;
    public JButton btnFormSupplierClean;
    public DefaultTableModel dtmTableSupplier;

    public SupplierView() {
        initComponent();
    }


    private void initComponent() {
        initBtn();
        initTables();
    }

    private void initTables() {

        dtmTableSupplier = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        tableSuppliers.setModel(dtmTableSupplier);

    }


    private void initBtn() {
        //btn Table
        Utilities.setBorderBtn(btnTableGoHome, Color.white);
        btnTableGoHome.setActionCommand("btnTableGoHome");

        Utilities.setBorderBtn(btnTableSupplierDelete, Color.white);
        btnTableSupplierDelete.setActionCommand("btnTableSupplierDelete");

        //btn Form
        Utilities.setBorderBtn(btnFormSupplierSave, Color.white);
        btnFormSupplierSave.setActionCommand("btnFormSupplierSave");

        Utilities.setBorderBtn(btnFormSupplierUpdate, Color.white);
        btnFormSupplierUpdate.setActionCommand("btnFormSupplierUpdate");

        Utilities.setBorderBtn(btnFormSupplierClean, Color.white);
        btnFormSupplierClean.setActionCommand("btnFormSupplierUpdateClean");
    }

}
