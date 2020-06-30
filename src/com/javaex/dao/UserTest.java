package com.javaex.dao;

import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

public class UserTest {

	public static void main(String[] args) {
		
		//UserDao userDao = new UserDao();

		
		/*
		UserVo vo = new UserVo("kimnr", "1234", "김누리", "male");
		userDao.userInsert(vo);
		*/
		
		/*
		UserVo vo = new UserVo(1 ,"56789", "김누리", "male");
		userDao.userUpdate(vo);
		*/
		
		BoardDao boardDao = new BoardDao();
		
		/*
		BoardVo boardVo = new BoardVo("kimnr", "1234", 1);
		boardDao.boardInsert(boardVo);
		*/
		
		/*
		BoardVo boardVo = new BoardVo(2, 1);
		boardDao.boardDelete(boardVo);
		*/
		
		/*
		BoardVo boardVo = new BoardVo(3 , "kimnr1", "12345");
		boardDao.boardUpdate(boardVo);
		*/
		
		/*
		boardDao.getBoardList();
		*/
		
		boardDao.getBoard(1);

	}

}
