import java.sql.*;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.82.3:4567/madang", "root", "1234");
            Scanner scanner = new Scanner(System.in);
            
            while (true) {
                System.out.println("1. 데이터 삽입");
                System.out.println("2. 데이터 삭제");
                System.out.println("3. 데이터 검색");
                System.out.println("4. 종료");
                System.out.print("메뉴를 선택하세요: ");
                
                int choice = scanner.nextInt();
                
                if (choice == 1) {
                    insertData(con, scanner);
                } else if (choice == 2) {
                    deleteData(con, scanner);
                } else if (choice == 3) {
                    searchData(con);
                } else if (choice == 4) {
                    break;
                } else {
                    System.out.println("유효하지 않은 메뉴 선택입니다.");
                }
            }
            
            con.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void insertData(Connection con, Scanner scanner) {
        try {
            System.out.print("Book ID: ");
            int bookId = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.print("Book Name: ");
            String bookName = scanner.nextLine();
            System.out.print("Publisher: ");
            String publisher = scanner.nextLine();
            System.out.print("Price: ");
            double price = scanner.nextDouble();
            
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Book (bookid, bookname, publisher, price) VALUES (?, ?, ?, ?)");
            pstmt.setInt(1, bookId);
            pstmt.setString(2, bookName);
            pstmt.setString(3, publisher);
            pstmt.setDouble(4, price);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("데이터가 성공적으로 삽입되었습니다.");
            } else {
                System.out.println("데이터 삽입 실패");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public static void deleteData(Connection con, Scanner scanner) {
        try {
            System.out.print("Book ID to delete: ");
            int bookId = scanner.nextInt();
            
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Book WHERE bookid = ?");
            pstmt.setInt(1, bookId);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("데이터가 성공적으로 삭제되었습니다.");
            } else {
                System.out.println("데이터 삭제 실패");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public static void searchData(Connection con) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Book");
            
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getDouble(4));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
