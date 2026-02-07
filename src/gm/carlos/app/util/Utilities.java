package gm.carlos.app.util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Utilities {

    public static void setBorderBtn(JButton btn,Color color,Color hover,Color txtBtnColor) {

        Color normalBg = btn.getBackground();
        Color hoverBg  = hover != null ? hover : new Color(230, 230, 230);
        Color btnTextColor = txtBtnColor != null ? txtBtnColor : Color.white;

        LineBorder lineBorder = new LineBorder(color, 2, true);
        Border emptyBorder = BorderFactory.createEmptyBorder(5, 40, 5, 40);
        CompoundBorder compoundBorder = BorderFactory.createCompoundBorder(lineBorder, emptyBorder);

        btn.setBorder(compoundBorder);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(hoverBg);

                if(txtBtnColor == null){
                    btn.setForeground(btnTextColor);
                    return;
                }

                if(btn.getForeground().equals(Color.white)){
                    btn.setForeground(normalBg);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(normalBg);

                if(btn.getForeground().equals(normalBg)){
                    btn.setForeground(Color.white);
                }
            }
        });
    }

    public static void setBorderTxtField(JTextField txt,Color color){
        LineBorder lineBorder = new LineBorder(color, 1, true);
        Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 40);
        CompoundBorder compoundBorder = BorderFactory.createCompoundBorder(lineBorder, emptyBorder);
        txt.setBorder(compoundBorder);
    }

    public static void manageBtn(JButton btn,boolean behavior){
        btn.setEnabled(behavior);
        btn.setVisible(behavior);
    }

    public static void showErrorAlert(String mensaje) {
        JOptionPane.showMessageDialog(null,mensaje,"Error",JOptionPane.ERROR_MESSAGE);
    }

    public static void showWarningAlert(String mensaje) {
        JOptionPane.showMessageDialog(null,mensaje,"Warning",JOptionPane.WARNING_MESSAGE);
    }

    public static void showInfoAlert(String mensaje) {
        JOptionPane.showMessageDialog(null,mensaje,"Information",JOptionPane.INFORMATION_MESSAGE);
    }

    public static void displayCard(CardLayout card,JPanel panel,String nameCard) {
        card.show(panel,nameCard);
    }

    public static int confirmMessage(String message,String title) {
        return JOptionPane.showConfirmDialog(null,message
                ,title,JOptionPane.YES_NO_OPTION);
    }

}
