import com.sbs.board.article.Article;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JDBCSelectTest {
  // JDBC URL, 사용자명, 비밀번호를 설정합니다.
  private static final String DB_URL = "jdbc:mysql://localhost:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeBehavior=convertToNull";
  private static final String DB_USER = "root"; // MariaDB 사용자 이름
  private static final String DB_PASSWORD = ""; // MariaDB 비밀번호

  public static void main(String[] args) {
    // MariaDB에 연결을 시도합니다.
    Connection conn = null;
    PreparedStatement pstat = null;
    ResultSet rs = null;

    List<Article> articles = new ArrayList<>();

    try {
      // JDBC 드라이버 로드
      Class.forName("com.mysql.cj.jdbc.Driver");

      conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
      System.out.println("데이터베이스 연결 성공");

      String sql = "SELECT *";
      sql += " FROM article";
      sql += " ORDER BY id DESC;";

      pstat = conn.prepareStatement(sql);
      rs = pstat.executeQuery(sql);

      // rs에서 데이터 읽기
      while (rs.next()) {
        int id = rs.getInt("id");
        LocalDateTime regDate = rs.getTimestamp("regDate").toLocalDateTime();
        LocalDateTime updateDate = rs.getTimestamp("updateDate").toLocalDateTime();;
        String subject = rs.getString("subject");
        String content = rs.getString("content");

        Article article = new Article(id, regDate, updateDate, subject, content);
        articles.add(article);
      }
      
      System.out.println("결과 : " + articles);

    } catch (ClassNotFoundException e) {
      System.err.println("JDBC 드라이버를 찾지 못했습니다.");
      e.printStackTrace();
    } catch (SQLException e) {
      System.out.println("데이터 베이스 연결 실패");
      e.printStackTrace();
    } finally {
      try {
        if(rs != null && !rs.isClosed()) rs.close();
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
