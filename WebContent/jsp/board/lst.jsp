<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="com.cyj.board.vo.BoardVO"%>

<html>
<head>
<title>게시판목록</title>
</head>
<body>
	<table width="1000" border="6" summary="게시판목록">
		<h2>게시판 목록</h2>
<% 
String pageStr = request.getParameter("page");
if (pageStr == null)
	pageStr = "1";


//int pageprev = Integer.parseInt(String.valueOf(request.getParameter("page")));

int pageprev = Integer.parseInt(pageStr);
 
if(pageprev-1 <= 0){  //1페이지 일때 ,[이전] 버튼을 누른경우 계속 1페이지로 머물게
	pageprev=2;      
}

%>

		<%
			// int totalcount = (int)request.getAttribute("totalcount");
			int totalcount = Integer.parseInt(String.valueOf(request.getAttribute("totalcount")));
		//	int page = Integer.parseInt(String.valueOf(request.getParameter("page")));
		%>

		<h3>총 게시물 수는 <%= totalcount%> 건 입니다.</h3>
	    <h3>현재 페이지는 <%= pageStr%> 페이지 입니다.</h3>
		
		<tr>
			<td width="50" align="center">순번</td>
			<td width="130" align>공지사항(Y-공지,N-일반)</td>
			<td width="300" align="center">제목</td>
			<td width="80" align="center">조회수</td>

			<td width="130" align="center">작성자</td>
			<td width="200" align="center">작성일</td>
			<td width="200" align="center">ip주소</td>

		</tr>
		<%
			// Atrribute로 저장되는 순간 해당 파라미터는 Object 형으로 형변환되어 저장된다.
			List<BoardVO> boardlsttt = (List<BoardVO>) request.getAttribute("boardlst");
			for (int i = 0; i < boardlsttt.size(); i++) {
				BoardVO st = boardlsttt.get(i);

				////////////////////////////////////////////////
				//제목 30자리 짜르기
				String title = "";

				if (st.getTitle().length() > 30) {
					title = st.getTitle().substring(0, 30)+ "...";
				} else {
					title = st.getTitle();
				}
				////////////////////////////////////////////////		
				//일반게시물 YN 구분
				String noticeyn = "";
				if (st.getNotice().equals("N")) {
					noticeyn = "일반게시물";
				} else {
					noticeyn = "공지사항";
				}
		%>



		<tr>
			<td align="center"><%=st.getRownum()%></td>
			<td align="center"><%=noticeyn%></td>
			<td align="center">
			<a href=/CYJ_BBS_board/jsp/board/sel.do?boardnum=<%=st.getBoardnum()%>&rn=<%=st.getRownum()%>><%=title%></a></td>
			<td align="center"><%=st.getHit()%></td>
			<td align="center"><%=st.getUsername()%></td>
			<td align="center"><%=st.getRegdate()%></td>
			<td align="center"><%=st.getIp()%></td>
		</tr>

		<%
			}
		%>

	</table>


	<a href="/CYJ_BBS_board/jsp/board/lst.do?page=1">[처음페이지]</a>  
	<a href="/CYJ_BBS_board/jsp/board/lst.do?page=<%=pageprev-1%>">[이전]</a>
	
	
<%--    <a href="/CYJ_BBS_board/jsp/board/lst.do?page=<%=page-1%>">[이전]</a> --%>

	<%
		//int end = (int)request.getAttribute("end"); 
		int end = Integer.parseInt(String.valueOf(request.getAttribute("end")));
		for (int i = 1; i < end + 1; i++) {
	%>

	<a href="/CYJ_BBS_board/jsp/board/lst.do?page=<%=i%>&countList=15&notice=<%=request.getParameter("notice")%>"><%=i%>
	</a>
	<%
		}
	%>

<a href="/CYJ_BBS_board/jsp/board/lst.do?page=<%=pageprev+1%>">[다음]</a>
<a href="/CYJ_BBS_board/jsp/board/lst.do?page=<%=end%>">[마지막페이지]</a>

	<br>
	<!--한칸띄우기 -->
	<a href="ins.jsp">새글작성</a>
	<br>

	

</body>


<%
String schNotice = request.getParameter("");
%>

<tr>
	<td colspan="7"><br />
		<form action="/CYJ_BBS_board/jsp/board/lst.do" name="serach"
			method="get">
			<select name="searchType">
				<option value="0">----선택----</option>
				<option value="id">작성자+제목</option>
				<option value="name">작성자</option>
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select> <input type="text" name="keyWord" /> <input type="submit" value="검색" />

		</form>

		<form name="Notice" action="/CYJ_BBS_board/jsp/board/lst.do"
			method="get">
			<label><input type="radio" name="notice" value="YN">공지글+일반글</label>
			<label><input type="radio" name="notice" value="N">일반글</label>
			<label><input type="radio" name="notice" value="Y">공지글</label>
			<input type="submit" value="검색" />

		</form></td>
</tr>

<h2><a href="lst.do">새로고침</a></h2>

<!-- 	<table border="5" summary ="연습"> -->
<!-- 	<h4>------------test연습------------</h4> -->
<!-- 	</table> -->

</html>


