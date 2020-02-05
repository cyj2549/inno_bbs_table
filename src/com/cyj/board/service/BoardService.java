package com.cyj.board.service;

import java.util.List;

import com.cyj.board.vo.BoardVO;

public interface BoardService {

	// List<클래스명> Boardlst라고 정의 하고 , boardServiceImpl 클래스에서 Boardlst 구현
	
	List<BoardVO> Boardlst(BoardVO _boardVO) throws Exception;  // 목록
    

	BoardVO Boardsel(BoardVO _boardVO) throws Exception; // 상세
	
	
	int BoardUpdate(BoardVO _boardVO) throws Exception; // 수정
	
	int totalCount(BoardVO _boardVO) throws Exception ; // 총 게시물 수

	int BoardIns(BoardVO _boardVO) throws Exception ; // 삽입(글쓰기)
	
	int Boarddel(BoardVO _boardVO) throws Exception;  // 삭제
	
	//dao로 갔다가 다시 역행 
	//dao>serviceimpl>controller>jsp
	
 //요청 > controller > serviceimpl >
 //dao > serviceimpl > controller > jsp  
}
