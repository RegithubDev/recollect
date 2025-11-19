package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.FileUtils;
import com.resustainability.recollect.commons.IdGenerator;
import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.exception.InvalidDataException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class UploadService {
    private final String fileUploadPath;

    @Autowired
    public UploadService(
            @Value("${app.file.uploadPath}") String fileUploadPath
    ) {
        this.fileUploadPath = fileUploadPath;
    }

    public String upload(String suffixFolderName, MultipartFile file) {
        validate(file);
        final Path dir = Paths.get(fileUploadPath, suffixFolderName);
        FileUtils.ensureDirectory(dir);
        final String name = IdGenerator.nextId() + extension(file.getOriginalFilename());
        final Path path = dir.resolve(name);
        write(file, path);
        return suffixFolderName + "/" + name;
    }

    public void remove(String storedName) {
        if (StringUtils.isBlank(storedName)) return;
        final Path path = Paths.get(fileUploadPath, storedName);
        delete(path);
    }

    private void validate(MultipartFile file) {
        ValidationUtils.validateMultipart(file);
        final String type = file.getContentType();
        if (StringUtils.isBlank(type)) throw new InvalidDataException("Invalid content");
        if (!type.equals("image/png") &&
            !type.equals("image/jpeg") &&
            !type.equals("image/jpg") &&
            !type.equals("image/webp")) {
            throw new InvalidDataException("Unsupported type");
        }
    }

    private String extension(String filename) {
        if (StringUtils.isBlank(filename)) return ".jpg";
        int i = filename.lastIndexOf('.');
        if (i == -1) return ".jpg";
        String ext = filename.substring(i).toLowerCase();
        ext = ext.replaceAll("[^a-z0-9.]", "");
        if (!ext.startsWith(".")) ext = "." + ext;
        return switch (ext) {
            case ".png", ".jpg", ".jpeg", ".webp" -> ext;
            default -> ".jpg";
        };
    }

    private void write(MultipartFile file, Path path) {
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new InvalidDataException(e, "Write failed");
        }
    }

    private void delete(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {}
    }
}
