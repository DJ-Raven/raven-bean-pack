package raven.bean.util;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Raven
 */
public class ComponentUtil {

    public static Component createActionButton(JTable table, Consumer<Integer> onEdit) {
        DefaultTableModel tblModel = (DefaultTableModel) table.getModel();
        JPanel panel = new JPanel(new MigLayout("fillx,wrap,insets 0", "[fill]"));
        JButton buttonInsert = new JButton("Insert");
        JButton buttonDelete = new JButton("Delete");
        JButton buttonMoveUp = new JButton("MoveUp");
        JButton buttonMoveDown = new JButton("MoveDown");
        buttonDelete.setEnabled(false);
        buttonMoveUp.setEnabled(false);
        buttonMoveDown.setEnabled(false);
        buttonInsert.setMnemonic('I');
        buttonDelete.setMnemonic('D');
        buttonMoveUp.setMnemonic('U');
        buttonMoveDown.setMnemonic('w');
        buttonInsert.addActionListener((ActionEvent e) -> {
            stopRowEditing(table);
            tblModel.addRow(new Object[]{table.getRowCount() + 1, ""});
            int row = table.getRowCount() - 1;
            table.setRowSelectionInterval(row, row);
        });
        buttonDelete.addActionListener((ActionEvent e) -> {
            stopRowEditing(table);
            int rows[] = table.getSelectedRows();
            for (int i = rows.length - 1; i >= 0; i--) {
                tblModel.removeRow(rows[i]);
            }
            resetRowNumber(table);
        });
        buttonMoveUp.addActionListener((ActionEvent e) -> {
            stopRowEditing(table);
            int row = table.getSelectedRow();
            tblModel.moveRow(row, row, row - 1);
            table.setRowSelectionInterval(row - 1, row - 1);
            resetRowNumber(table);
        });
        buttonMoveDown.addActionListener((ActionEvent e) -> {
            stopRowEditing(table);
            int row = table.getSelectedRow();
            tblModel.moveRow(row, row, row + 1);
            table.setRowSelectionInterval(row + 1, row + 1);
            resetRowNumber(table);
        });

        panel.add(buttonInsert);
        panel.add(buttonDelete);
        panel.add(buttonMoveUp);
        panel.add(buttonMoveDown);
        final JButton buttonEdit = createEdit(table, onEdit);
        if (onEdit != null) {
            panel.add(buttonEdit);
        }
        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            int rows[] = table.getSelectedRows();
            boolean emptyRow = rows.length == 0;
            buttonDelete.setEnabled(!emptyRow);
            buttonMoveUp.setEnabled(!emptyRow && rows.length == 1 && rows[0] != 0);
            buttonMoveDown.setEnabled(!emptyRow && rows.length == 1 && rows[rows.length - 1] != table.getRowCount() - 1);
            if (buttonEdit != null) {
                buttonEdit.setEnabled(!emptyRow);
            }
        });
        return panel;
    }

    private static JButton createEdit(JTable table, Consumer<Integer> onEdit) {
        if (onEdit == null) {
            return null;
        }
        JButton buttonEdit = new JButton("Edit");
        buttonEdit.setEnabled(false);
        buttonEdit.setMnemonic('E');
        buttonEdit.addActionListener(((e) -> {
            stopRowEditing(table);
            int row = table.getSelectedRow();
            onEdit.accept(row);
        }));
        return buttonEdit;
    }

    public static void stopRowEditing(JTable table) {
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }
    }

    public static void resetRowNumber(JTable table) {
        for (int i = 0; i < table.getRowCount(); i++) {
            table.setValueAt(i + 1, i, 0);
        }
    }

    public static int getRowCount(JTable table, int col) {
        int count = 0;
        for (int i = 0; i < table.getRowCount(); i++) {
            if (!table.getValueAt(i, col).toString().trim().equals("")) {
                count++;
            }
        }
        return count;
    }

    public static void addTrailingComponent(JTextField text, JButton... buttons) {
        JToolBar toolBar = new JToolBar();
        for (JButton button : buttons) {
            toolBar.add(button);
        }
        text.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, toolBar);
    }
}
