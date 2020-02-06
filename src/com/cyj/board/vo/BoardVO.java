package com.cyj.board.vo;

public class BoardVO {
	
	private int rownum ;

	private int boardnum;  		// 글번호
	private String notice;  	// 공지사항 (Y - 공지사항, N - 일반 게시물)
	private String username; 	// 작성자
	private String title ; 		// 제목
	private String content;  	// 내용
	private int hit;       		// 조회수
	private String upddate;  	// 수정날짜
	private String regdate;  	// 작성날짜
	
	private String searchType ;   //검색필드 : 작성자+제목, 작성자, 제목
	private String keyword ;      // 검색 키워드
	
	private int totalCount ;      // 게시 글 전체 수
	private int countList=15 ;    // 한 화면에 출력될 게시물 수  기본15
	private int page=1 ;          //현재 페이지 번호

	private int end ;
	
	private String ip;
	private String pwd;
	
	
	
	
	
	
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	
	
	
	private int countPage;  // 한 화면에 출력될 페이지 수 
	
	
	
	
	
	
	public int getCountList() {
		return countList;
	}
	public void setCountList(int countList) {
		this.countList = countList;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getCountPage() {
		return countPage;
	}
	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}

	
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	
	
	public int getBoardnum() {
		return boardnum;
	}
	public void setBoardnum(int boardnum) {
		this.boardnum = boardnum;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getUpddate() {
		return upddate;
	}
	public void setUpddate(String upddate) {
		this.upddate = upddate;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
	@Override
	public String toString() {
		return "BoardVO [notice=" + notice + ", username=" + username + ", title=" + title + ", content=" + content
				+ ", pwd=" + pwd + "]";
	}
	
	
	

	
}
