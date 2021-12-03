<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Quên mật khẩu </title>
</head>
<style>
    a:link,
    a:visited {
        background-color: #f44336;
        color: white;
        padding: 14px 25px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
    }

    a:hover, a:active {
        background-color: red;
    }
</style>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="center" valign="top" bgcolor="#838383"
            style="background-color: #838383;"><br> <br>
            <table width="600" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center" valign="top" bgcolor="#d3be6c"
                        style="background-color: #d3be6c; font-family: Arial, Helvetica, sans-serif; font-size: 13px; color: #000000; padding: 0px 15px 10px 15px;">

                        <div style="font-size: 48px; color:blue;">
                            <b>Hello ${name}</b>
                        </div>

                        <div style="font-size: 24px; color: #555100;">
                            <br> Bạn đã gửi yêu cầu thay đổi mật khẩu đến <b>Soc-store !!!</b> <br>
                        </div>
                        <div>
                            <br> Nhấn vào đường dẫn bên dưới để thực hiện thay đổi mật khẩu:
                            <a
                               href="${resetLink}"  target="_blank" title="Chuyển đến trang đổi mật khẩu">Chuyển đến trang đổi mật
                                khẩu</a>
                            <br>"Bỏ qua email này nếu bạn đã nhớ mật khẩu của mình hoặc bạn không thực hiện yêu cầu"
                            <br> <br>
                            <p>Link đổi mật khẩu này sẽ hết hạn sau 15 phút </p>
                            <p><b>Trân trọng </b></p>
                            <br>----------------------------------------------------------------------------------</b> </b></br>
                            <img src='cid:logoImage'/>
                            <br>
                        </div>
                    </td>
                </tr>
            </table>
            <br> <br></td>
    </tr>
</table>
</body>
</html>
