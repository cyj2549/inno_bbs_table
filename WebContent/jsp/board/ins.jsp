<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<title>글쓰기 구현</title>
<script lang ="javascript">

	function Insert(){
		//frm1 = 변수임
		var frm1 = document.ins;
		if(!frm1.notice.value){
			alert("공지사항 여부를 선택해주세요");
			return;
		}
		if(!frm1.pwd.value){
			alert("비밀번호를 설정해 주세요");
			return;
		}
		if(!frm1.title.value){
			alert("제목을 입력해주세요");
			return;
		}
		if(!frm1.username.value){
			alert("작성자를 입력해주세요");
			return;
		}if(!frm1.content.value){
			alert("내용을 입력해주세요");
			return;
		}		
		frm1.submit();
	}

</script>

</head>
<body>
<h2>게시글 작성</h2>

<form name="ins" action="/CYJ_BBS_board/jsp/board/ins.do" method="post" onsubmit="return false;">

	<table border="1">
		<tr>
			<td>공지사항</td>
			<td><input type="radio" name="notice" value="Y">공지글
			<input type="radio" name="notice" value="N">일반글</td>
		</tr>
		
		<tr>
		<td>비밀번호</td>
		<td><input type="password" name ="pwd">  *필수</td>
		</tr>
		
		<tr>
			<td>작성자</td>
			<td><input type="text" name="username">  *필수</td>
		</tr>
		
		<tr>
			<td>제목</td>
			<td><input type="text" size="70" name="title"> *필수</td>
		</tr>
		
		<tr>
			<td>내용</td>
			<td><textarea cols="70" rows="15" name="content" ></textarea>*필수</td>
		</tr>
		
	</table>
	<li>클라이언트 IP = <%= request.getRemoteAddr() %></li>


		<br>
		<input type="button" value="저장" onclick="Insert()">
		<input type="reset" value="다시 작성">
		<a href="lst.do">목록으로</a>
</form>
</body>

</html>