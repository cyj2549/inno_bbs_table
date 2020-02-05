package com.cyj.board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cyj.board.vo.BoardVO;

//요청 > controller > serviceimpl >  
//dao > serviceimpl > controller > jsp 

public class BoardDAO {
//큐브리드 데이터 베이스에 연결하고 SELECT,INSERT, UPDATE,DELETE 작업을 실행해주는 클래스
	
	private Connection getConnection() throws Exception {

		Connection conn = null;
		String driver = "core.log.jdbc.driver.CUBRIDDriver";
		String url = "jdbc:cubrid:localhost:33000:CYJPRO:::?charset=UTF-8";
		String user = "dba";
		String pwd = "rhfi456";
		try {
			Class.forName(driver); //해당 데이터 베이스를 사용한다고 선언(클래스를 등록=큐브리드용을 사용)
			conn = DriverManager.getConnection(url, user, pwd);
			System.out.println("데이터베이스 연결 성공");
		} catch (Exception e) {
			System.out.println("데이터베이스 연결 실패");
		}
		return conn;
	}

	// 요청 > controller > serviceimpl >
	// dao > serviceimpl > controller > jsp
	/**
	 * 
	 * List<BoardVO> : 리턴타입
	 * 
	 */
// @@@@@@@@목록@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	public List<BoardVO> lst(BoardVO _boardVO) throws Exception {

		List<BoardVO> boardlst_data = new ArrayList<BoardVO>();
		System.out.println("boarddao   DAO 단  1111");
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		System.out.println("sql에 담나");
                                 
		int startList = ((_boardVO.getPage() - 1) * _boardVO.getCountList() + 1); // 한 화면에 출력될 게시물 수
		int endList   = (_boardVO.getPage()) * _boardVO.getCountList();
              
		
		String sql =  "SELECT ROWNUM, B.* FROM ("
				    + "SELECT A.* FROM (SELECT ROWNUM AS rn,boardnum,notice,title,username,content,hit,upddate,"
				    + "TO_CHAR(regdate, 'YYYY-MM-DD HH24:MI') AS regdate,deletee,ip FROM BBS WHERE 1=1 ";
					
//		String sql = " SELECT * FROM (           				 	                                  "
//				+ "		SELECT             							  			                      "
//				+ "			ROWNUM 		                            AS  	     rn    	,             "
//				+ "			boardnum   				  			                        ,             "
//				+ "			notice    				  			                        ,             "
//				+ "			title                     			                        ,             "
//				+ "			username                  			                        ,             "
//				+ "	 		content                   			                        ,             "
//				+ "			hit                       			                        ,             "
//				+ "			upddate                   			                        ,             "
//
//		    	+ "			TO_CHAR(regdate,'YYYY-MM-DD HH24:MI')   AS          regdate               "
//				+ "		FROM BBS                                                                      "
//				+ "      	WHERE 1=1 			                                                      ";
              
		if (_boardVO.getSearchType() != null) {   //검색타임   제목+내용, 작성자, 제목, 내용

			if (_boardVO.getSearchType().equals("id")) {                  // 제목+내용
				sql = sql + "AND username LIKE '%"  +  _boardVO.getKeyword() +  "%'" 
			              + "OR  title    LIKE '%"  +  _boardVO.getKeyword() +  "%'" ;
				}
			    else if (_boardVO.getSearchType().equals("name")) {       // 작성자
				sql = sql + "AND username LIKE '%"  + _boardVO.getKeyword()   + "%'" ;
				}
			    else if (_boardVO.getSearchType().equals("title")) {      //제목
				sql = sql + "AND title LIKE '%" + _boardVO.getKeyword() + "%'";
			    }   
			    else if (_boardVO.getSearchType().equals("content")) {    //내용
				sql = sql + "AND content LIKE '%" + _boardVO.getKeyword() + "%'";
			}
		}
		System.out.println("ddddddddddddddd"+_boardVO.getNotice() );
		if(_boardVO.getNotice() != null) {  // 체크박스 일반+공지, 일반, 공지
					System.out.println("ddddddddddddddd"+_boardVO.getNotice() );
			if(_boardVO.getNotice().equals("YN")) {        //일반+공지
				sql = sql + "AND notice IN ('Y','N') ";
			}
			else if (_boardVO.getNotice().equals("Y")) {   //공지
				sql = sql + "AND notice LIKE 'Y'" ;
			}
			else if (_boardVO.getNotice().equals("N")) {    //일반
				sql = sql + "AND notice LIKE 'N'" ;
			}
		}
		
				sql = sql + " AND deletee = 'N' " ;
				
			    sql = sql + " ORDER BY regdate DESC)A"
			    		  + " ORDER BY A.rn DESC)B";
		
			    sql = sql+ " WHERE ROWNUM BETWEEN " + startList + " AND " + endList ;
		      //  sql = sql + " WHERE rn BETWEEN " + startList + " AND " + endList + " ORDER BY rn DESC";
		        System.out.println(sql);

		try {
			
			pstmt = conn.prepareStatement(sql); // 데이터가 빠진 sql을 전송
			rs = pstmt.executeQuery();
	
			while (rs.next()) {  // 데이터 개수가 몇개인지 모르기에 반복문을 이용하여 데이터를 추출
				BoardVO vo = new BoardVO();   // 데이터를 담기 위해 boarVO 객체에 메모리를 할당하는 코드
				
				vo.setRownum(rs.getInt("rn"));
				vo.setBoardnum(rs.getInt("boardnum"));
				vo.setNotice(rs.getString("notice"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setUsername(rs.getString("username"));
				vo.setHit(rs.getInt("hit"));
				vo.setUpddate(rs.getString("upddate"));
				vo.setRegdate(rs.getString("regdate"));
				vo.setIp(rs.getString("ip"));
				
				boardlst_data.add(vo);
			}


		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("lst DAO 단 오류발생/////");
		}
		finally { // try , catch가 끝나고 무조건 실행   
			rs.close();
			pstmt.close();
			conn.close();
		}	
		System.out.println("반환");
		return boardlst_data;
	}

// @@@@@@@상세 +조회수@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	public BoardVO sel(BoardVO _boardVO) throws Exception {
		Connection conn = getConnection();

		BoardVO boardsel_data = new BoardVO();

		try {

			String readCountsql = "UPDATE BBS SET hit = hit+1 WHERE boardnum = ? ";
			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement(readCountsql);
			pstmt.setInt(1, _boardVO.getBoardnum());
			pstmt.executeUpdate();

			String sql = "SELECT * FROM BBS WHERE boardnum = ?";

			// PreparedStatement pstmt =null;
			ResultSet rs = null;

//			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, _boardVO.getBoardnum());
			rs = pstmt.executeQuery();

//executeQuery(String sql) : SELECT문을 실행할 때 사용(ResultSet 객체 반환)

			// 상세 _글번호, 작성자, 제목, 내용, 조회수, 등록날짜
			if (rs.next()) {
				BoardVO vo = new BoardVO();
				
				
				vo.setBoardnum(rs.getInt("boardnum"));
				vo.setUsername(rs.getString("username"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setHit(rs.getInt("hit"));
				vo.setRegdate(rs.getString("regdate"));
				vo.setUpddate(rs.getString("upddate"));
				

				boardsel_data = vo;
			}
			rs.close();
			pstmt.close();
			conn.commit();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("sel DAO 단 오류발생!!!!!");
		}
		return boardsel_data;
	}
	
//	try(
//			conn = getConnection(); 
//			pstmt = conn.prepareStatement(sql);
//			) {
//@@@@@@@@@@@@@수정@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	// 작성자 제목 내용 을 수정 할 수 있음.
	public int boardupd(BoardVO _boardVO) throws Exception {

		int success = 0;

		Connection conn = getConnection();
		
//		BoardVO boardupd_data = new BoardVO();
		String sql = "UPDATE BBS SET username=?, title=?, content=?, upddate = SYSDATETIME ,pwd =? WHERE boardnum =? ";

		PreparedStatement pstmt = null;
	//	ResultSet rs = null;

		try {
			conn = getConnection(); 
			pstmt = conn.prepareStatement(sql);
			// rs = pstmt.executeQuery();

			pstmt.setString(1, _boardVO.getUsername()); // 작성자
			pstmt.setString(2, _boardVO.getTitle()); // 제목
			pstmt.setString(3, _boardVO.getContent()); // 내용
			pstmt.setString(4, _boardVO.getPwd()); // 비밀번호  
			pstmt.setInt(5, _boardVO.getBoardnum()); // 글번호

			success = pstmt.executeUpdate();

		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("수정 DAO 단 오류 발생");
		} 
		finally {
			// rs.close();
			pstmt.close();
			conn.close();
		}
		return success;
	}

//	executeQuery(String sql) : SELECT문을 실행할 때 사용(ResultSet 객체 반환)
//	executeUpdate(String sql) : 삽입, 수정, 삭제와 관련된 SQL문 실행에 쓰인다.
//	close(): Statement 객체를 반환 할 때 쓰인다.

	// executeUpdate() 메소드는 삽입, 수정, 삭제와 관련된 SQL문 실행에 쓰이는데,
	// 이것은 실행된 레코드 수를 반환해 준다.

// @@@@@@@총 게시물 수@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	// 전체 글의 갯수를 리턴 하는 메소드
	public int totalcount(BoardVO _boardVO) throws Exception {

		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;  //resultset는 select 할때 필요
		//쿼리를 실행 시킨 결과를 리턴해서 받아줌.(큐브리드 테이블의 검색된 결과를 자바 객체에 저장)
		
		int totalCount = 0;
		try {

			String sql = "SELECT COUNT(*) FROM BBS WHERE DELETEE = 'N' " ;
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); 
            
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return totalCount;
	}
	
	
	
//@@@@@@@@@@@@삽입(글쓰기)@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@	

	public int ins(BoardVO _boardVO) throws Exception {
		
		int boardins = 0;
		Connection conn = getConnection() ;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO BBS "
				   + "( boardnum, notice, title, username, content, hit, upddate, regdate, deletee,ip,pwd) "
				   + " VALUES ( seq_boardnum.next_value,  ?, ? ,? ,?, 0, SYSDATETIME, SYSDATETIME ,'N',? ,?)" ;
				
		try {
	
			pstmt =conn.prepareStatement(sql); //쿼리를 실행시켜주는 객체 선언
			
			pstmt.setString(1, _boardVO.getNotice()); // 공지사항
			pstmt.setString(2, _boardVO.getTitle());  // 제목
			pstmt.setString(3, _boardVO.getUsername()); // 작성자
			pstmt.setString(4, _boardVO.getContent()); // 내용
			pstmt.setString(5, _boardVO.getIp()); // ip주소 
			pstmt.setString(6,_boardVO.getPwd());
			
			pstmt.executeUpdate();
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();			
		}finally {

			pstmt.close();			
			conn.close();
		}		
		return boardins;
	}
	
	
//@@@@@@@@@@@삭제@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	public int Boarddel(BoardVO _boardVO) throws Exception {
		
		int boarddel = 0;
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		
		//3. 접속 후 쿼리준비
		String sql ="UPDATE BBS SET DELETEE ='Y' WHERE BOARDNUM = ? AND pwd= ?";
		
		try {
			pstmt = conn.prepareStatement(sql); // 쿼리를 사용하도록 설정
			pstmt.setInt(1, _boardVO.getBoardnum());
			pstmt.setString(2, _boardVO.getPwd());
			
			//4. 큐브리드에서 쿼리를 실행하시오 라는 소스코드 삽입
			boarddel = pstmt.executeUpdate();  // int반환 1은 성공 0은 실패 insert , update ,delete 시 사용하는 메소드			
//			if(pstmt.executeUpdate()==1) {
//				response.sendRedirect(request.getContextPath() + );
//			}
			conn.commit(); 

		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			
			pstmt.close();
			conn.close();
		}
		return boarddel;  //저장된 값 리턴
	}

//@@@@@@@@게시글 삭제를 위해 게시글에 설정된 비밀번호를 조회하는 메서드@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//	public String getPwd(int boardnum) throws Exception {
//		String pwd1 = null;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		String sql = "SELECT pwd FROM BBS WHERE boardnum = ? ";
//		try {
//			pstmt = conn.prepareStatement(sql);
//			
//			pstmt.setInt(1, boardnum);
//						
//			rs =pstmt.executeQuery();
//			conn.commit();
//			if(rs.next()) {
//				pwd1=rs.getString(1);
//			}
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			
//		}finally {
//			rs.close();
//			pstmt.close();
//			conn.close();
//		}
//		
//		
//		return pwd1;
//	}
	

	
	
}







//@@@@@@@@@@@@@@상세@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

//	public BoardVO sel(BoardVO _boardVO) throws Exception{
//		Connection conn = getConnection();
//			
//		BoardVO boardsel_data = new BoardVO();
//		
//		String sql = "SELECT * FROM BBS WHERE boardnum = ?";
//				
//		PreparedStatement pstmt =null;
//		ResultSet rs = null;
//
//		try {
//			conn= getConnection();
//			pstmt= conn.prepareStatement(sql);
//			pstmt.setInt(1, _boardVO.getBoardnum());   			
//			rs = pstmt.executeQuery();
//			
////executeQuery(String sql) : SELECT문을 실행할 때 사용(ResultSet 객체 반환)
//
//			
//		// 상세 _글번호, 작성자, 제목, 내용, 조회수, 등록날짜
//			if(rs.next()) {
//				BoardVO vo = new BoardVO();
//				vo.setBoardnum(rs.getInt("boardnum")); 
//				vo.setUsername(rs.getString("username"));
//				vo.setTitle(rs.getString("title"));
//				vo.setContent(rs.getString("content"));
//				vo.setHit(rs.getInt("hit"));
//				vo.setRegdate(rs.getString("regdate"));
//			
//				boardsel_data = vo;				
//			}
//			rs.close();
//			pstmt.close();
//			conn.close();
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			System.out.println("sel DAO 단 오류발생!!!!!");
//			
//		}
//		return boardsel_data;
//	}
