package raven.crazypanel.bean.editor.util;

import java.util.StringJoiner;

/**
 *
 * @author Raven
 */
public class MigLayoutUtil {

    public String removeLayout(String layout, String text) {
        String arrs[] = layout.toLowerCase().split(",");
        StringJoiner stJoint = new StringJoiner(",");
        for (String st : arrs) {
            if (text.equals("") || !st.trim().startsWith(text)) {
                stJoint.add(st.trim());
            }
        }
        return stJoint.toString();
    }

    public String addLayout(String layout, String text, boolean first) {
        String newLayout = removeLayout(layout, text);
        if (newLayout.equals("")) {
            return text;
        } else {
            if (first) {
                newLayout = text + "," + newLayout;
            } else {
                newLayout += "," + text;
            }
        }
        return newLayout;
    }

    public String removeSpace(String layout) {
        return removeLayout(layout, "");
    }
}
