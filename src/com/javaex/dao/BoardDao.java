package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.GuestVo;

public class BoardDao {
	
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

	public void boardInsert(BoardVo boardVo) {
		int count = 0;
		getConnection();
		try {
			String query = "";
			query += "insert into board ";
			query += "values(seq_board_no.nextval, ?, ?, 0, sysdate, ?) ";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUserNo());
			
			count = pstmt.executeUpdate();
			
			System.out.println("[" + count + "건 추가되었습니다.]");
			
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
		close();
	}
	
	public void boardDelete(int no) {
		int count = 0;
		getConnection();
		
		try {
			String query = "";
			query += " delete from board ";
			query += " where no = ? ";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, no);
			
			count = pstmt.executeUpdate();
			
			System.out.println("[" + count + "건 추가되었습니다.]");
			
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	public void boardUpdate(BoardVo boardVo) {
		int count = 0;
		getConnection();
		
		try {
			String query = "";
			query += " update board ";
			query += " set title = ? , ";
			query += "     content = ? ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getNo());
			
			count = pstmt.executeUpdate();
			
			System.out.println("[" + count + "건 추가되었습니다.]");
			
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		
	}
	
	public List<BoardVo> getBoardList() {
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		getConnection();
		
		try {
			String query = "";
			query += " select  bo.no, ";
			query += "         bo.title, ";
			query += "         bo.content, ";
			query += "         bo.hit, ";
			query += "         bo.reg_date, ";
			query += "         bo.user_no, ";
			query += "         ur.name ";
			query += " from board bo, users ur ";
			query += " where bo.user_no = ur.no ";
			query += " order by no asc ";
			
			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String date = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");
				String name = rs.getString("name");

				BoardVo boardVo = new BoardVo(no, title, content, hit, date, userNo, name);
				boardList.add(boardVo);
				
			}
			System.out.println(boardList.toString());
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		
		return boardList;
		
	}
	
	public BoardVo getBoard(int no) {
		BoardVo vo = null;
		getConnection();
		
		try {
			String query = "";
			query += "select ur.name, bo.hit, bo.reg_date, bo.title, bo.content, bo.user_no ";
			query += "from board bo, users ur ";
			query += "where bo.user_no = ur.no ";
			query += "and bo.no = ? ";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int userNo = rs.getInt("user_no");
				
				vo = new BoardVo(no, title, content, hit, regDate, userNo, name);

			}
			System.out.println(vo.toString());
			
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
		
		return vo;
		
	}

}
