package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVo;

public class GuestDao {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "1234";
	
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			// System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	public void close() {
		// 5. 자원정리
				try {
					if (rs != null) {
						rs.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					System.out.println("error:" + e);
				}
	}
	
	public void contentsInsert(GuestVo guestVo) {
		int count = 0;
		getConnection();
		if(guestVo.getName().equals("") || guestVo.getPassword().equals("") || guestVo.getContent().equals("")) {
			System.out.println("다시 입력");
		} else {
			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = ""; // 쿼리문 문자열만들기, ? 주의
				query += " INSERT INTO guestbook ";
				query += " VALUES (seq_no.nextval, ?, ?, ?, sysdate) ";
				pstmt = conn.prepareStatement(query); // 쿼리로 만들기
				
				pstmt.setString(1, guestVo.getName()); // ?(물음표) 중 1번째, 순서중요
				pstmt.setString(2, guestVo.getPassword()); // ?(물음표) 중 2번째, 순서중요
				pstmt.setString(3, guestVo.getContent()); // ?(물음표) 중 3번째, 순서중요
				
				count = pstmt.executeUpdate();
				
				System.out.println("[" + count + "건 추가되었습니다.]");
				
			} catch(SQLException e) {
				System.out.println("error:" + e);
			}
		}
		close();
		
	}
	
	public List<GuestVo> getGuestList() {
		List<GuestVo> guestList = new ArrayList<GuestVo>();
		getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select  no, ";
			query += "         name, ";
			query += "         password, ";
			query += "         content, ";
			query += "         reg_date ";
			query += " from guestbook ";
			query += " order by no asc ";
			
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String date = rs.getString("reg_date");

				GuestVo guestVo = new GuestVo(no, name, password, content, date);
				guestList.add(guestVo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return guestList;
		
	}
	
	public void guestDelete(GuestVo guestVo) {
		getConnection();
		
		try {
			String query = "";
			query += " delete from guestbook ";
			query += " where password like ? ";
			query += " and no = ? ";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, guestVo.getPassword());
			pstmt.setInt(2, guestVo.getNo());
			
			pstmt.executeUpdate();
			
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
	}

}
