<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="com.cyj.board.vo.BoardVO"%>
<html>
<head>
<title>게시판 상세</title>
<!--  처음에 입력창을 빈칸으로 하고싶다면  위에 홍길동이 입력되있는 칸을 공백으로 냅두면 된다. -->
<!--  var returnValue = prompt("Prompt", ""); -->

<script type="text/javascript">
function pwdcheck(){
	
	var pwdch =prompt('비밀번호는?','비밀번호를 입력하세요');
	
	if(pwdck){
		alert("비밀번호일치");
	}else{
		alert("삭제를 취소합니다.");
		history.go(-1);
	}
}

</script>
</head>

<body>
	<h3>게시판 상세</h3>
	<form name="pwd_ck" method="post" >
		<%
 			String boardnum = request.getParameter("boardnum"); 
 			String pwd = request.getParameter("pwd"); 
 		%> 
		<input type="hidden" name="boardnum" value="<%=boardnum%>"> 
		<input type="hidden" name="pwd" value="<%=pwd%>">
	</form>

	<table border="1">
		<%
			BoardVO boardsel = (BoardVO) request.getAttribute("boardsel");
			//java 임포트하고 형변환 무조건!!!!!
		%>

		<tr>
			<td align="center">글번호</td>
			<td><%=request.getParameter("rn")%></td>

			<td width="70" align="center">조회수</td>
			<td colspan="3"><%=boardsel.getHit()%></td>
		</tr>

		<tr>
			<td width="70" align="center">작성자</td>
			<td width="120"><%=boardsel.getUsername()%></td>

			<td width="70" align="center">작성일</td>
			<td><%=boardsel.getRegdate()%></td>

			<td width="70" align="center">수정일</td>
			<td><%=boardsel.getUpddate()%></td>

		</tr>


		<tr height="30">
			<td align="center">제목</td>


			<td colspan="5"><%=boardsel.getTitle()%></td>
		</tr>

		<tr height="300">
			<td align="center">내용</td>
			<td colspan="5"><%=boardsel.getContent()%></td>
		</tr>

	</table>

	<%
		// BoardVO
	%>
	<a href="lst.do">목록으로 가기</a>
	<a
		href="sel.do?se=UU&boardnum=<%=boardsel.getBoardnum()%>&rn=<%=boardsel.getRownum()%>">수정하기</a>

		<a href="del.do?boardnum=<%=boardsel.getBoardnum()%>" onclick="pwdcheck">삭제</a>
</body>
</html>

<!-- 목록_get -->
<!-- 상세_get  -->
<!-- 상세에서 수정 잡아오는거_get  -->
<!-- 수정버튼(submit)_post -->
<!-- 게시글작성_post -->






<!-- <script type="text/javascript"> -->
<!-- // 	function pwdcheck() { -->

<!-- // 		var pwdcheck = confirm("삭제하시겠습니까???"); -->
<!-- // 		if (pwdcheck== true) {  //확인을 누를경우 -->
<!-- // 			document.pwd_ck.action="pwd_check.jsp"; -->
<!-- // 			document.pwd_ck.submit(); -->
<!-- // 		} else { -->
<!-- // 			history.go(-1); -->
<!-- // 		} -->
<!-- // 	} -->
	
<!-- </script> -->

