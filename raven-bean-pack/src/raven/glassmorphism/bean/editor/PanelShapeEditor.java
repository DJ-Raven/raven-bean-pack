package raven.glassmorphism.bean.editor;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.util.function.Consumer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.text.DefaultFormatter;
import net.miginfocom.swing.MigLayout;
import raven.glassmorphism.GlassIconConfig;
import raven.glassmorphism.bean.editor.util.GlassIconEditorUtil;

/**
 *
 * @author Raven
 */
public class PanelShapeEditor extends JPanel {

    public void setEventChange(EventChange eventChange) {
        this.eventChange = eventChange;
    }

    public GlassIconConfig.GlassShape getGlassShape() {
        return new GlassIconConfig.GlassShape(Color.decode(textColor.getColor()), getShape(), Float.parseFloat(spinnerRotate.getValue().toString()));
    }

    public void setGlassShape(GlassIconConfig.GlassShape glassShape) {
        textColor.setColor(GlassIconEditorUtil.toColorText(glassShape.getColor()));
        spinnerRotate.setValue(glassShape.getRotate());
        setShapeValue(glassShape.getShape());

        initType(1);
    }

    private void setShapeValue(Shape shape) {
        if (shape instanceof RoundRectangle2D) {
            RoundRectangle2D rec = (RoundRectangle2D) shape;
            spinnerX.setValue(rec.getX());
            spinnerY.setValue(rec.getY());
            spinnerWidth.setValue(rec.getWidth());
            spinnerHeight.setValue(rec.getHeight());
            spinnerArcWidth.setValue(rec.getArcWidth());
            spinnerArcHeight.setValue(rec.getArcHeight());
        }
    }

    private int shapeType;
    private EventChange eventChange;
    private TextFieldColor textColor;

    private JSpinner spinnerX;
    private JSpinner spinnerY;
    private JSpinner spinnerRotate;
    private JSpinner spinnerWidth;
    private JSpinner spinnerHeight;
    private JSpinner spinnerArcWidth;
    private JSpinner spinnerArcHeight;

    public PanelShapeEditor() {
        init();
    }

    private void init() {
        setBorder(javax.swing.BorderFactory.createTitledBorder("Shape Editor"));
        textColor = new TextFieldColor();
        textColor.setOnChange((Consumer) (Object t) -> {
            runEvent();
        });
        spinnerX = createSpinerFloat(500);
        spinnerY = createSpinerFloat(500);
        spinnerWidth = createSpinerFloat(500);
        spinnerHeight = createSpinerFloat(500);
        spinnerArcWidth = createSpinerFloat(500);
        spinnerArcHeight = createSpinerFloat(500);
        spinnerRotate = createSpinerFloat(360);
        spinnerRotate.addChangeListener((ChangeEvent e) -> {
            runEvent();
        });
        initType(1);
    }

    private JSpinner createSpinerFloat(int max) {
        SpinnerNumberModel numberModel = new SpinnerNumberModel(0.0, 0.0, max, 1f);
        JSpinner spinner = new JSpinner(numberModel);
        spinner.addChangeListener((ChangeEvent e) -> {
            runEvent();
        });
        JSpinner.NumberEditor editor = (JSpinner.NumberEditor) spinner.getEditor();
        ((DefaultFormatter) editor.getTextField().getFormatter()).setCommitsOnValidEdit(true);
        return spinner;
    }

    private void runEvent() {
        if (eventChange != null) {
            eventChange.onChange(getGlassShape());
        }
    }

    public void initType(int type) {
        this.shapeType = type;
        removeAll();
        setLayout(new MigLayout("wrap 2,fillx", "[grow 0]30[fill][grow 0]"));
        add(new JLabel("Color"));
        add(textColor);
        add(new JLabel("X, Y, Rotate"));
        add(spinnerX, "split 3");
        add(spinnerY);
        add(spinnerRotate);
        add(new JLabel("Width, Height"));
        add(spinnerWidth, "split 2");
        add(spinnerHeight);
        add(new JLabel("Arc Width, Height"));
        add(spinnerArcWidth, "split 2");
        add(spinnerArcHeight);
    }

    private Shape getShape() {
        double x = Double.parseDouble(spinnerX.getValue().toString());
        double y = Double.parseDouble(spinnerY.getValue().toString());
        double width = Double.parseDouble(spinnerWidth.getValue().toString());
        double height = Double.parseDouble(spinnerHeight.getValue().toString());
        double arcWidth = Double.parseDouble(spinnerArcWidth.getValue().toString());
        double arcHeight = Double.parseDouble(spinnerArcHeight.getValue().toString());
        return new RoundRectangle2D.Double(x, y, width, height, arcWidth, arcHeight);
    }

    public interface EventChange {

        public void onChange(GlassIconConfig.GlassShape glassShape);
    }
}
