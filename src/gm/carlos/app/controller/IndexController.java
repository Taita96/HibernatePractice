package gm.carlos.app.controller;

import gm.carlos.app.view.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class IndexController implements ActionListener{

    private View view;

    public IndexController(View view) {
        this.view = view;
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
                showCard("SupplierCard");
            }
            case "btnWestLocation":{
                System.out.println("VIEW LOCATION");
            }
            case "btnWestBags":{
                System.out.println("VIEW BAGS");
            }
            case "btnWestinformation":{
                System.out.println("VIEW INFORMATION");
            }
        }

    }

    private void showCard(String cardName) {
        CardLayout card = (CardLayout) view.panelCenter.getLayout();
        card.show(view.panelCenter, cardName);
    }


}
