<body style="background: #838383;color: black">
<div style="padding: 5rem 0">
    <div style="width: 1000px; margin: 5rem auto;background-color: #a8ecec; padding-left: 3rem; border-radius: 5px">
        <div style="width: 100%">
            <h3 style="">Bạn vừa thanh toán thành công tại SOCSTORE.</h3>
        </div>
        <hr style="width: 100%; margin: 2rem 0"/>
        <div>
            <h1 style="color: red;text-align: center">CHI TIẾT ĐƠN HÀNG</h1>
        </div>
        <div style="display: flex !important; flex-direction: row;width: 1000px !important;">
            <div>
                <div style="display: flex; margin-top: 1rem; margin-bottom: 1rem ">
                    <div style="width: 200px;font-weight: bold">Họ và tên:</div>
                    <div style="width: 340px">${name}</div>
                </div>
                <div style="display: flex; margin-top: 1rem; margin-bottom: 1rem  ">
                    <div style="width: 200px;font-weight: bold">Số điện thoại:</div>
                    <div style="width: 340px">${phone}</div>
                </div>
                <div style="display: flex; margin-top: 1rem; margin-bottom: 1rem  ">
                    <div style="width: 200px;font-weight: bold">Email:</div>
                    <div style="width: 340px">${email}</div>
                </div>
                <div style="display: flex; margin-top: 1rem; margin-bottom: 1rem  ">
                    <div style="width: 200px;font-weight: bold">Địa chi:</div>
                    <div style="width: 340px">${address}</div>
                </div>
            </div>
            <div>
                <div style="display: flex; margin-top: 1rem; margin-bottom: 1rem ">
                    <div style="width: 400px;font-weight: bold; text-align: center">THÔNG TIN THANH TOÁN</div>
                </div>
                <div style="display: flex; margin-top: 1rem; margin-bottom: 1rem  ">
                    <div style="width: 200px;font-weight: bold">Số giao dịch:</div>
                    <div style="width: 200px">${payment.transactionNo}</div>
                </div>
                <div style="display: flex; margin-top: 1rem; margin-bottom: 1rem  ">
                    <div style="width: 200px;font-weight: bold">Ngân hàng:</div>
                    <div style="width: 200px">${payment.bankCode}</div>
                </div>
                <div style="display: flex; margin-top: 1rem; margin-bottom: 1rem  ">
                    <div style="width: 200px;font-weight: bold">Mã giao dịch:</div>
                    <div style="width: 200px">${payment.bankTranNo}</div>
                </div>
                <div style="display: flex; margin-top: 1rem; margin-bottom: 1rem  ">
                    <div style="width: 200px;font-weight: bold">Loại hình thanh toán:</div>
                    <div style="width: 200px">${payment.cardType}</div>
                </div>
            </div>
        </div>
        <div style="width: 940px">
            <table style="border: solid 1px; border-collapse: collapse;padding: 5px">
                <thead>
                <tr>
                    <th style="border: solid 1px; border-collapse: collapse;width: 465px ">Tên sản phẩm</th>
                    <th style="border: solid 1px; border-collapse: collapse;width: 70px">Số lượng</th>
                    <th style="border: solid 1px; border-collapse: collapse;width: 90px">Bảo hành</th>
                    <th style="border: solid 1px; border-collapse: collapse;width: 120px">Giá thành</th>
                    <th style="border: solid 1px; border-collapse: collapse;width: 95px">Khuyến mại</th>
                    <th style="border: solid 1px; border-collapse: collapse;width: 120px">Total</th>
                </tr>
                </thead>
                <tbody>
                <#assign  details = orderDetails>
                <#list  details as detail>
                    <tr>
                        <td style="border: solid 1px; border-collapse: collapse;text-align: justify">${detail.productName}</td>
                        <td style="border: solid 1px; border-collapse: collapse;text-align: center">${detail.quantity}</td>
                        <td style="border: solid 1px; border-collapse: collapse;text-align: center">${detail.warranty}</td>
                        <td style="border: solid 1px; border-collapse: collapse;text-align: center">${detail.price}
                            <span style="font-weight: bold"> VNĐ</span></td>
                        <td style="border: solid 1px; border-collapse: collapse;text-align: center">${detail.discount}
                            <span style="font-weight: bold"> VNĐ</span></td>
                        <td style="border: solid 1px; border-collapse: collapse;text-align: center">${detail.quantity * (detail.price - detail.discount)}
                            <span style="font-weight: bold"> VNĐ</span></td>
                    </tr>
                </#list>
                </tbody>
            </table>
            <div style="width: 940px;display: flex; margin-top: 1rem; margin-bottom: 1rem ">
                <div style="width: 740px">Giá thành:</div>
                <div style="width: 200px">${price} VNĐ</div>
            </div>
            <div style="width: 940px;display: flex; margin-top: 1rem; margin-bottom: 1rem ">
                <div style="width: 740px">Khuyến mại:</div>
                <div style="width: 200px">
                    <#if discount gt 0>
                        ${discount}
                    <#else>
                        0
                    </#if> VNĐ
                </div>
            </div>
            <div style="width: 940px;display: flex; margin-top: 1rem; margin-bottom: 1rem ">
                <div style="width: 740px">Số tiền phải trả:</div>
                <div style="width: 200px">${totalPrice} VNĐ</div>
            </div>
            <hr style="width: 100%"/>
            <img src='cid:logoImage'/>
        </div>
    </div>
</div>
</body>