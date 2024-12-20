import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCUpdateTest {
  // JDBC URL, 사용자명, 비밀번호를 설정합니다.
  private static final String DB_URL = "jdbc:mysql://localhost:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeBehavior=convertToNull";
  private static final String DB_USER = "root"; // MariaDB 사용자 이름
  private static final String DB_PASSWORD = ""; // MariaDB 비밀번호

  public static void main(String[] args) {
    // MariaDB에 연결을 시도합니다.
    Connection conn = null;
    PreparedStatement pstat = null;

    try {
      // JDBC 드라이버 로드
      Class.forName("com.mysql.cj.jdbc.Driver");

      conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
      System.out.println("데이터베이스 연결 성공");
      
      // 수정 임시 데이터
      int id = 2;
      String subject = "수정된 제목";
      String content = "수정된 내용";

      String sql = "UPDATE article";
      sql += " SET updateDate = NOW()";
      sql += ", `subject` = '%s'".formatted(subject);
      sql += ", content = '%s'".formatted(content);
      sql += " WHERE id = %d;".formatted(id);

      System.out.println(sql);

      pstat = conn.prepareStatement(sql);
      // 쿼리 실행

      pstat.executeUpdate();
    } catch (ClassNotFoundException e) {
      System.err.println("JDBC 드라이버를 찾지 못했습니다.");
      e.printStackTrace();
    } catch (SQLException e) {
      System.out.println("데이터 베이스 연결 실패");
      e.printStackTrace();
    } finally {
      try {
        if(pstat != null && !pstat.isClosed()) pstat.close();
        if(conn != null && !conn.isClosed()) {
          conn.close(); // 데이터 베이스 연결 해제
          System.out.println("데이터베이스 연결이 해제되었습니다.");
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
