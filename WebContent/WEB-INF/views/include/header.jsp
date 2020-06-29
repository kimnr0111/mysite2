<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="header"> 
   
	<h1><a href="/ms2/main">MySite</a></h1>
			
			<!-- 로그인실패시, 로그인하기전 -->
			<c:if test="${sessionScope.authUser == null }">
			<ul>
				<li><a href="/ms2/user?action=loginForm">로그인</a></li>
				<li><a href="/ms2/user?action=joinForm">회원가입</a></li>
			</ul>
			</c:if>
			<!-- 로그인성공시 -->
			<c:if test="${sessionScope.authUser != null }">
			<ul>
				<li>${sessionScope.authUser.name }님 안녕하세요^^</li>
				<li><a href="/ms2/user?action=logout">로그아웃</a></li>
				<li><a href="/ms2/user?action=modifyForm">회원정보수정</a></li>
			</ul>
			</c:if>
</div>