package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ShohinDAO;

/**
 * Servlet implementation class Delete
 */
@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("Main");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("selectedShohin");
		
		 // エラーメッセージを格納するリスト
        List<String> message = new ArrayList<>();
		
		ShohinDAO dao = new ShohinDAO();
        boolean isDelete = dao.deleteShohin(id);

        if (isDelete) {
        	message.add("商品が削除されました");
            request.setAttribute("messageList", message);
        } 

        // 削除後、検索結果ページにリダイレクト
        request.getRequestDispatcher("WEB-INF/jsp/result.jsp").forward(request, response);  
	}
}
