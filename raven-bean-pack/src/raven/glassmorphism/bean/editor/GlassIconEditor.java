package raven.glassmorphism.bean.editor;

import raven.glassmorphism.bean.editor.util.FileSvg;
import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGElement;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.xml.StyleAttribute;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.beans.PropertyEditor;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatter;
import net.miginfocom.swing.MigLayout;
import raven.glassmorphism.GlassIcon;
import raven.glassmorphism.GlassIconConfig;
import raven.glassmorphism.bean.editor.util.GlassIconEditorUtil;

/**
 *
 * @author Raven
 */
public class GlassIconEditor extends JPanel implements PropertyEditor {

    private static final SVGUniverse svgUniverse = new SVGUniverse();
    private final GlassIconEditorUtil glassIconUtil = new GlassIconEditorUtil();
    private JLabel labelIcon;
    private JComboBox comboPackage;
    private JComboBox comboFile;
    private JSpinner textScale;
    private JSpinner textBlur;
    private JSpinner textGlassInxex;
    private JTable table;

    private PanelShapeEditor panelShapeEditor;
    private DefaultTableModel tableModel;
    private GlassIcon glassIcon;
    private GlassIconConfig glassIconConfig;
    private boolean onInit = true;

    public GlassIconEditor() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("wrap 2,fillx", "[grow 0]30[fill]", "[150!,fill][]"));

        //  Init component
        labelIcon = new LabelBackground();
        comboFile = createComboFile();
        comboPackage = createComboPackage();
        textScale = createSpinerFloat(30);
        textBlur = createSpinner(30);
        textGlassInxex = createSpinner(-1);
        table = createTable();
        panelShapeEditor = new PanelShapeEditor();
        panelShapeEditor.setEventChange((GlassIconConfig.GlassShape glassShape) -> {
            glassIcon.setGlassShape(glassShape);
        });
        add(new JScrollPane(labelIcon), "cell 1 0,center");
        add(new JLabel("Package"));
        add(comboPackage);
        add(new JLabel("File"));
        add(comboFile);
        add(new JLabel("Scale, Blur, Glass Index"));
        add(textScale, "split 3");
        add(textBlur);
        add(textGlassInxex);
        add(new JLabel("SVG Elements"));
        add(new JScrollPane(table), "height 100:100");
        add(new JLabel("Shape"));
        add(panelShapeEditor, "cell 1 5");
    }

    private JTable createTable() {
        tableModel = new DefaultTableModel(new Object[]{"Tag", "Color"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };
        JTable tbl = new JTable(tableModel);
        tbl.getTableHeader().setReorderingAllowed(false);
        tbl.getColumnModel().getColumn(0).setPreferredWidth(100);
        tbl.getColumnModel().getColumn(1).setMinWidth(100);
        tbl.getColumnModel().getColumn(1).setCellRenderer(new ColorCellRender());
        tbl.getColumnModel().getColumn(1).setCellEditor(new ColorCellEditor(new ColorCellEditor.EventClick() {
            @Override
            public void onClick(int row) {
                Elements element = (Elements) table.getValueAt(row, 0);
                stopTableEditing();
                Color color = showSelecteColor("Color Tag [" + element.getTag() + "]", GlassIconEditorUtil.toColor(labelIcon, element.getEditColor()));
                if (color != null) {
                    String editColor = GlassIconEditorUtil.toColorText(color);
                    element.setEditColor(editColor);
                    table.setValueAt(editColor, row, 1);
                    glassIcon.setColorMap(getColorMap());
                }
            }

            @Override
            public void onValueChange(int row) {
                glassIcon.setColorMap(getColorMap());
            }
        }));
        return tbl;
    }

    private Color showSelecteColor(String title, Color initColor) {
        Color color = JColorChooser.showDialog(this, title, initColor);
        return color;
    }

    private void stopTableEditing() {
        if (table.isEditing()) {
            table.getCellEditor().cancelCellEditing();
        }
    }

    private JSpinner createSpinner(int max) {
        JSpinner spinner = new JSpinner();
        spinner.addChangeListener((ChangeEvent e) -> {
            if (e.getSource() == textGlassInxex) {
                glassIcon.setGlassIndex(Integer.parseInt(spinner.getValue().toString()));
            } else if (e.getSource() == textBlur) {
                glassIcon.setBlur(Integer.parseInt(spinner.getValue().toString()));
            }
        });
        JSpinner.NumberEditor editor = (JSpinner.NumberEditor) spinner.getEditor();
        SpinnerNumberModel numberModel = (SpinnerNumberModel) spinner.getModel();
        numberModel.setMinimum(0);
        if (max != -1) {
            numberModel.setMaximum(max);
        }
        ((DefaultFormatter) editor.getTextField().getFormatter()).setCommitsOnValidEdit(true);
        return spinner;
    }

    private JSpinner createSpinerFloat(int max) {
        SpinnerNumberModel numberModel = new SpinnerNumberModel(0.0, 0.0, max, 0.1);
        JSpinner spinner = new JSpinner(numberModel);
        spinner.addChangeListener((ChangeEvent e) -> {
            if (e.getSource() == textScale) {
                glassIcon.setScale(Float.parseFloat(spinner.getValue().toString()));
            }
        });
        JSpinner.NumberEditor editor = (JSpinner.NumberEditor) spinner.getEditor();
        ((DefaultFormatter) editor.getTextField().getFormatter()).setCommitsOnValidEdit(true);
        return spinner;
    }

    private JComboBox createComboPackage() {
        JComboBox combo = new JComboBox();
        combo.addActionListener((ActionEvent e) -> {
            if (combo.getSelectedIndex() >= 0) {
                try {
                    List<FileSvg> files = glassIconUtil.listSvgFile("/" + combo.getSelectedItem().toString() + "/");
                    comboFile.removeAllItems();
                    comboFile.addItem("-- null --");
                    for (FileSvg f : files) {
                        comboFile.addItem(f);
                    }
                } catch (UnsupportedEncodingException ex) {
                    System.err.println(ex);
                }
            }
        });
        return combo;
    }

    private JComboBox createComboFile() {
        JComboBox combo = new JComboBox();
        combo.addItem("-- null --");
        combo.addActionListener((ActionEvent e) -> {
            if (!onInit) {
                if (glassIcon != null) {
                    if (combo.getSelectedIndex() > 0) {
                        FileSvg file = (FileSvg) combo.getSelectedItem();
                        glassIcon.setName(file.getNameWidthPacakgeName());
                        loadElements();
                    } else if (combo.getSelectedIndex() == 0) {
                        glassIcon.setName(null);
                        loadElements();
                    }
                }
            }
        });
        return combo;
    }

    private void loadPackage() {
        try {
            comboPackage.removeAllItems();
            List<String> files = glassIconUtil.listPackageSvg();
            for (String f : files) {
                comboPackage.addItem(f);
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println(e);
        }
    }

    private void loadElements() {
        tableModel.setRowCount(0);
        if (glassIconConfig.getName() != null) {
            URL url = getClass().getResource(glassIconConfig.getName());
            if (url != null) {
                SVGDiagram diagram = loadSvg(svgUniverse.loadSVG(url));
                List<SVGElement> list = diagram.getRoot().getChildren(null);
                int index = 0;
                for (SVGElement e : list) {
                    String color = getStyle(e, "fill");
                    tableModel.addRow(new Elements(e.getTagName(), color, getColorMap(index++, color)).toTableRow());
                }
            }
        }
    }

    private String getColorMap(int key, String defaultValue) {
        if (glassIconConfig.getColorMap() != null) {
            if (glassIconConfig.getColorMap().containsKey(key)) {
                return glassIconConfig.getColorMap().get(key);
            }
        }
        return defaultValue;
    }

    @Override
    public void setValue(Object value) {
        if (value == null) {
            glassIconConfig = new GlassIconConfig();
        } else {
            glassIconConfig = glassIconUtil.cloneGlassIconConfig((GlassIconConfig) value);
        }
        onInit = true;
        glassIcon = new GlassIcon(glassIconConfig);
        labelIcon.setIcon(glassIcon);
        loadPackage();
        selectedIcon(glassIconConfig.getName());
        textScale.setValue(glassIconConfig.getScale());
        textBlur.setValue(glassIconConfig.getBlur());
        textGlassInxex.setValue(glassIconConfig.getGlassIndex());
        panelShapeEditor.setGlassShape(glassIconConfig.getGlassShape());
        loadElements();
        onInit = false;
    }

    private void selectedIcon(String iconName) {
        if (iconName != null) {
            if (iconName.contains("/")) {
                if (iconName.startsWith("/")) {
                    iconName = iconName.substring(1, iconName.length());
                }
                int index = iconName.lastIndexOf("/");
                String packageName = iconName.substring(0, index).replace("/", ".");
                String name = iconName.substring(index + 1, iconName.length());
                comboPackage.setSelectedItem(packageName);
                for (int i = 0; i < comboFile.getItemCount(); i++) {
                    if (comboFile.getItemAt(i).toString().equals(name)) {
                        comboFile.setSelectedIndex(i);
                        return;
                    }
                }
            }
        }
    }

    private String getStyle(SVGElement e, String style) {
        try {
            StyleAttribute att = new StyleAttribute(style);
            if (e.getStyle(att)) {
                return att.getStringValue();
            }
        } catch (SVGException ex) {
            System.err.println(ex);
        }
        return "";
    }

    private synchronized SVGDiagram loadSvg(URI uri) {
        SVGDiagram dg = svgUniverse.getDiagram(uri);
        return dg;
    }

    private Map<Integer, String> getColorMap() {
        Map<Integer, String> mapColor = new HashMap<>();
        for (int i = 0; i < table.getRowCount(); i++) {
            Elements elements = (Elements) table.getValueAt(i, 0);
            if (!elements.getColor().equals(elements.getEditColor())) {
                mapColor.put(i, elements.getEditColor());
            }
        }
        return mapColor;
    }

    private String colorMapToString(Map<Integer, String> colorMap) {
        if (colorMap == null || colorMap.isEmpty()) {
            return "null";
        } else {
            StringJoiner stJoin = new StringJoiner("\n");
            for (Map.Entry<Integer, String> entry : colorMap.entrySet()) {
                String v = "put(" + entry.getKey() + ",\"" + entry.getValue() + "\");";
                stJoin.add(v);
            }

            return "new java.util.HashMap<Integer, String>(){\n"
                    + "{\n"
                    + stJoin.toString() + "\n"
                    + "}\n"
                    + "}";
        }
    }

    @Override
    public Object getValue() {
        if (glassIconConfig == null) {
            return null;
        } else {
            return new GlassIconConfig(glassIconConfig.getName(), glassIconConfig.getScale(), glassIconConfig.getGlassIndex(), glassIconConfig.getBlur(), glassIconConfig.getColorMap(), glassIconConfig.getGlassShape());
        }
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
        GlassIconConfig v = (GlassIconConfig) getValue();
        return "new raven.glassmorphism.GlassIconConfig(\n"
                + "\"" + v.getName() + "\", " + v.getScale() + "f, " + v.getGlassIndex() + ", " + v.getBlur() + ",\n"
                + colorMapToString(v.getColorMap()) + ",\n"
                + "new raven.glassmorphism.GlassIconConfig.GlassShape(\n"
                + "java.awt.Color.decode(\"" + GlassIconEditorUtil.toColorText(v.getGlassShape().getColor()) + "\"),\n"
                + "" + GlassIconEditorUtil.shapeToString(v.getGlassShape().getShape()) + ",\n"
                + "" + v.getGlassShape().getRotate() + "f)\n"
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
