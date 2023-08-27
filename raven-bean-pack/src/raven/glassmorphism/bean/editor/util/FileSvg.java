package raven.glassmorphism.bean.editor.util;

import java.io.File;

/**
 *
 * @author Raven
 */
public class FileSvg {

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileSvg() {
    }

    public FileSvg(String packageName, File file) {
        this.packageName = packageName;
        this.file = file;
    }

    private String packageName;
    private File file;

    @Override
    public String toString() {
        return file.getName();
    }

    public String getNameWidthPacakgeName() {
        return packageName + toString();
    }
}
