package com.poly.datn.service;

import com.poly.datn.config.FileStorageException;
import com.poly.datn.config.FileStorageProperties;
import com.poly.datn.config.MyFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;


@Service
public class FileStorageService {
    static final String[] EXTENSIONS = new String[]{
            "gif", "png", "bmp", "jpeg", "jpg"// and other formats you need
    };

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String s = System.currentTimeMillis() + file.getOriginalFilename();
        String fileName = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));
        //String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public List<String> getAllLinkFiles() {
//        System.out.println(Paths.get(this.fileStorageLocation.toString()));
        List<String> links = new ArrayList<>();
        File folder = new File(this.fileStorageLocation.toString());
        FilenameFilter filenameFilter = (dir, name) -> {
            for (final String ext : EXTENSIONS) {
                if (name.toLowerCase().endsWith("." + ext.toLowerCase())) {
                    return (true);
                }
            }
            return (false);
        };
        for (File f : folder.listFiles(filenameFilter)) {
//            System.out.println();
            links.add("http://150.95.105.29:8800/api/upload/downloadFile/".concat(f.getName()));
        }
        return links;
    }
}
