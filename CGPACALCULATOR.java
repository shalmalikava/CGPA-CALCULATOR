import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/StudentRankingServlet")
public class CGPACalculatorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>CGPA Calculator</title></head>");
        out.println("<body>");
        out.println("<h1>CGPA Calculator</h1>");

        // Display the form to input student details and grade points
        out.println("<form method='post'>");
        for (int i = 1; i <= 5; i++) {
            out.println("<h3>Student " + i + ":</h3>");
            out.println("<label>Name:</label>");
            out.println("<input type='text' name='name[" + (i - 1) + "]' required><br>");
            out.println("<label>Maths Grade Point:</label>");
            out.println("<input type='number' step='0.01' name='maths[" + (i - 1) + "]' required><br>");
            out.println("<label>OS Grade Point:</label>");
            out.println("<input type='number' step='0.01' name='os[" + (i - 1) + "]' required><br>");
            out.println("<label>Bio Grade Point:</label>");
            out.println("<input type='number' step='0.01' name='bio[" + (i - 1) + "]' required><br>");
            out.println("<label>DAA Grade Point:</label>");
            out.println("<input type='number' step='0.01' name='daa[" + (i - 1) + "]' required><br>");
            out.println("<label>MCEB Grade Point:</label>");
            out.println("<input type='number' step='0.01' name='mceb[" + (i - 1) + "]' required><br>");
            out.println("<label>Lab1 Grade Point:</label>");
            out.println("<input type='number' step='0.01' name='lab1[" + (i - 1) + "]' required><br>");
            out.println("<label>Lab2 Grade Point:</label>");
            out.println("<input type='number' step='0.01' name='lab2[" + (i - 1) + "]' required><br>");
            out.println("<label>UHV Grade Point:</label>");
            out.println("<input type='number' step='0.01' name='uhv[" + (i - 1) + "]' required><br>");
            out.println("<label>Consti Grade Point:</label>");
            out.println("<input type='number' step='0.01' name='consti[" + (i - 1) + "]' required><br>");
            out.println("<br>");
            out.println("<br>");
        }
        out.println("<input type='submit' value='Calculate and Rank'>");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double[][] gradePoints = new double[5][9];
        String[] names = new String[5];
        
        for (int i = 0; i < 5; i++) {
            names[i] = request.getParameter("name[" + i + "]");
            gradePoints[i][0] = Double.parseDouble(request.getParameter("maths[" + i + "]"));
            gradePoints[i][1] = Double.parseDouble(request.getParameter("os[" + i + "]"));
            gradePoints[i][2] = Double.parseDouble(request.getParameter("bio[" + i + "]"));
            gradePoints[i][3] = Double.parseDouble(request.getParameter("daa[" + i + "]"));
            gradePoints[i][4] = Double.parseDouble(request.getParameter("mceb[" + i + "]"));
            gradePoints[i][5] = Double.parseDouble(request.getParameter("lab1[" + i + "]"));
            gradePoints[i][6] = Double.parseDouble(request.getParameter("lab2[" + i + "]"));
            gradePoints[i][7] = Double.parseDouble(request.getParameter("uhv[" + i + "]"));
            gradePoints[i][8] = Double.parseDouble(request.getParameter("consti[" + i + "]"));
        }

        double[] cgpa = new double[5];
        for (int i = 0; i < 5; i++) {
            cgpa[i] = calculateCGPA(gradePoints[i]);
        }

        selectionSort(cgpa, names);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>CGPA Ranking</title></head>");
        out.println("<body>");
        out.println("<h1>CGPA Ranking</h1>");
        out.println("<table border='1'>");
        out.println("<tr><th>Rank</th><th>Name</th><th>CGPA</th></tr>");
        for (int i = 0; i < 5; i++) {
            out.println("<tr><td>" + (i + 1) + "</td><td>" + names[i] + "</td><td>" + cgpa[i] + "</td></tr>");
        }
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    private double calculateCGPA(double[] gradePoints) {
        double totalCreditPoints = 0;
        double totalCredits = 0;
        double[] creditPoints = { 3, 2, 2, 4, 4, 1, 1, 1, 1 };

        for (int i = 0; i < gradePoints.length; i++) {
            totalCreditPoints += gradePoints[i] * creditPoints[i];
            totalCredits += creditPoints[i];
        }

        return totalCreditPoints / totalCredits;
    }

    private void selectionSort(double[] arr, String[] names) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] > arr[minIndex]) {
                    minIndex = j;
                }
            }
            double temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
            
            String tempName = names[minIndex];
            names[minIndex] = names[i];
            names[i] = tempName;
        }
    }
}