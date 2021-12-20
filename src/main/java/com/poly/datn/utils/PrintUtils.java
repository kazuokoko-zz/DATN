package com.poly.datn.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.poly.datn.config.MyFileNotFoundException;
import com.poly.datn.dao.*;
import com.poly.datn.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrintUtils {

    @Autowired
    ResourceLoader loader;

    @Autowired
    ProductDAO productDAO;

    @Autowired
    OrderDetailsDAO orderDetailsDAO;

    @Autowired
    OrdersDAO ordersDAO;

    @Autowired
    ProductColorDAO productColorDAO;

    @Autowired
    ColorDAO colorDAO;

    @Autowired
    CustomerDAO customerDAO;

    @Autowired
    WarrantyDAO warrantyDAO;

    @Autowired
    PaymentDAO paymentDAO;

    @Autowired
    WarrantyInvoiceDAO warrantyInvoiceDAO;

    /**
     * Print order
     */
    public Resource printOrder(Integer orderId) {
        if (orderId == null) {
            throw new MyFileNotFoundException("Lỗi tham số");
        }
        Orders orders = ordersDAO.getById(orderId);
        if (orders == null) {
            throw new MyFileNotFoundException("Không tìm thấy hóa đơn với id: ".concat(String.valueOf(orderId)));
        }
        List<Color> colors = colorDAO.findAll();
        List<OrderDetails> orderDetails = orderDetailsDAO.findAllByOrderIdEquals(orderId);
        if (orderDetails.isEmpty()) {
            throw new MyFileNotFoundException("Lỗi chi tiết hóa đơn: ".concat(String.valueOf(orderId)));
        }
        Customer customer = customerDAO.findCustomerById(orders.getCustomerId());
        if (customer == null) {
            throw new MyFileNotFoundException("Lỗi khách hàng: ".concat(String.valueOf(orderId)));
        }
        try {
            Path path = Files.createTempFile(null, "pdf");
            FontFactory.register(loader.getResource("classpath:static/times.ttf").getFile().getPath());
            Font font = FontFactory.getFont("Times New Roman", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fonttable = FontFactory.getFont("Times New Roman", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fontI = FontFactory.getFont("Times New Roman", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);


            Document document = createDocument(path);
            document.open();

//            font.setColor(BaseColor.RED);
            font.setColor(BaseColor.BLACK);
            /**
             * add icon and title
             */
            PdfPTable table = new PdfPTable(6);

            PdfPCell cell = createCell("\nCỬA HÀNG SOCSTORE\nStyle of computer", font, Element.ALIGN_CENTER, Rectangle.NO_BORDER);
            cell.setColspan(2);
            table.addCell(cell);
            cell = createCell("", font, Element.ALIGN_CENTER, Rectangle.NO_BORDER);
            table.addCell(cell);
            cell = createCell("", font, Element.ALIGN_CENTER, Rectangle.NO_BORDER);
            table.addCell(cell);
            cell = createCell("", font, Element.ALIGN_CENTER, Rectangle.NO_BORDER);
            table.addCell(cell);
            Image image = Image.getInstance("classpath:static/logoshop.png");
            image.scaleToFit(100, 100);
            cell = createCell(image, Rectangle.RIGHT, Rectangle.NO_BORDER);
            table.addCell(cell);
            document.add(table);

            /**
             * add title;
             */
            document.add(new Paragraph("\n"));
            font.setSize(25);
            font.setStyle(Font.BOLD);
            Paragraph paragraph = new Paragraph("HÓA ĐƠN BÁN HÀNG", font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            font.setSize(14);
            font.setStyle(Font.NORMAL);
            paragraph = new Paragraph("HD".concat(String.format("%011d", orders.getId())), font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

            document.add(new Paragraph("\n"));

            paragraph = new Paragraph("Tên khách hàng: ".concat(customer.getFullname()), font);
            document.add(paragraph);
            if (customer.getPhone() != null) {
                paragraph = new Paragraph("Số điện thoại: ".concat(customer.getPhone()), font);
                document.add(paragraph);
            }
            if (customer.getEmail() != null) {
                paragraph = new Paragraph("Địa chỉ email: ".concat(customer.getEmail()), font);
                document.add(paragraph);
            }
            if (customer.getAddress() != null) {
                paragraph = new Paragraph("Địa chỉ: ".concat(customer.getAddress()), font);
                document.add(paragraph);
            }
            paragraph = new Paragraph("\nChi tiết mặt hàng: ", font);
            document.add(paragraph);

            table = createTableDetails(fonttable, orderDetails, colors);
//            table = new PdfPTable(20);
//            table.setWidthPercentage(100);
//            table.setSpacingBefore(10);
//
//            cell = createCell("STT", fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
//            table.addCell(cell);
//
//            cell = createCell("Tên sản phẩm", fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
//            cell.setColspan(9);
//            table.addCell(cell);
//
//            cell = createCell("Bảo hành", fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
//            cell.setColspan(2);
//            table.addCell(cell);
//
//            cell = createCell("Giá thành", fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
//            cell.setColspan(2);
//            table.addCell(cell);
//
//            cell = createCell("Khuyến mại", fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
//            cell.setColspan(2);
//            table.addCell(cell);
//
//            cell = createCell("Số lượng", fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
//            cell.setColspan(2);
//            table.addCell(cell);
//
//            cell = createCell("Thành tiền", fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
//            cell.setColspan(2);
//            table.addCell(cell);
//
//            fonttable.setStyle(Font.NORMAL);
//            for (int i = 0; i < orderDetails.size(); i++) {
//                cell = createCell(String.valueOf(i + 1), fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
//                table.addCell(cell);
//
//                OrderDetails details = orderDetails.get(i);
//                cell = createCell(productDAO.getById(details.getProductId()).getName().concat("\n\nMàu: ")
//                                .concat(colors.stream().filter(color -> color.getId() == details.getColorId()).collect(Collectors.toList()).get(0).getColorName()),
//                        fonttable, Element.ALIGN_LEFT, Rectangle.BOX);
//                cell.setColspan(9);
//                table.addCell(cell);
//
//                cell = createCell(String.valueOf(productDAO.getById(details.getProductId()).getWarranty()).concat(" tháng"), fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
//                cell.setColspan(2);
//                table.addCell(cell);
//
//                cell = createCell(String.valueOf(details.getPrice()).concat(" đ"), fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
//                cell.setColspan(2);
//                table.addCell(cell);
//
//                cell = createCell(String.valueOf(details.getDiscount()).concat(" đ"), fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
//                cell.setColspan(2);
//                table.addCell(cell);
//
//                cell = createCell(String.valueOf(details.getQuantity()), fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
//                cell.setColspan(2);
//                table.addCell(cell);
//
//                cell = createCell(String.valueOf(details.getQuantity() * details.getPrice()).concat(" đ"), fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
//                cell.setColspan(2);
//                table.addCell(cell);
//            }
            document.add(table);
            document.add(new Paragraph("\n"));
            table = createTableFooter(font, orderDetails, null);
//            table = new PdfPTable(20);
//            font.setSize(14);
//            cell = new PdfPCell();
//            cell.setColspan(20);
//            cell.setBorder(Rectangle.NO_BORDER);
//            table.addCell(cell);
//            cell = new PdfPCell();
//            cell.setColspan(20);
//            cell.setBorder(Rectangle.NO_BORDER);
//            table.addCell(cell);
//            cell = new PdfPCell();
//            cell.setColspan(20);
//            cell.setBorder(Rectangle.NO_BORDER);
//            table.addCell(cell);
//
//            cell = createCell("Tổng tiền: ", font, Element.ALIGN_LEFT, Rectangle.NO_BORDER);
//            cell.setColspan(16);
//            table.addCell(cell);
//            Long sum = 0L;
//            for (OrderDetails details : orderDetails) {
//                sum += details.getQuantity() * details.getPrice();
//            }
//            cell = createCell(String.valueOf(sum).concat(" đ"), font, Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
//            cell.setColspan(4);
//            table.addCell(cell);
//
//
//            cell = createCell("Giảm giá: ", font, Element.ALIGN_LEFT, Rectangle.NO_BORDER);
//            cell.setColspan(16);
//            table.addCell(cell);
//            Long dis = 0L;
//            for (OrderDetails details : orderDetails) {
//                dis += details.getQuantity() * details.getDiscount();
//            }
//            cell = createCell(String.valueOf(dis).concat(" đ"), font, Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
//            cell.setColspan(4);
//            table.addCell(cell);
//
//            cell = createCell("Số tiền phải trả: ", font, Element.ALIGN_LEFT, Rectangle.NO_BORDER);
//            cell.setColspan(16);
//            table.addCell(cell);
//            cell = createCell(String.valueOf(sum - dis).concat(" đ"), font, Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
//            cell.setColspan(4);
//            table.addCell(cell);
            document.add(table);

            document.add(new Paragraph("\n\n"));
            table = new PdfPTable(5);
            cell = createCell("", font, Element.ALIGN_CENTER, Rectangle.NO_BORDER);
            cell.setColspan(3);
            table.addCell(cell);

            cell = createCell("Nhân viên bán hàng", font, Element.ALIGN_CENTER, Rectangle.NO_BORDER);
            cell.setColspan(2);
            table.addCell(cell);

            cell = createCell("", font, Element.ALIGN_CENTER, Rectangle.NO_BORDER);
            cell.setColspan(3);
            table.addCell(cell);

            fontI.setStyle(Font.ITALIC);
            cell = createCell("(Ký, ghi rõ họ tên)", fontI, Element.ALIGN_CENTER, Rectangle.NO_BORDER);
            cell.setColspan(2);
            table.addCell(cell);

            document.add(table);

            document.close();
            File file = new File(path.toString());

            return loadFileAsResource(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * print payment
     */
    public Resource printPayment(Integer orderId) {
        try {
            Payment payment = paymentDAO.getByOrdersIdEquals(orderId);
            if (payment == null) {
                throw new MyFileNotFoundException("Không tìm thấy thanh toán");
            }
            Orders orders = ordersDAO.getById(orderId);
            if (payment == null) {
                throw new MyFileNotFoundException("Không tìm thấy hóa đơn");
            }
            List<Color> colors = colorDAO.findAll();
            List<OrderDetails> orderDetails = orderDetailsDAO.findAllByOrderIdEquals(orderId);
            if (orderDetails.isEmpty()) {
                throw new MyFileNotFoundException("Lỗi chi tiết hóa đơn: ".concat(String.valueOf(orderId)));
            }
            Path path = Files.createTempFile(null, "pdf");
            FontFactory.register(loader.getResource("classpath:static/times.ttf").getFile().getPath());
            Font font = FontFactory.getFont("Times New Roman", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fonttable = FontFactory.getFont("Times New Roman", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fontI = FontFactory.getFont("Times New Roman", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Document document = createDocument(path);
            document.open();
            font.setColor(BaseColor.BLACK);

            /**
             * add icon and title
             */
            PdfPTable table = new PdfPTable(6);

            PdfPCell cell = createCell("\nCỬA HÀNG SOCSTORE\nStyle of computer", font, Element.ALIGN_CENTER, Rectangle.NO_BORDER);
            cell.setColspan(2);
            table.addCell(cell);
            cell = createCell("", font, Element.ALIGN_CENTER, Rectangle.NO_BORDER);
            table.addCell(cell);
            cell = createCell("", font, Element.ALIGN_CENTER, Rectangle.NO_BORDER);
            table.addCell(cell);
            cell = createCell("", font, Element.ALIGN_CENTER, Rectangle.NO_BORDER);
            table.addCell(cell);
            Image image = Image.getInstance("classpath:static/logoshop.png");
            image.scaleToFit(100, 100);
            cell = createCell(image, Rectangle.RIGHT, Rectangle.NO_BORDER);
            table.addCell(cell);
            document.add(table);

            /**
             * add title;
             */
            document.add(new Paragraph("\n"));
            font.setSize(25);
            font.setStyle(Font.BOLD);
            Paragraph paragraph = new Paragraph("HÓA ĐƠN THANH TOÁN", font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            font.setSize(14);
            font.setStyle(Font.NORMAL);
            paragraph = new Paragraph("HD".concat(String.format("%011d", payment.getOrdersId())), font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

            table = new PdfPTable(2);

            cell = createCell("Mã giao dịch: ", font, Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            table.addCell(cell);
            cell = createCell(String.valueOf(payment.getTxnRef()), font, Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = createCell("Mã hóa giao dịch tại vnpay: ", font, Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            table.addCell(cell);
            cell = createCell(payment.getTransactionNo() == null ? "" : String.valueOf(payment.getTransactionNo()), font, Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = createCell("Thời gian giao dịch: ", font, Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            table.addCell(cell);
            cell = createCell(payment.getPayDate() == null ? "" : new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                    .format(new SimpleDateFormat("yyyyMMddHHmmss").parse(payment.getPayDate())), font, Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = createCell("Loại thanh toán: ", font, Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            table.addCell(cell);
            cell = createCell(payment.getCardType() == null ? "" : payment.getCardType(), font, Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = createCell("Số tiền: ", font, Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            table.addCell(cell);
            cell = createCell(String.valueOf(orders.getSumprice()).concat(" đ"), font, Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = createCell("Trạng thái: ", font, Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            table.addCell(cell);
            cell = createCell(payment.getStatus() == 1 ? "Thành công" : payment.getStatus() == 0 ? "Chờ thanh toán" : "Lỗi", font, Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            table.addCell(cell);
            document.add(table);
            document.add(new Paragraph("\n\n"));

            document.add(new Paragraph("Chi tiết mặt hàng", font));

            table = createTableDetails(fonttable, orderDetails, colors);
            document.add(table);

            document.add(new Paragraph("\n"));

            table = createTableFooter(font, orderDetails, orders.getSumprice());
            document.add(table);


            document.close();
            File file = new File(path.toString());

            return loadFileAsResource(file);
        } catch (MyFileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Resource printWarranty(Integer id) {
        return null;
    }

    public Resource printWarrantyIv(Integer id) {
        return null;
    }
    /**
     *  print payment
     */


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

    /**
     * Create cell
     *
     * @param text
     * @param font
     * @param align
     * @param border
     * @return
     */

    PdfPCell createCell(String text, Font font, Integer align, Integer border) {
        Chunk chunk = new Chunk(text, font);
        Phrase phrase = new Phrase(chunk);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(align);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingTop(8);
        cell.setPaddingBottom(8);
        cell.setBorder(border);
        return cell;
    }

    PdfPCell createCell(Image image, Integer align, Integer border) {
        PdfPCell cell = new PdfPCell(image);
        cell.setHorizontalAlignment(align);
        cell.setBorder(border);
        return cell;
    }

    Document createDocument(Path path) throws FileNotFoundException, DocumentException {
        Document document = new Document(PageSize.A4, 35, 20, 35, 30);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path.toString()));
        document.addAuthor("SOC STORE");
        document.addCreationDate();
        document.addProducer();
        document.addCreator("SOCSTORE.XYZ");
        document.addTitle("HÓA ĐƠN");
        return document;
    }

    PdfPTable createTableDetails(Font fonttable, List<OrderDetails> orderDetails, List<Color> colors) {
        PdfPTable table;
        PdfPCell cell;

        fonttable.setStyle(Font.BOLD);
        fonttable.setSize(10);
        table = new PdfPTable(20);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        cell = createCell("STT", fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
        table.addCell(cell);

        cell = createCell("Tên sản phẩm", fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
        cell.setColspan(9);
        table.addCell(cell);

        cell = createCell("Bảo hành", fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
        cell.setColspan(2);
        table.addCell(cell);

        cell = createCell("Giá thành", fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
        cell.setColspan(2);
        table.addCell(cell);

        cell = createCell("Khuyến mại", fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
        cell.setColspan(2);
        table.addCell(cell);

        cell = createCell("Số lượng", fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
        cell.setColspan(2);
        table.addCell(cell);

        cell = createCell("Thành tiền", fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
        cell.setColspan(2);
        table.addCell(cell);

        fonttable.setStyle(Font.NORMAL);
        for (int i = 0; i < orderDetails.size(); i++) {
            cell = createCell(String.valueOf(i + 1), fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
            table.addCell(cell);

            OrderDetails details = orderDetails.get(i);
            cell = createCell(productDAO.getById(details.getProductId()).getName().concat("\n\nMàu: ")
                            .concat(colors.stream().filter(color -> color.getId() == details.getColorId()).collect(Collectors.toList()).get(0).getColorName()),
                    fonttable, Element.ALIGN_LEFT, Rectangle.BOX);
            cell.setColspan(9);
            table.addCell(cell);

            cell = createCell(String.valueOf(productDAO.getById(details.getProductId()).getWarranty()).concat(" tháng"), fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);

            cell = createCell(String.valueOf(details.getPrice()).concat(" đ"), fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);

            cell = createCell(String.valueOf(details.getDiscount()).concat(" đ"), fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);

            cell = createCell(String.valueOf(details.getQuantity()), fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);

            cell = createCell(String.valueOf(details.getQuantity() * details.getPrice()).concat(" đ"), fonttable, Element.ALIGN_CENTER, Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
        }
        return table;
    }

    PdfPTable createTableFooter(Font font, List<OrderDetails> orderDetails, Long alreadyPay) {
        PdfPTable table;
        PdfPCell cell;
        table = new PdfPTable(20);
        font.setSize(14);
        cell = new PdfPCell();
        cell.setColspan(20);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell();
        cell.setColspan(20);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell();
        cell.setColspan(20);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = createCell("Tổng tiền: ", font, Element.ALIGN_LEFT, Rectangle.NO_BORDER);
        cell.setColspan(16);
        table.addCell(cell);
        Long sum = 0L;
        for (OrderDetails details : orderDetails) {
            sum += details.getQuantity() * details.getPrice();
        }
        cell = createCell(String.valueOf(sum).concat(" đ"), font, Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
        cell.setColspan(4);
        table.addCell(cell);


        cell = createCell("Giảm giá: ", font, Element.ALIGN_LEFT, Rectangle.NO_BORDER);
        cell.setColspan(16);
        table.addCell(cell);
        Long dis = 0L;
        for (OrderDetails details : orderDetails) {
            dis += details.getQuantity() * details.getDiscount();
        }
        cell = createCell(String.valueOf(dis).concat(" đ"), font, Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
        cell.setColspan(4);
        table.addCell(cell);
        Long priceneed = sum - dis;
        if (alreadyPay != null) {
            cell = createCell("Số tiền đã thanh toán: ", font, Element.ALIGN_LEFT, Rectangle.NO_BORDER);
            cell.setColspan(16);
            table.addCell(cell);
            cell = createCell(String.valueOf(alreadyPay).concat(" đ"), font, Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
            cell.setColspan(4);
            table.addCell(cell);
            priceneed -= alreadyPay;
        }
        cell = createCell("Số tiền phải trả: ", font, Element.ALIGN_LEFT, Rectangle.NO_BORDER);
        cell.setColspan(16);
        table.addCell(cell);
        cell = createCell(String.valueOf(priceneed).concat(" đ"), font, Element.ALIGN_RIGHT, Rectangle.NO_BORDER);
        cell.setColspan(4);
        table.addCell(cell);
        return table;
    }

}
