<body style="background: #838383;color: black">
<div style="padding: 5rem 0">
    <div style="width: 1000px; margin: 5rem auto;background-color: #a8ecec; padding-left: 3rem; border-radius: 5px">
        <div style="width: 100%">
            <h3 style="">Trạng thái đơn hàng vừa được cập nhập</h3>
        </div>
        <hr style="width: 100%; margin: 2rem 0"/>
        <div>
            <h1 style="color: red;text-align: center">CHI TIẾT TRẠNG THÁI ĐƠN HÀNG</h1>
        </div>
        <div style="display: flex; margin-top: 1rem; margin-bottom: 1rem ">
            <div style="width: 200px;font-weight: bold">Họ và tên:</div>
            <div style="width: 740px">${name}</div>
        </div>
        <div style="display: flex; margin-top: 1rem; margin-bottom: 1rem  ">
            <div style="width: 200px;font-weight: bold">Số điện thoại:</div>
            <div style="width: 740px">${phone}</div>
        </div>
        <div style="display: flex; margin-top: 1rem; margin-bottom: 1rem  ">
            <div style="width: 200px;font-weight: bold">Email:</div>
            <div style="width: 740px">${email}</div>
        </div>
        <div style="display: flex; margin-top: 1rem; margin-bottom: 1rem  ">
            <div style="width: 200px;font-weight: bold">Địa chi:</div>
            <div style="width: 740px">${address}</div>
        </div>
        <div style="display: flex; margin-top: 1rem; margin-bottom: 1rem  ">
            <div style="width: 200px;font-weight: bold">Mã số đơn hàng:</div>
            <div style="width: 740px">${orderId}</div>
        </div>
        <div style="width: 940px">
            <#assign  details = orderManagementVO>
            <#list  details as detail>
                <table style="border: solid 1px; border-collapse: collapse;padding: 5px">
                    <thead>
                    <tr>
                        <th style="border: solid 1px; border-collapse: collapse;width: 250px">Thời gian</th>
                        <th style="border: solid 1px; border-collapse: collapse;width: 465px ">Trạng thái</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td style="border: solid 1px; border-collapse: collapse;">Thời gian:  ${detail.timeChange}<br>Người thay đổi: ${detail.changedBy}</td>
                        <td style="border: solid 1px; border-collapse: collapse;">Trạng thái: ${detail.status} <br>Ghi chú:  ${detail.note}</td>
                    </tr>
                    </tbody>
                </table>
            </#list>
            <h4>Nếu có bất cứ sai sót nào, vui lòng liên hệ 0562505814 hoặc trả lời email này</h4>
            <h3>Xin chân thành cảm ơn</h3>
            <hr style="width: 100%"/>
            <img src='cid:logoImage'/>
        </div>
    </div>
</div>
</body>