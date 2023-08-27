package raven.glassmorphism.bean.editor.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import raven.glassmorphism.GlassIconConfig;

/**
 *
 * @author Raven
 */
public class GlassIconEditorUtil {

    public List<String> listPackageSvg() throws UnsupportedEncodingException {
        List<String> list = new ArrayList<>();
        listPackages(list, toFile("/"), "");
        return list;
    }

    public List<FileSvg> listSvgFile(String packageName) throws UnsupportedEncodingException {
        packageName = packageName.replace(".", "/");
        File file = toFile(packageName);
        List<FileSvg> list = new ArrayList<>();
        for (File f : file.listFiles()) {
            if (f.isFile() && f.getName().toLowerCase().endsWith(".svg")) {
                list.add(new FileSvg(packageName, f));
            }
        }
        return list;
    }

    private void listPackages(List<String> data, File path, String packageName) {
        File[] files = path.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                String newPackageName = packageName + "." + file.getName();
                if (isContainOfSvg(file)) {
                    data.add(newPackageName.replaceFirst("\\.", ""));
                }
                listPackages(data, file.getAbsoluteFile(), newPackageName);
            }
        }
    }

    private boolean isContainOfSvg(File file) {
        for (File f : file.listFiles()) {
            if (f.getName().toLowerCase().endsWith(".svg")) {
                return true;
            }
        }
        return false;
    }

    private File toFile(String name) throws UnsupportedEncodingException {
        String decodedPathName = URLDecoder.decode(getClass().getResource(name).getFile(), "UTF-8");
        return new File(decodedPathName);
    }

    public GlassIconConfig cloneGlassIconConfig(GlassIconConfig v) {
        GlassIconConfig glassIconConfig = new GlassIconConfig(v.getName(), v.getScale(), v.getGlassIndex(), v.getBlur(), cloneColorMap(v.getColorMap()), cloneGlassShape(v.getGlassShape()));
        return glassIconConfig;
    }

    public GlassIconConfig.GlassShape cloneGlassShape(GlassIconConfig.GlassShape v) {
        GlassIconConfig.GlassShape glassShape = new GlassIconConfig.GlassShape(v.getColor(), v.getShape(), v.getRotate());
        return glassShape;
    }

    public Map<Integer, String> cloneColorMap(Map<Integer, String> v) {
        if (v == null) {
            return null;
        } else {
            Map<Integer, String> colorMap = new HashMap<>();
            for (Map.Entry<Integer, String> entry : v.entrySet()) {
                colorMap.put(entry.getKey(), entry.getValue());
            }
            return colorMap;
        }
    }

    public static String toColorText(Color color) {
        String hexColor = "#" + Integer.toHexString(color.getRGB()).substring(2);
        return hexColor;
    }

    public static String fixColor(String color) {
        if (!color.equals("")) {
            if (!color.startsWith("#")) {
                return "#" + color;
            } else {
                int in = color.lastIndexOf("#");
                if (in != 0) {
                    return color.substring(in, color.length());
                }
            }
        }
        return color;
    }

    public static String supportColor(String color) {
        color = color.toLowerCase();
        if (color.equals("@background") || color.equals("@foreground")) {
            return color;
        }
        return null;
    }

    public static Color toColor(Component com, String color) {
        if (color.equals("@background")) {
            return com.getBackground();
        } else if (color.equals("@foreground")) {
            return com.getForeground();
        } else {
            return Color.decode(color);
        }
    }

    public static void createCustomButton(Component com, Graphics2D g2, int width, int height, int space, Color color) {
        int w = com.getWidth();
        int h = com.getHeight();
        int arc = 7;
        AffineTransform oldTran = g2.getTransform();
        g2.translate(w - width - space, (h - height) / 2);
        g2.setColor(new Color(150, 150, 150));
        g2.fill(new RoundRectangle2D.Double(0, 0, width, height, arc, arc));
        g2.setPaint(new GradientPaint(0, 0, Color.WHITE, 0, height, color));
        g2.fill(new RoundRectangle2D.Double(1, 1, width - 2, height - 2, arc, arc));
        float d = width / 4f;
        float ds = 2f;
        g2.setColor(new Color(100, 100, 100));
        for (int i = 1; i <= 3; i++) {
            g2.fill(new Ellipse2D.Double(d * i - ds / 2, (height / 2 - (ds / 2)), ds, ds));
        }
        g2.setTransform(oldTran);
    }

    public static String shapeToString(Shape shape) {
        if (shape instanceof RoundRectangle2D) {
            RoundRectangle2D rec = (RoundRectangle2D) shape;
            return "new java.awt.geom.RoundRectangle2D.Double(" + rec.getX() + ", " + rec.getY() + ", " + rec.getWidth() + ", " + rec.getHeight() + ", " + rec.getArcWidth() + ", " + rec.getArcHeight() + ")";
        } else {
            return "new java.awt.geom.RoundRectangle2D.Double(2, 2, 10, 10, 0, 0)";
        }
    }
}
