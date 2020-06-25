package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
	
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
			
		} catch(ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	private void close() {
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
	
	//회원추가
	public int userInsert(UserVo vo) {
		int count = 0;
		getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += "insert into users ";
			query += "values(SEQ_USERS_NO.nextval, ?, ?, ?, ?) ";
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			
			pstmt.setString(1, vo.getId()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setString(2, vo.getPassword()); // ?(물음표) 중 2번째, 순서중요
			pstmt.setString(3, vo.getName()); // ?(물음표) 중 3번째, 순서중요
			pstmt.setString(4, vo.getGender()); // ?(물음표) 중 4번째, 순서중요
			
			count = pstmt.executeUpdate();
			
			System.out.println("[" + count + "건 추가되었습니다.]");
			
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		return 0;
		
	}

}
