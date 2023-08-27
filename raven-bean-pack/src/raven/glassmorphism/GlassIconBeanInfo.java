package raven.glassmorphism;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import raven.glassmorphism.bean.editor.GlassIconEditor;

/**
 *
 * @author Raven
 */
public class GlassIconBeanInfo extends SimpleBeanInfo {

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor glassIconConfigProperty = new PropertyDescriptor("glassIconConfig", GlassIcon.class);
            glassIconConfigProperty.setPropertyEditorClass(GlassIconEditor.class);

            return new PropertyDescriptor[]{glassIconConfigProperty};
        } catch (IntrospectionException e) {
            return null;
        }
    }
}
