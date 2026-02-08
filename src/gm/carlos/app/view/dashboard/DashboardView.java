package gm.carlos.app.view.dashboard;

import gm.carlos.app.util.Utilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DashboardView {

    public JPanel panel1;
    public JPanel dashboardView;

    public JTable table;
    public JButton btnUpdate;
    public JButton btnDelete;
    public JButton btnRegister;


    public DefaultTableModel dtmTableDasboard;

    public DashboardView() {
        initComponent();
    }


    private void initComponent() {
        initBtn();
        initTables();
    }

    private void initTables() {

        dtmTableDasboard = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        table.setModel(dtmTableDasboard);

    }


    private void initBtn() {
        Utilities.setBorderBtn(btnUpdate, Color.white);
        btnUpdate.setActionCommand("btnUpdate");

        Utilities.setBorderBtn(btnDelete, Color.white);
        btnDelete.setActionCommand("btnDelete");

        Utilities.setBorderBtn(btnRegister, Color.white);
        btnRegister.setActionCommand("btnRegister");
    }
}
