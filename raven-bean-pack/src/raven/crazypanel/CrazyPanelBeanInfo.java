package raven.crazypanel;

import raven.crazypanel.bean.editor.MigPanelEditor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import raven.crazypanel.bean.editor.FlatLafStyleEditor;

/**
 *
 * @author Raven
 */
public class CrazyPanelBeanInfo extends SimpleBeanInfo {

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor migPanelProperty = new PropertyDescriptor("migLayoutConstraints", CrazyPanel.class);
            migPanelProperty.setPropertyEditorClass(MigPanelEditor.class);

            PropertyDescriptor flatlafStypeProperty = new PropertyDescriptor("flatLafStyleComponent", CrazyPanel.class);
            flatlafStypeProperty.setPropertyEditorClass(FlatLafStyleEditor.class);

            PropertyDescriptor background = new PropertyDescriptor("background", CrazyPanel.class);
            PropertyDescriptor foreground = new PropertyDescriptor("foreground", CrazyPanel.class);
            PropertyDescriptor border = new PropertyDescriptor("border", CrazyPanel.class);
            PropertyDescriptor name = new PropertyDescriptor("name", CrazyPanel.class);
            PropertyDescriptor opaque = new PropertyDescriptor("opaque", CrazyPanel.class);

            PropertyDescriptor preferredSize = new PropertyDescriptor("preferredSize", CrazyPanel.class);
            return new PropertyDescriptor[]{migPanelProperty, flatlafStypeProperty, background, foreground, border, name, opaque, preferredSize};
        } catch (IntrospectionException e) {
            return null;
        }
    }
}
