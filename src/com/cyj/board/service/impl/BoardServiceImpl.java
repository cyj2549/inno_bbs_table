package com.cyj.board.service.impl;


import java.util.List;

import com.cyj.board.dao.BoardDAO;
import com.cyj.board.service.BoardService;
import com.cyj.board.vo.BoardVO;

public class BoardServiceImpl implements BoardService{

	
	private BoardDAO boardDAOO;
	
	public BoardServiceImpl() {
		
		if(boardDAOO == null) {
			boardDAOO = new BoardDAO();
		}	
	}	 
//	 List<BoardVO> : 리턴타입
//	 boardDao 에 있는 lst 메서드를 호출한뒤 리턴값을 받아오는데 타입은 
//	 List<BoardVO> 이유는 lst 메서드 리턴 타입이 List<BoardVO> 
	 
	//dao로 갔다가 다시 역행 
	//dao>serviceimpl>controller>jsp
	@Override
	public List<BoardVO> Boardlst(BoardVO _boardVO) throws Exception{	//목록	
		System.out.println("서비스 impl");
		return boardDAOO.lst(_boardVO); 
	}
	
	@Override
	public BoardVO Boardsel(BoardVO _boardVO) throws Exception{		//상세
		return boardDAOO.sel(_boardVO);
	}
	
	@Override
	public int BoardUpdate(BoardVO _boardVO) throws Exception{   //수정
		
		return boardDAOO.boardupd(_boardVO);
	}
	
	
	@Override
	public int totalCount(BoardVO _boardVO) throws Exception{  //총 게시물 수
		
		return  boardDAOO.totalcount(_boardVO);
		
	}
	
	@Override
	public int BoardIns(BoardVO _boardVO) throws Exception{
		return boardDAOO.ins(_boardVO);
	}
	
	@Override
	public int Boarddel(BoardVO _boardVO) throws Exception{
		return boardDAOO.Boarddel(_boardVO);
	}
	

	
	
 //요청 > controller > serviceimpl >  
 //dao > serviceimpl > controller > jsp  

}
