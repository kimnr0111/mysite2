package com.javaex.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		UserDao userDao = new UserDao();
		
		if("joinForm".equals(action)) {
			System.out.println("joinForm");
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
			
		} else if("join".equals(action)) {
			System.out.println("join");
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			
			UserVo userVo = new UserVo(id, name, password, gender);
			userDao.userInsert(userVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
			
		} else if("loginForm".equals(action)) {
			System.out.println("loginForm");
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			
		} else if("login".equals(action)) {
			System.out.println("login");
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			
			UserVo authVo = userDao.getUser(id, password);
			
			if(authVo == null) { //로그인실패
				System.out.println("로그인실패");
				WebUtil.redirect(request, response, "/ms2/user?action=loginForm&result=fail");
			} else { //로그인성공
				//세션영역에 값을 추가
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authVo);
				WebUtil.redirect(request, response, "/ms2/main");
			}
			
		} else if("logout".equals(action)) {
			System.out.println("logout");
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.redirect(request, response, "/ms2/main");
			
		} else if("modifyForm".equals(action)) {
			System.out.println("modifyForm");
			HttpSession session = request.getSession();
			//UserVo vo = (UserVo)session.getAttribute("authUser");
			//vo.getNo();
			int no = ((UserVo)session.getAttribute("authUser")).getNo();
			
			UserVo vo = userDao.getUser(no);
			
			request.setAttribute("userVo", vo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
			
		} else if("modify".equals(action)) {
			System.out.println("modify");
			HttpSession session = request.getSession();
			
			int no = ((UserVo)session.getAttribute("authUser")).getNo();			
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			if(password == null || name == null || gender == null) {
				System.out.println("수정실패");
				WebUtil.redirect(request, response, "/ms2/main");
			} else {
				System.out.println("수정완료");
				UserVo userVo = new UserVo(no, "", password, name, gender);
				userDao.userUpdate(userVo);
				
				//세션간 업데이트
				//필요없는 값도 같이 세션에 올라감
				
				//세션의 이름, 넘버 값만 수정
				UserVo vo = (UserVo)session.getAttribute("authUser");
				vo.setName(name);
				vo.setNo(no);
				
				WebUtil.redirect(request, response, "/ms2/main");
			}
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
