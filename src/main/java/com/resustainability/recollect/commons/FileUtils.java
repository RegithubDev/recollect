package com.resustainability.recollect.commons;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    private FileUtils() {}
    private static final long ONE_KB = 1024L;
    private static final long ONE_MB = ONE_KB * 1024L;
    private static final long ONE_GB = ONE_MB * 1024L;
    private static final long ONE_TB = ONE_GB * 1024L;
    private static final long ONE_PB = ONE_TB * 1024L;

    public static String byteCountToDisplaySize(long size) {
        if (size < ONE_KB) {
            return size + " B";
        } else if (size < ONE_MB) {
            return (size / ONE_KB) + " KB";
        } else if (size < ONE_GB) {
            return (size / ONE_MB) + " MB";
        } else if (size < ONE_TB) {
            return (size / ONE_GB) + " GB";
        } else if (size < ONE_PB) {
            return (size / ONE_TB) + " TB";
        } else {
            return (size / ONE_PB) + " PB";
        }
    }

    public static void ensureDirectory(Path path) {
        try {
            if (Files.exists(path)) {
                if (!Files.isDirectory(path)) {
                    throw new IllegalStateException("Path exists but is not a directory: " + path);
                }
                if (!Files.isReadable(path) || !Files.isWritable(path)) {
                    throw new IllegalStateException("Directory is not readable/writable: " + path);
                }
                return;
            }
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create directory: " + path, e);
        }
    }
}
