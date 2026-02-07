package gm.carlos.app.view;

import gm.carlos.app.util.Utilities;

import javax.swing.*;
import java.awt.*;

public class SupplierView extends JPanel {

    public JPanel SupplierCard;
    public JTable table1;
    public JButton btnTableSupplierSave;
    public JButton btnTableSupplierDelete;
    public JTextField txtSupplierName;
    public JTextField txtSupplierContact;
    public JButton btnFormSupplierSave;
    public JButton btnFormSupplierUpdate;
    public JButton btnFormSupplierUpdateClean;

    public SupplierView() {
        initComponent();
    }


    private void initComponent() {
        initBtn();
    }


    private void initBtn() {
        //Panel Center
        Utilities.setBorderBtn(btnTableSupplierSave, Color.white,null,null);
        btnTableSupplierSave.setActionCommand("btnTableSupplierSave");

        Utilities.setBorderBtn(btnTableSupplierDelete, Color.white,null,null);
        btnTableSupplierDelete.setActionCommand("btnTableSupplierDelete");

        //Card Panel Center
        Utilities.setBorderBtn(btnFormSupplierSave, Color.white,null,null);
        btnFormSupplierSave.setActionCommand("btnFormSupplierSave");

        Utilities.setBorderBtn(btnFormSupplierUpdate, Color.white,null,null);
        btnFormSupplierUpdate.setActionCommand("btnFormSupplierUpdate");

        Utilities.setBorderBtn(btnFormSupplierUpdateClean, Color.white,null,null);
        btnFormSupplierUpdateClean.setActionCommand("btnFormSupplierUpdateClean");
    }

}
