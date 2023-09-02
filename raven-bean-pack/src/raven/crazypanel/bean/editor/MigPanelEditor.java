package raven.crazypanel.bean.editor;

import raven.crazypanel.bean.editor.util.MigLayoutUtil;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.beans.PropertyEditor;
import java.util.StringJoiner;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;
import raven.crazypanel.MigLayoutConstraints;

/**
 *
 * @author Raven
 */
public class MigPanelEditor extends JPanel implements PropertyEditor {

    private final MigLayoutUtil migLayoutUtil = new MigLayoutUtil();
    private MigLayoutConstraints migLayoutConstraints;
    private JCheckBox chDebug;
    private JTextField txtLayout;
    private JTextField txtColumn;
    private JTextField txtRow;
    private JTable tableComponents;
    private DefaultTableModel tableModel;

    public MigPanelEditor() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap 2", "[grow 0]10[fill,400]10[grow 0]", "top"));
        JLabel labelDebug = new JLabel("Design Mode");
        JLabel labelLayout = new JLabel("Layout Constraints");
        JLabel labelColumn = new JLabel("Column Contraints");
        JLabel labelRow = new JLabel("Row Contraints");
        JLabel labelComponent = new JLabel("Component Contraints");
        chDebug = new JCheckBox("Debug");
        txtLayout = new JTextField();
        txtColumn = new JTextField();
        txtRow = new JTextField();

        tableModel = new DefaultTableModel(new Object[]{"No", "Contraints"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };
        tableComponents = new JTable(tableModel);
        tableComponents.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableComponents.getColumnModel().getColumn(0).setMaxWidth(50);

        chDebug.addActionListener((ActionEvent e) -> {
            String layoutText = txtLayout.getText();
            if (chDebug.isSelected()) {
                txtLayout.setText(migLayoutUtil.addLayout(layoutText, "debug", true));
            } else {
                txtLayout.setText(migLayoutUtil.removeLayout(layoutText, "debug"));
            }
        });

        JScrollPane tableScroll = new JScrollPane(tableComponents);
        add(labelDebug);
        add(chDebug, "grow 0");
        add(labelLayout);
        add(txtLayout);
        add(labelColumn);
        add(txtColumn);
        add(labelRow);
        add(txtRow);
        add(labelComponent);
        add(tableScroll);
        createActionButton();
    }

    private void createActionButton() {
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
            stopRowEditing();
            tableModel.addRow(new Object[]{tableComponents.getRowCount() + 1, ""});
            int row = tableComponents.getRowCount() - 1;
            tableComponents.setRowSelectionInterval(row, row);
        });
        buttonDelete.addActionListener((ActionEvent e) -> {
            stopRowEditing();
            int rows[] = tableComponents.getSelectedRows();
            for (int i = rows.length - 1; i >= 0; i--) {
                tableModel.removeRow(rows[i]);
            }
            resetRowNumber();
        });
        buttonMoveUp.addActionListener((ActionEvent e) -> {
            stopRowEditing();
            int row = tableComponents.getSelectedRow();
            tableModel.moveRow(row, row, row - 1);
            tableComponents.setRowSelectionInterval(row - 1, row - 1);
            resetRowNumber();
        });
        buttonMoveDown.addActionListener((ActionEvent e) -> {
            stopRowEditing();
            int row = tableComponents.getSelectedRow();
            tableModel.moveRow(row, row, row + 1);
            tableComponents.setRowSelectionInterval(row + 1, row + 1);
            resetRowNumber();
        });

        panel.add(buttonInsert);
        panel.add(buttonDelete);
        panel.add(buttonMoveUp);
        panel.add(buttonMoveDown);
        add(panel, "cell 2 4");
        tableComponents.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            int rows[] = tableComponents.getSelectedRows();
            boolean emptyRow = rows.length == 0;
            buttonDelete.setEnabled(!emptyRow);
            buttonMoveUp.setEnabled(!emptyRow && rows.length == 1 && rows[0] != 0);
            buttonMoveDown.setEnabled(!emptyRow && rows.length == 1 && rows[rows.length - 1] != tableComponents.getRowCount() - 1);
        });
    }

    private void stopRowEditing() {
        if (tableComponents.isEditing()) {
            tableComponents.getCellEditor().stopCellEditing();
        }
    }

    private void resetRowNumber() {
        for (int i = 0; i < tableComponents.getRowCount(); i++) {
            tableComponents.setValueAt(i + 1, i, 0);
        }
    }

    private String[] getTableValue() {
        String componentLayout[] = new String[tableComponents.getRowCount()];
        for (int i = 0; i < tableComponents.getRowCount(); i++) {
            componentLayout[i] = migLayoutUtil.removeSpace(tableComponents.getValueAt(i, 1).toString().trim());
        }
        return componentLayout;
    }

    public String arrayToString(String[] arrs) {
        if (arrs.length == 0) {
            return "null";
        }
        StringJoiner join = new StringJoiner(",\n");
        for (String arr : arrs) {
            join.add("\"" + arr + "\"");
        }
        return "new String[]{\n" + join.toString() + "\n}";
    }

    @Override
    public void setValue(Object value) {
        migLayoutConstraints = (MigLayoutConstraints) value;
        txtLayout.setText(migLayoutConstraints.getLayoutConstraints());
        txtColumn.setText(migLayoutConstraints.getColumnConstraints());
        txtRow.setText(migLayoutConstraints.getRowConstraints());

        chDebug.setSelected(migLayoutConstraints.getLayoutConstraints().toLowerCase().contains("debug"));

        String components[] = migLayoutConstraints.getComponentConstraint();
        tableModel.setRowCount(0);
        for (int i = 0; i < components.length; i++) {
            tableModel.addRow(new Object[]{i + 1, components[i]});
        }
    }

    @Override
    public Object getValue() {
        String layout = migLayoutUtil.removeSpace(txtLayout.getText().trim());
        String column = migLayoutUtil.removeSpace(txtColumn.getText().trim());
        String row = migLayoutUtil.removeSpace(txtRow.getText().trim());
        String[] components = getTableValue();
        MigLayoutConstraints value = new MigLayoutConstraints(layout, column, row, components);
        return value;
    }

    @Override
    public boolean isPaintable() {
        return false;
    }

    @Override
    public void paintValue(Graphics gfx, Rectangle box) {

    }

    @Override
    public String getJavaInitializationString() {
        MigLayoutConstraints value = (MigLayoutConstraints) getValue();
        return "new raven.crazypanel.MigLayoutConstraints(\n"
                + "\"" + value.getLayoutConstraints() + "\",\n"
                + "\"" + value.getColumnConstraints() + "\",\n"
                + "\"" + value.getRowConstraints() + "\",\n"
                + arrayToString(value.getComponentConstraint()) + "\n"
                + ")";
    }

    @Override
    public String getAsText() {
        return "";
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

    }

    @Override
    public String[] getTags() {
        return null;
    }

    @Override
    public Component getCustomEditor() {
        return this;
    }

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }
}
