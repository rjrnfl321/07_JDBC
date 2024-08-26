package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample4 {
	public static void main(String[] args) {
		
		// 부서명을 입력 받아
		// 해당 부서에 근무하는 사원의
		// 사번, 이름, 부서명, 직급명을
		// 직급코드 오름차순으로 조회
		
		// hint : SQL에서 문자열은 양쪽에 ''(홀따옴표) 필요
		// ex) 총무부 입력 => '총무부'
		
		
		/* 1. JDBC 객체 참조용 변수 선언 */ 
		
		Connection conn = null;
		Statement  stmt = null;
		ResultSet  rs   = null;
		
		try {
			
			/* 2. DriverManager 객체를 이용해서 Connection 객체 생성하기 */
			/* 2-1) Oracle JDBC Driver 객체를 메모리에 로드(적재) 하기 */ 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			
			
			
			/* 2-2) DB 연결 정보 작성 */
			// url == type + host + port + dbName
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "KH_KJW"; // 사용자 계정명
			String password = "KH1234"; 		// 계정 비밀번호
			
			/* 2-3) DB 연결 정보와 DriverManager를 이용해서 Connection 객체 생성 */
			conn = DriverManager.getConnection(url,userName,password);
			
			/* 3. SQL 작성 */ 
			Scanner sc = new Scanner(System.in);
			
			System.out.print("부서명 입력 : ");
			String input = sc.nextLine();
			
			String sql = """
						SELECT 
					    EMP_ID, 
						EMP_NAME, 
						NVL(DEPT_TITLE, '없음') DEPT_TITLE, 
						JOB_NAME
				FROM EMPLOYEE
				JOIN JOB USING(JOB_CODE)
				LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)""";
			
			sql += String.format(" WHERE DEPT_TITLE = '%s' "
							   + " ORDER BY JOB_CODE ASC", input);
			
			
			/* 4. Statement 객체 생성 */ 
			stmt = conn.createStatement();

			/* 5. Statement 객체를 이용해서 SQL 수행 후 결과 반환 받기 */
			rs = stmt.executeQuery(sql);

			boolean flag = true;
			// 조회 결과가 없으면 true, 있으면 false 
			
			/* 6. 조회 결과가 담겨 있는 ResultSet을 
			 *  커서(Cussor)를 이용해 1행씩 접근
			 *  각 행에 작성된 컬럼 값 얻어오기  */
			while (rs.next()) {
				
				flag = false;
				
				String empId = rs.getString("EMP_ID"); // 사번
				String empName = rs.getString("EMP_NAME"); // 이름
				String deptTitle = rs.getString("DEPT_TITLE"); // 부서명
				String jobName = rs.getString("JOB_NAME"); // 직급명
				
				System.out.printf("%s / %s / %s / %s \n",
						 empId,empName,deptTitle,jobName);
				
			}
			
			if(flag) { // flag == true == while문이 수행된 적 없음
				System.out.println("일치하는 부서가 없습니다.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			  /* 7. 사용 완료된 JDBC 객체 자원 반환(close)  */
			  /* 생성된 역순으로 close 수행 */ 
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
		
		
		
		
		
		
	}
}
