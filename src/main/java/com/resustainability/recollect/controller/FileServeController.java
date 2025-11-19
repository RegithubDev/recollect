package com.resustainability.recollect.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

@RestController
@RequestMapping(FileServeController.ROOT_PATH)
@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
public class FileServeController {
    public static final String ROOT_PATH = "/recollect/v1/files";
    private static final Pattern SAFE_NAME = Pattern.compile("^[a-zA-Z0-9._/-]+$");

    private final String fileUploadPath;

    @Autowired
    public FileServeController(
            @Value("${app.file.uploadPath}") String fileUploadPath
    ) {
        this.fileUploadPath = fileUploadPath;
    }

    @GetMapping(value = "/**")
    public ResponseEntity<Resource> serve(HttpServletRequest request) {
        String uri = request.getRequestURI().substring(ROOT_PATH.length());
        if (!SAFE_NAME.matcher(uri).matches()) return ResponseEntity.notFound().build();
        if (uri.startsWith("/")) uri = uri.substring(1);

        final Path base = Paths.get(fileUploadPath).toAbsolutePath().normalize();
        final Path file = base.resolve(uri).normalize();
        if (!file.startsWith(base)) return ResponseEntity.notFound().build();
        if (!Files.exists(file) || !Files.isReadable(file)) return ResponseEntity.notFound().build();

        try {
            final var resource = new UrlResource(file.toUri());
            var type = Files.probeContentType(file);
            if (type == null) type = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(type))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFileName() + "\"")
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
