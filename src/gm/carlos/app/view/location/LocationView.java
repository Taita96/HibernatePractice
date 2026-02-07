package gm.carlos.app.view.location;


import gm.carlos.app.util.Utilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LocationView extends JPanel {

    public JPanel locationCard;
    public JTable tableLocation;

    public JButton btnTableGoHome;
    public JButton btnTableDelete;
    public JButton btnFormSave;
    public JButton btnFormUpdate;
    public JButton btnFormClean;

    public JTextField txtAisle;
    public JTextField txtShelf;

    public DefaultTableModel dtmTableLocation;

    public LocationView() {
        initComponent();
    }


    private void initComponent() {
        initBtn();
        initTables();
    }

    private void initTables() {

        dtmTableLocation = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        tableLocation.setModel(dtmTableLocation);

    }


    private void initBtn() {
        //btn Table
        Utilities.setBorderBtn(btnTableGoHome, Color.white);
        btnTableGoHome.setActionCommand("btnTableGoHome");

        Utilities.setBorderBtn(btnTableDelete, Color.white);
        btnTableDelete.setActionCommand("btnTableDelete");

        //btn Form
        Utilities.setBorderBtn(btnFormSave, Color.white);
        btnFormSave.setActionCommand("btnFormSave");

        Utilities.setBorderBtn(btnFormUpdate, Color.white);
        btnFormUpdate.setActionCommand("btnFormUpdate");

        Utilities.setBorderBtn(btnFormClean, Color.white);
        btnFormClean.setActionCommand("btnFormClean");
    }
}
