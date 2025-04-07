package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ShohinDAO;
import model.Shohin;

@WebServlet("/Result")
public class Result extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("name");
        String bunrui = request.getParameter("bunrui");
        
        //検索用文字列に変換（安全対策
        String searchName = (name != null) ? name.trim() : "";
        String searchBunrui = (bunrui != null) ? bunrui.trim() : "";
        
        //セッションに検索キーワードを保存
        HttpSession session = request.getSession();
        session.setAttribute("selectedCategory", searchBunrui);
        session.setAttribute("searchKeyword", searchName);

        // DAOを使って検索処理
        ShohinDAO dao = new ShohinDAO();
        List<Shohin> searchResults = dao.Shohin(name, bunrui);

     // 検索結果をリクエスト属性にセット
        request.setAttribute("searchResults", searchResults);

        // JSPへフォワード
        request.getRequestDispatcher("WEB-INF/jsp/result.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		request.setCharacterEncoding("UTF-8");
//		String id = request.getParameter("id");
//		String name = request.getParameter("name");
//		String bunrui = request.getParameter("bunrui");
//		String sTanka = request.getParameter("sTanka");
//		String tanka = request.getParameter("tanka");
//		
//		ShohinDAO dao = new ShohinDAO();
//		
//		 // エラーメッセージを格納するリスト
//        List<String> message = new ArrayList<>();
//        
//        
//        if(name == null || name.trim().isEmpty() &&
//        		bunrui == null || bunrui.trim().isEmpty()) {
//        	
//        }
//        
//        
//       
	}

}
