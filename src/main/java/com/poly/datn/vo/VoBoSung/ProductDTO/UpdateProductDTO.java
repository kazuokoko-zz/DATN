package com.poly.datn.vo.VoBoSung.ProductDTO;

import com.poly.datn.common.RegexEmail;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Valid
public class UpdateProductDTO extends RegexEmail {

    private Integer id;
    @NotNull(message = "Tên sản phẩm không được để trống")
    private String name;
    @NotNull(message = "Giá sản phẩm không được để trống")
    private Long price;
    @NotNull(message = "Thời gian bảo hành không được để trống")
    private Integer warranty;

    private List<String> photos;

    private String status;

    private Long Discount;
    @Size(min = 1, message = "chọn tối thiểu 1 danh mục")
    List<UpdateProductCategoryDTO> productCategories;
    @Size(min = 1, message = "chọn tối thiểu 1 màu")
    List<UpdateProductColorDTO> productColors;
    @Size(min = 1, message = "Thêm tối thiểu một thông tin")
    List<UpdateProductDetailDTO> productDetails;



}
