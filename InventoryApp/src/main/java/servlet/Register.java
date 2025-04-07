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


@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("WEB-INF/jsp/register.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String bunrui = request.getParameter("bunrui");
		String sTanka = request.getParameter("sTanka");
		String tanka = request.getParameter("tanka");
		
		 // エラーメッセージを格納するリスト
        List<String> message = new ArrayList<>();

        // バリデーションチェック
        if (id == null || id.trim().isEmpty()) {
            message.add("商品IDは必須項目です。");
        } else if (id.length() > 4) {
            message.add("商品IDは4文字以内で入力してください。");
        }
        if (name == null || name.trim().isEmpty()) {
            message.add("商品名は必須項目です。");
        }
        if (bunrui == null || bunrui.trim().isEmpty()) {
            message.add("商品分類は必須項目です。");
        }
        
        int sTankaInt = 0, tankaInt = 0;

        // 商品単価の整数チェック（未入力なら0）
        if (sTanka != null && !sTanka.trim().isEmpty()) {
            try {
                sTankaInt = Integer.parseInt(sTanka);
                if (sTankaInt < 0) message.add("販売単価は正の整数で入力してください。");
            } catch (NumberFormatException e) {
                message.add("販売単価は整数で入力してください。");
            }
        }

        // 仕入単価の整数チェック（未入力なら0）
        if (tanka != null && !tanka.trim().isEmpty()) {
            try {
                tankaInt = Integer.parseInt(tanka);
                if (tankaInt < 0) message.add("仕入単価は正の整数で入力してください。");
            } catch (NumberFormatException e) {
                message.add("仕入単価は整数で入力してください。");
            }
        }

        // エラーがある場合はフォームへ戻す
        if (!message.isEmpty()) {
        	request.setAttribute("messageList", message);
        	request.getRequestDispatcher("WEB-INF/jsp/register.jsp").forward(request, response);
        	return;
        } 		
        
        // ShohinDAO に登録処理を依頼
        ShohinDAO dao = new ShohinDAO();
        boolean success = dao.RegisterShohin(id, name, bunrui, sTankaInt, tankaInt);

        if (success) {
            // 完了メッセージを設定
            message.add("商品が正常に登録されました。");
        	request.setAttribute("messageList", message);
        } else {
            // エラーメッセージを設定
            message.add("登録処理時に問題が発生しました。");
            request.setAttribute("messageList", message);
        }

        // `register.jsp` を再表示
        request.getRequestDispatcher("WEB-INF/jsp/register.jsp").forward(request, response);
	}
}
