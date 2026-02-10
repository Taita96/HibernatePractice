package gm.carlos.app.view.bag;

import com.github.lgooddatepicker.components.DatePicker;
import gm.carlos.app.model.entity.Location;
import gm.carlos.app.model.entity.Supplier;
import gm.carlos.app.model.entity.enums.ColorEnum;
import gm.carlos.app.model.entity.enums.Material;
import gm.carlos.app.model.entity.enums.ModelEnum;
import gm.carlos.app.model.entity.enums.Status;
import gm.carlos.app.util.Utilities;


import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class BagView {

    private JPanel panel1;
    public JPanel bagCard;

    public JComboBox cbLocation;
    public JComboBox cbSupplier;
    public JComboBox cbModel;
    public JComboBox cbMaterial;
    public JComboBox cbColor;

    public JSpinner spQuantity;
    public JSpinner spWeight;
    public JSpinner spPrice;

    public JButton btnSave;
    public JButton btnUpdate;
    public JButton btnClean;

    public JTextField txtCode;

    public DatePicker dpDateEntry;
    public JTextField txtLastCode;
    public JComboBox cbStatus;
    public JButton btnHome;


    public BagView() {
        initComponent();
    }


    private void initComponent() {
        initBtn();
        initSpinner();
        initCombobox();
        initDp();
    }

    private void initDp() {
        dpDateEntry.setDate(LocalDate.now());
    }

    private void initCombobox() {
        cbLocation.setModel(new DefaultComboBoxModel<Location>());
        cbSupplier.setModel(new DefaultComboBoxModel<Supplier>());
        cbModel.setModel(new DefaultComboBoxModel<ModelEnum>(ModelEnum.values()));
        cbMaterial.setModel(new DefaultComboBoxModel<Material>(Material.values()));
        cbColor.setModel(new DefaultComboBoxModel<ColorEnum>(ColorEnum.values()));
        cbStatus.setModel(new DefaultComboBoxModel<Status>(Status.values()));
    }

    private void initSpinner(){
        spQuantity.setModel(new SpinnerNumberModel(1,0,10000,1));
        spWeight.setModel(new SpinnerNumberModel(1.0,1.0,10000.0,1.0));
        spPrice.setModel(new SpinnerNumberModel(1.0,1.0,10000.0,1.0));
    }

    private void initBtn() {

        Utilities.setBorderBtn(btnSave, Color.white);
        btnSave.setActionCommand("btnSave");

        Utilities.setBorderBtn(btnUpdate, Color.white);
        btnUpdate.setActionCommand("btnUpdate");

        Utilities.setBorderBtn(btnClean, Color.white);
        btnClean.setActionCommand("btnClean");

        Utilities.setBorderBtn(btnHome, Color.white);
        btnHome.setActionCommand("btnHome");

    }
}
