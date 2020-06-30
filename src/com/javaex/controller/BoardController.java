package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		BoardDao boardDao = new BoardDao();
		
		if("list".equals(action)) {
			System.out.println("list");
			
			List<BoardVo> bList = boardDao.getBoardList();
			request.setAttribute("boardList", bList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			
		} else if("modifyForm".equals(action)) {
			System.out.println("modifyForm");
			
			int no = Integer.parseInt(request.getParameter("no"));
			BoardVo vo = boardDao.getBoard(no);
			request.setAttribute("vo", vo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
			
		} else if("writeForm".equals(action)) {
			System.out.println("writeForm");
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			
		} else if("read".equals(action)) {
			System.out.println("read");
			
			int no = Integer.parseInt(request.getParameter("no"));
			BoardVo vo = boardDao.getBoard(no);
			request.setAttribute("vo", vo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			
		} else if("delete".equals(action)) {
			System.out.println("delete");
			
			int no = Integer.parseInt(request.getParameter("no"));
			boardDao.boardDelete(no);
			
			WebUtil.redirect(request, response, "/ms2/board?action=list");
			
		} else if("modify".equals(action)) {
			System.out.println("modify");
			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardVo boardVo = new BoardVo(no ,title, content);
			boardDao.boardUpdate(boardVo);
			
			WebUtil.redirect(request, response, "/ms2/board?action=list");
			
		} else if("write".equals(action)) {
			System.out.println("write");
			HttpSession session = request.getSession();
			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int userNo = ((UserVo)session.getAttribute("authUser")).getNo();
			
			BoardVo boardVo = new BoardVo(title, content, userNo);
			boardDao.boardInsert(boardVo);
			
			WebUtil.redirect(request, response, "/ms2/board?action=list");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
