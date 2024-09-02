package edu.kh.jdbc.dao;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import edu.kh.jdbc.dto.ShopMember;

public class ShopDao {

	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	/** 사용자에게 아이디를 입력 받아 한명의 쇼핑몰 회원정보를 조회
	 * 
	 * @param conn
	 * @param memberId
	 * @return sm
	 * @throws Exception
	 */
	public ShopMember selectMember(Connection conn, String memberId) throws Exception {
		
		// 1. 결과 저장용 변수 선언
		ShopMember sm = null;
		
		try {
			// 2. SQL 작성
			Scanner sc = new Scanner(System.in);
			
			System.out.print("아이디 입력 : ");
			String id = sc.nextLine();
			
			String sql = "SELECT * FROM SHOP_MEMBERWHERE MEMBER_ID = ?";
			
			// 3. PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. ?(placeholder)에 알맞은 값 대입
			pstmt = setString(1, memberId);
			
			// 5. SQL 수행 후 결과 반환 받기
			rs = pstmt.executeQuery();
			
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("조회 성공");
				conn.commit();
			}
				
			if(rs.next()) {
				// 각 컬럼의 값 얻어오기
				String id = rs.getString("MEMBER_ID");
				String pw = rs.getString("MEMBER_PW");
				String phone    = rs.getString("PHONE");
				String gender   = rs.getString("GENDER");
				
				// 조회된 컬럼값을 이용해 Shopmember 객체 생성
				sm = new ShopMember(id, pw, phone, gender);
			}
					
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				// 사용한 JDBC 객체 자원 반환(close)
				if (pstmt != null) pstmt.close();
				if (rs != null) rs.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sm;
	}

	private PreparedStatement setString(int i, String memberId) {

		return null;
	}


}
