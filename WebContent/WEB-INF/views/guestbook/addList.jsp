<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.javaex.vo.GuestVo" %>
<%
	List<GuestVo> gList = (List<GuestVo>)request.getAttribute("guestList");
	gList.toString();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/ms2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/ms2/assets/css/guestbook.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">

		<div id="header">
			<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
		</div>
		<!-- //header -->

		<div id="nav">
			<jsp:include page="/WEB-INF/views/include/nav.jsp"></jsp:include>
			<div class="clear"></div>
		</div>
		<!-- //nav -->

		<div id="aside">
			<jsp:include page="/WEB-INF/views/include/guestAside.jsp"></jsp:include>
		</div>
		<!-- //aside -->

		<div id="content">
			
			<div id="content-head">
            	<h3>일반방명록</h3>
            	<div id="location">
            		<ul>
            			<li>홈</li>
            			<li>방명록</li>
            			<li class="last">일반방명록</li>
            		</ul>
            	</div>
                <div class="clear"></div>
            </div>
            <!-- //content-head -->

			<div id="guestbook">
				<form action="/ms2/gb" method="post">
					<table id="guestAdd">
						<colgroup>
							<col style="width: 70px;">
							<col>
							<col style="width: 70px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th><label class="form-text" for="input-uname">이름</label></td>
								<td><input id="input-uname" type="text" name="name"></td>
								<th><label class="form-text" for="input-pass">패스워드</label></td>
								<td><input id="input-pass"type="password" name="pass"></td>
							</tr>
							<tr>
								<td colspan="4"><textarea name="content" cols="72" rows="5"></textarea></td>
							</tr>
							<tr class="button-area">
								<td colspan="4"><button type="submit">등록</button></td>
							</tr>
						</tbody>
						
					</table>
					<!-- //guestWrite -->
					<input type="hidden" name="action" value="add">
					
				</form>	
				<%for(GuestVo vo: gList) { %>
				<table class="guestRead">
					<colgroup>
						<col style="width: 10%;">
						<col style="width: 40%;">
						<col style="width: 40%;">
						<col style="width: 10%;">
					</colgroup>
					<tr>
						<td><%=vo.getNo() %></td>
						<td><%=vo.getName() %></td>
						<td><%=vo.getDate() %></td>
						<td><a href="/ms2/gb?action=deleteForm&no=<%=vo.getNo() %>">[삭제]</a></td>
					</tr>
					<tr>
						<td colspan=4 class="text-left"><%=vo.getContent() %></td>
					</tr>
				</table>
				<%} %>
				<!-- //guestRead -->
				
			</div>
			<!-- //guestbook -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>
		
		<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
		<!-- //footer -->

	</div>
	<!-- //wrap -->

</body>

</html>