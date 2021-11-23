package com.poly.datn.rest.controler.customer;

import com.poly.datn.common.Constant;
import com.poly.datn.service.FileStorageService;
import com.poly.datn.vo.ResponseDTO;
import com.poly.datn.vo.UploadFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/upload")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("uploadFile")
    public ResponseEntity<ResponseDTO<Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = "http://150.95.105.29:8800/api/upload/downloadFile/".concat(fileName);
//                = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/api/upload/downloadFile/")
//                .path(fileName)
//                .toUriString();


        String fileContentType = file.getContentType();
        long fileSize = file.getSize();

        UploadFileResponse uploadFileResponse = new UploadFileResponse();
        uploadFileResponse.setFileName(fileName);
        uploadFileResponse.setFileDownloadUri(fileDownloadUri);
        uploadFileResponse.setFileType(fileContentType);
        uploadFileResponse.setSize(fileSize);

        return ResponseEntity.ok(ResponseDTO.builder().object(uploadFileResponse)
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());

             /* return new UploadFileResponse(fileName, fileDownloadUri,
                filegetContentType, fileSize);*/
    }

    @PostMapping("uploadMultipleFiles")
    public ResponseEntity<ResponseDTO<Object>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return ResponseEntity.ok(ResponseDTO.builder().object(Arrays.asList(files)
                        .stream()
                        .map(file -> uploadFile(file))
                        .collect(Collectors.toList()))
                .code(Constant.RESPONSEDTO_CODE).message(Constant.RESPONSEDTO_MESS).build());
    }

    @GetMapping("downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
