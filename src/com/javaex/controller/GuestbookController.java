package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;

@WebServlet("/gb")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		GuestDao guestDao = new GuestDao();
		
		if("list".equals(action)) {
			System.out.println("list");
			List<GuestVo> gList = guestDao.getGuestList();
			
			request.setAttribute("guestList", gList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
			
		} else if("add".equals(action)) {
			System.out.println("add");
			
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");
			GuestVo vo = new GuestVo(name, password, content);
			guestDao.contentsInsert(vo);
			
			WebUtil.redirect(request, response, "/ms2/gb?action=list");
			
		} else if("deleteForm".equals(action)) {
			System.out.println("deleteForm");
			
			int no = Integer.parseInt(request.getParameter("no"));
			request.setAttribute("no", no);
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			
		} else if("delete".equals(action)) {
			System.out.println("delete");
			String password = request.getParameter("pass");
			int no = Integer.parseInt(request.getParameter("no"));
			
			GuestVo vo = new GuestVo(no, password);
			guestDao.guestDelete(vo);
			
			WebUtil.redirect(request, response, "/ms2/gb?action=list");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
