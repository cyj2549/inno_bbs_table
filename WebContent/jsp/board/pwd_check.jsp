<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>삭제시 비밀번호 체크</title>
</head>

<body>
	<h2>삭제시 비밀번호 체크폼</h2>
</body>



<form action="/CYJ_BBS_board/jsp/board/pwd_check.jsp" method="post">



            <table>
                <tr>
                    <th> 비밀번호 </th>
                    <td>
                        <input type="password" name="pass" size="20">
                    </td>
                </tr>
            </table>




</form>
<input type="submit" value="확인" onclick="return passCheck()">


</html>