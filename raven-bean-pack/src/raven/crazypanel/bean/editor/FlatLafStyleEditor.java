package raven.crazypanel.bean.editor;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.beans.PropertyEditor;
import java.util.StringJoiner;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;
import raven.bean.util.ComponentUtil;
import raven.crazypanel.FlatLafStyleComponent;
import raven.crazypanel.bean.editor.util.FlatLafUtil;

/**
 *
 * @author Raven
 */
public class FlatLafStyleEditor extends JPanel implements PropertyEditor {

    private final FlatLafUtil flatLafUtil = new FlatLafUtil();
    private FlatLafStyleComponent flatLafStyleComponent;
    private JTextField txtStyle;
    private JTable tableComponents;
    private DefaultTableModel tableModel;

    public FlatLafStyleEditor() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx,wrap 2", "[grow 0]10[fill,400]10[grow 0]", "top"));
        JLabel labelStyle = new JLabel("Style");

        JLabel labelComponent = new JLabel("Component Style");

        txtStyle = new JTextField();
        JButton button = new JButton("Edit");
        button.setMnemonic('t');
        button.addActionListener((ActionEvent e) -> {
            String newStyle = editing(txtStyle.getText().trim());
            if (newStyle != null) {
                txtStyle.setText(newStyle);
            }
        });
        tableModel = new DefaultTableModel(new Object[]{"No", "Contraints"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };
        tableComponents = new JTable(tableModel);
        tableComponents.getTableHeader().setReorderingAllowed(false);
        tableComponents.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableComponents.getColumnModel().getColumn(0).setMaxWidth(50);

        JScrollPane tableScroll = new JScrollPane(tableComponents);
        add(labelStyle);
        add(txtStyle);
        add(button, "cell 2 0,grow 1");
        add(labelComponent);
        add(tableScroll);
        add(ComponentUtil.createActionButton(tableComponents, (row) -> {
            String style = tableComponents.getValueAt(row, 1).toString();
            String newStyle = editing(style);
            if (newStyle != null) {
                tableComponents.setValueAt(newStyle, row, 1);
            }
        }), "cell 2 1");
    }

    private String editing(String value) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"No", "Style"}, 0);
        JTable table = new JTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        String styles[] = flatLafUtil.splitStyle(value);
        for (String style : styles) {
            model.addRow(new Object[]{table.getRowCount() + 1, style});
        }
        JPanel panel = new JPanel(new MigLayout("wrap,fillx", "[fill][]", "[top]"));
        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel.add(new JScrollPane(table));
        panel.add(ComponentUtil.createActionButton(table, null));
        int opt = JOptionPane.showConfirmDialog(this, panel, "Edit Style", JOptionPane.OK_CANCEL_OPTION, -1);
        if (opt == JOptionPane.OK_OPTION) {
            ComponentUtil.stopRowEditing(table);
            int count = ComponentUtil.getRowCount(table, 1);
            String newStyles[] = new String[count];
            for (int i = 0; i < table.getRowCount(); i++) {
                String v = table.getValueAt(i, 1).toString().trim();
                if (!v.equals("")) {
                    newStyles[i] = v;
                }
            }
            return flatLafUtil.toString(newStyles);
        } else {
            return null;
        }
    }

    private String[] getTableValue() {
        String componentStyle[] = new String[tableComponents.getRowCount()];
        for (int i = 0; i < tableComponents.getRowCount(); i++) {
            componentStyle[i] = flatLafUtil.fixStyle(tableComponents.getValueAt(i, 1).toString());
        }
        return componentStyle;
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
        if (value != null) {
            flatLafStyleComponent = (FlatLafStyleComponent) value;
            txtStyle.setText(flatLafStyleComponent.getOwnStyle());
            String components[] = flatLafStyleComponent.getStyles();
            tableModel.setRowCount(0);
            for (int i = 0; i < components.length; i++) {
                tableModel.addRow(new Object[]{i + 1, components[i]});
            }
        }
    }

    @Override
    public Object getValue() {
        String ownStyle = txtStyle.getText().trim();
        FlatLafStyleComponent value = new FlatLafStyleComponent(ownStyle, getTableValue());
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
        FlatLafStyleComponent value = (FlatLafStyleComponent) getValue();
        return "new raven.crazypanel.FlatLafStyleComponent(\n"
                + "\"" + value.getOwnStyle() + "\",\n"
                + arrayToString(value.getStyles()) + "\n"
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
