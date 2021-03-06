package de.clayntech.config4j.impl.key;

import de.clayntech.config4j.Key;

import java.io.File;
/**
 * Key implementation to access {@link File} values.
 *
 * @author Clayn
 * @since 0.1
 */
public class FileKey extends Key<File> {
    public FileKey(String key) {
        super(key);
    }

    @Override
    protected Class<File> getType() {
        return File.class;
    }

    @Override
    public File fromString(String str) {
        return str.trim().isEmpty() ? null : new File(str);
    }

    @Override
    public String toString(File val) {
        return val == null ? "" : val.getAbsolutePath();
    }
}
