package com.cyj.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cyj.board.service.BoardService;
import com.cyj.board.service.impl.BoardServiceImpl;
import com.cyj.board.vo.BoardVO;

public class BoardController extends HttpServlet {

	private BoardService boardservice;

	public BoardController() {
		if (boardservice == null) {
			boardservice = new BoardServiceImpl();
		}
	}
	// dao로 갔다가 다시 역행
	// dao>serviceimpl>controller>jsp

	// 요청 > controller > serviceimpl >
	// dao > serviceimpl > controller > jsp

	private static final long serialVersionUID = 1L;

	// service로 매핑정보를 불러와 적용시킨다.
	// 존재하면 request,response에 세팅
	// 조건에 맞는 메서드(lst,sel,ins,upd) 실행
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String url = request.getServletPath(); // request.getServletPath() 파일경로
			System.out.println("xml 초기 url 들어오는 값  " + url);

			if (url.equals("/jsp/board/lst.do")) {
				System.out.println("xml 매핑 url --lst.do 목록  연결 성공~~~!!~!~");

				lst(request, response); // 목록
				System.out.println("------------------------------------------------------------최종_목록 호출 종료@@@@@@----");
				System.out
						.println("----------------------------------------------------------------------------------");
			} else if (url.equals("/jsp/board/sel.do")) {
				System.out.println("url ----- sel.do 상세 연결 성공");
				sel(request, response); // 상세
				System.out.println("--------------------------------------------------------------최종_상세 호출 종료@@@@@@@");
				System.out
						.println("----------------------------------------------------------------------------------");
			} else if (url.equals("/jsp/board/upd.do")) {
				System.out.println("url ------- upd.do 수정 연결 성공");
				upd(request, response); // 수정
				System.out.println("최종_수정 종료");

			} else if (url.equals("/jsp/board/ins.do")) {
				System.out.println("글쓰기ㅣㅣㅣㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				ins(request, response);
				System.out.println("url ------- ins.do 삽입(글쓰기) 연결 성공");
			} else if (url.equals("/jsp/board/del.do")) {
				del(request, response);
				System.out.println("get_수정");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("controller 단에 service 오류발생!!");
		}
	}

	// 요청 > controller > serviceimpl >
	// dao > serviceimpl > controller > jsp

// @@@@@@@@@목록@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	// localhost:8181/CYJ_BBS_board/jsp/board/lst.do
	public void lst(HttpServletRequest request, HttpServletResponse response) throws Exception {

		BoardVO vo = new BoardVO();

		int totalCount = boardservice.totalCount(vo); // 총 게시물 수
		request.setAttribute("totalcount", totalCount);

		vo.setSearchType(request.getParameter("searchType"));
		vo.setKeyword(request.getParameter("keyWord"));
		vo.setNotice(request.getParameter("notice"));

		// vo.setPage(Integer.parseInt(request.getParameter("page")));

		if (request.getParameter("countList") != null)
			vo.setCountList(Integer.parseInt(request.getParameter("countList")));

		if (request.getParameter("page") != null) {// get
			vo.setPage(Integer.parseInt(request.getParameter("page")));
		}
//		else {
//			vo.setPage(1);
//		}

		request.setAttribute("end", (int) Math.ceil((double) totalCount / vo.getCountList()));

		List<BoardVO> boardlst_all = boardservice.Boardlst(vo);
		System.out.println("컨트롤러 단 !!!!boardVO 리스트 객체 성공~!!~!~!");

		request.setAttribute("boardlst", boardlst_all);

		// int boardnum =Integer.parseInt(request.getParameter("boardnum"));

		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/board/lst.jsp");
		dispatcher.forward(request, response);
		System.out.println("lst 목록 -----jsp로 던지기 성공~!~!~");
	}

	// getRequestDispatcher 사용해서 url값으로 다시 넘김!!! forward를 통해 req,res를 같이 보냄
	// 즉, 특정한 단일한 값을 변수로 받으려면 Parameter, 객체를 받으려면 Attribute

// @@@@@@@@@@@상세@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@	

	private void sel(HttpServletRequest request, HttpServletResponse response) throws Exception {

		BoardVO vo = new BoardVO();
		// request.setCharacterEncoding("utf-8");

		String se = request.getParameter("se");
		System.out.println(se + " 테스트 테스트");

		vo.setBoardnum(Integer.parseInt(request.getParameter("boardnum")));
		vo.setRownum(Integer.parseInt(request.getParameter("rn")));
//		vo.setBoardnum(Integer.valueOf(request.getParameter("boardnum"))); 	  둘다가능. 형변환 --valueOf 뭔지 알아보기. 공부하기!!!!			
		System.out.println("vo 담앗나??");

		BoardVO boardsel_all = boardservice.Boardsel(vo);
		System.out.println("selllll 상세 상세 ");
		request.setAttribute("boardsel", boardsel_all);

		RequestDispatcher dispatcher = null;
		if (se == null) {
			dispatcher = request.getRequestDispatcher("/jsp/board/sel.jsp");
			System.out.println("111111111111111111111111111111111111");
		} else {
			dispatcher = request.getRequestDispatcher("/jsp/board/upd.jsp");
			System.out.println("상세 --- >수정");
		}

		dispatcher.forward(request, response);

//		request.getRequestDispatcher("/jsp/board/sel.jsp").forward(request,response);
//		System.out.println("sel 상세 ----- jsp로 던지기 성공");		
//		request.setAttribute("boardsel", boardsel_all);	
	}

// @@@@@@@@@@@@@@@수정@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@	
	private void upd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// request.setCharacterEncoding("utf-8");

		BoardVO vo = new BoardVO();

		vo.setBoardnum(Integer.parseInt(request.getParameter("boardnum")));
		vo.setUsername(request.getParameter("username"));
		vo.setTitle(request.getParameter("title"));
		vo.setContent(request.getParameter("content"));
		vo.setPwd(request.getParameter("pwd"));
		System.out.println("수정 됏나ㅏㅏ");

		int boardupd_all = boardservice.BoardUpdate(vo);
		request.setAttribute("boardupd", boardupd_all);

		response.sendRedirect("/CYJ_BBS_board/jsp/board/sel.do?boardnum=" + vo.getBoardnum() + "&rn=" + vo.getRownum());

//	 http://localhost:8181/CYJ_BBS_board/sel.do?boardnum=16   
//	 http://localhost:8181/CYJ_BBS_board/jsp/board/sel.do?boardnum=16

//	 response.sendRedirect("/CYJ_BBS_board/jsp/board/sel.do?boardnum="+ vo.getBoardnum());
//	 RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/board/upd.jsp");
//	 dispatcher.forward(request,response);

	}

// @@@@@@@@@삽입(글쓰기)@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	private void ins(HttpServletRequest request, HttpServletResponse response) throws Exception {

		BoardVO vo = new BoardVO();

		vo.setNotice(request.getParameter("notice")); // 공지사항
		vo.setTitle(request.getParameter("title")); // 제목
		vo.setUsername(request.getParameter("username")); // 작성자
		vo.setContent(request.getParameter("content")); // 내용
		vo.setIp(request.getRemoteAddr()); // ip 주소
		vo.setPwd(request.getParameter("pwd"));
		
		String ii = request.getParameter("pwd");
		
		System.out.println(vo.toString()+ "////BoardVO 값 콘솔출력ㄱㄱㄱㄱㄱㄱㄱ");   
		System.out.println(ii +"이거ㅓ이거ㅓ이거ㅣㅇ거");
		boardservice.BoardIns(vo);
		// request.setAttribute("boardins", boardins_all);

		response.sendRedirect("/CYJ_BBS_board/jsp/board/lst.do");
	}



	//@@@@@@@@@삭제@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@	
	private void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BoardVO vo = new BoardVO();

//		String pwd = vo.getPwd(boardnum);
		
		vo.setBoardnum(Integer.parseInt(request.getParameter("boardnum")));
		vo.setPwd(request.getParameter("pwd")); // 비밀번호
		int boarddel_all = boardservice.Boarddel(vo);
		
		if (boarddel_all == 1) { // 1 성공
//			if (pwd.equals(pwd)) {				
				response.sendRedirect("/CYJ_BBS_board/jsp/board/lst.do");
//			}
		} else { // 0 비밀번호 불일치
			response.sendRedirect("/CYJ_BBS_board/jsp/board/pwd_fail.jsp");
		}
	}

}











//getRequestDispatcher 사용해서 url값으로 다시 넘김... forward를 통해 req,res를 같이 보냄
//getAttribute를 통해 request에 값을 넣음
//getAttribute()의 경우 setAttribute()속성을 통한 설정이 없으면 무조건 null값을 리턴한다.

//request.setParameter()와 request.getParameter()는 String으로 밖에 받지 못하지만
//setAttribute와 getAttribute는 List를 받을 수 있습니다.
//즉, 특정한 단일한 값을 변수로 받으려면 Parameter, 객체를 받으려면 Attribute
//Servlet에서 Parameter를 받아와서 에러메시지를 출력하는 코드입니다
//서블릿에서 request.setAttribute로 Parameter를 받아오면 JSP에서는 getAttribute로 받아와야 합니다.
