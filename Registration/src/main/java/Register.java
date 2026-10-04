import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Register")
public class Register extends HttpServlet {

    

    public void service(HttpServletRequest request,
                        HttpServletResponse response)
                        throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter pw = response.getWriter();

        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String course = request.getParameter("course");
        String upassword = request.getParameter("password");

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/registration_db";
            String user = "root";
            String password = "RRS88";

            Connection connect =
                    DriverManager.getConnection(url, user, password);

            String sql = "INSERT INTO users "
                    + "(fullname, email, phone, course, password) "
                    + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstmnt =
                    connect.prepareStatement(sql);

            pstmnt.setString(1, fullname);
            pstmnt.setString(2, email);
            pstmnt.setString(3, phone);
            pstmnt.setString(4, course);
            pstmnt.setString(5, upassword);

            int rowAff = pstmnt.executeUpdate();

            if (rowAff > 0) {
                pw.println("<h1>Registration Successful!</h1>");
            } else {
                pw.println("<h1>Registration Failed!</h1>");
            }

            pstmnt.close();
            connect.close();

        } catch (Exception e) {

            pw.println("<h2>Error occurred:</h2>");
            pw.println("<pre>");
            e.printStackTrace(pw);
            pw.println("</pre>");

            e.printStackTrace();
        }
    }
}


