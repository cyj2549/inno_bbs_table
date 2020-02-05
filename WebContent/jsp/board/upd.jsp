<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cyj.board.vo.BoardVO"%>

<%
	BoardVO boardsel = (BoardVO) request.getAttribute("boardsel");
%>
<html>
<body>

	<form name="boardupdate" action="/CYJ_BBS_board/jsp/board/upd.do"
		method="post">
		<input type="hidden" name="boardnum" value="<%=boardsel.getBoardnum()%>" />

		<h2>게시판 수정</h2>

		<%
			//request.setCharacterEncoding("utf-8"); ////  받을 때 : 한글깨짐 해결(post 방식)
			BoardVO boardupd = (BoardVO) (request.getAttribute("boardsel"));
			// java 임포트하고 형변환 무조건!!!!!
			// 작성자 제목 내용 을 수정 할 수 있음.
		%>
		<table>

			<tr>
				<td>작성자</td>
				<td><input type="text" name="username" size="90" value=<%=boardupd.getUsername()%>></td>
			</tr>
			
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="pwd" size="90" value=<%=boardupd.getPwd() %>> </td>
			</tr>

			<tr>
				<td>제목</td>
				<td><input type="text" name="title" size="90" maxlength="100" value=<%=boardupd.getTitle()%>></td>
			</tr>

			<tr>
				<td>내용</td>
					<td><textarea  rows="10" cols="100" name="content"><%=boardupd.getContent()%></textarea></td>						
			</tr>

		</table>
		<button type="submit">수정</button>
		<!-- <input type="submit" value="수정글 등록"> -->

		<a href="javascript:history.back(-1)">뒤로가기</A>
	</form>

</body>
</html>

<!-- 				<td><input type="text" name="content" size="70" maxlength="100" -->
<%-- 					value=<%=boardupd.getContent()%>></td> --%>


<!-- <input type= "submit" value="수정글 등록"> -->
<%-- <a href ="/CYJ_BBS_board/jsp/board/sel.do?boardnum=<%=boardupd.getBoardnum()%>">수정완료</a> --%>