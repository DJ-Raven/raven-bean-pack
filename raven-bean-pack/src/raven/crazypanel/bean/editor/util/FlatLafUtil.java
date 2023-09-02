package raven.crazypanel.bean.editor.util;

import java.util.StringJoiner;

/**
 *
 * @author Raven
 */
public class FlatLafUtil {

    private String fixValue(String value) {
        String v = value.contains(":") ? ":" : "=";
        String styles[] = value.split(v);
        if (styles.length == 2) {
            return styles[0].trim() + v + styles[1].trim();
        } else {
            return value.trim();
        }
    }

    public String toString(String[] styles) {
        StringJoiner stJoin = new StringJoiner(";");
        for (int i = 0; i < styles.length; i++) {
            stJoin.add(fixValue(styles[i]));
        }
        return stJoin.toString();
    }

    public String[] splitStyle(String style) {
        String styles[] = style.split(";");
        String arrStyle[] = new String[styles.length];
        for (int i = 0; i < styles.length; i++) {
            arrStyle[i] = fixValue(styles[i]);
        }
        return arrStyle;
    }

    public String fixStyle(String style) {
        return toString(splitStyle(style));
    }
}
