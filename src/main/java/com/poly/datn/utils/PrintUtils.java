package com.poly.datn.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.poly.datn.config.MyFileNotFoundException;
import com.poly.datn.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class PrintUtils {
    @Autowired
    OrderDetailsDAO orderDetailsDAO;

    @Autowired
    OrdersDAO ordersDAO;

    @Autowired
    CustomerDAO customerDAO;

    @Autowired
    WarrantyDAO warrantyDAO;

    @Autowired
    WarrantyInvoiceDAO warrantyInvoiceDAO;

    /**
     * Print order
     */
    public Resource printOrder(Integer orderId) {
        try {
            Path path = Files.createTempFile(null, "pdf");
            Document document = new Document(PageSize.A4, 35, 20, 35, 30);


        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


    /**
     * Convert file to resource to response
     *
     * @param file
     * @return
     */

    public Resource loadFileAsResource(File file) {
        try {
            Path filePath = file.toPath();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found.");
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found.", ex);
        }
    }

}
