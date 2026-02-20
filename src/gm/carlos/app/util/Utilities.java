package gm.carlos.app.util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * Clase de utilidades para componentes gráficos Swing.
 *
 * <p>Contiene métodos estáticos reutilizables para:
 * <ul>
 *     <li>Estilizar botones.</li>
 *     <li>Configurar alineación de tablas.</li>
 *     <li>Gestionar visibilidad y activación de componentes.</li>
 *     <li>Validaciones básicas.</li>
 *     <li>Mostrar mensajes estándar al usuario.</li>
 * </ul>
 *
 * <p>Es una clase utilitaria, por lo que no debe instanciarse.</p>
 */
public class Utilities {

    /**
     * Aplica un estilo personalizado a un botón, añadiendo borde redondeado,
     * efecto hover y cursor tipo "mano".
     *
     * @param btn   botón al que se aplicará el estilo.
     * @param color color del borde del botón.
     */
    public static void setBorderBtn(JButton btn,Color color) {

        Color normalBg = btn.getBackground();
        Color hoverBg  = new Color(230, 230, 230);

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

    /**
     * Centra el contenido de todas las columnas de una tabla.
     *
     * @param table tabla a la que se le aplicará la alineación centrada.
     */
    public static void centerTable(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centerRenderer);
        }
    }
    /**
     * Valida un correo electrónico mediante expresión regular.
     *
     * @param email correo a validar.
     * @return {@code true} si el email NO es válido, {@code false} si es válido.
     */
    public static boolean validateEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z]+\\.[A-Za-z]{2,}$";
        return !email.trim().matches(regex);
    }

    /**
     * Muestra un cuadro de diálogo de error.
     *
     * @param mensaje mensaje a mostrar.
     */
    public static void showErrorAlert(String mensaje) {
        JOptionPane.showMessageDialog(null,mensaje,"Error",JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un cuadro de diálogo de advertencia.
     *
     * @param mensaje mensaje a mostrar.
     */
    public static void showWarningAlert(String mensaje) {
        JOptionPane.showMessageDialog(null,mensaje,"Warning",JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Muestra un cuadro de diálogo informativo.
     *
     * @param mensaje mensaje a mostrar.
     */
    public static void showInfoAlert(String mensaje) {
        JOptionPane.showMessageDialog(null,mensaje,"Information",JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un cuadro de confirmación con opciones Sí/No.
     *
     * @param message mensaje de confirmación.
     * @param title   título de la ventana.
     * @return opción seleccionada por el usuario (JOptionPane.YES_OPTION o JOptionPane.NO_OPTION).
     */
    public static int confirmMessage(String message,String title) {
        return JOptionPane.showConfirmDialog(null,message
                ,title,JOptionPane.YES_NO_OPTION);
    }

}
