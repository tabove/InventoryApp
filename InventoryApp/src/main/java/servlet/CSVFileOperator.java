package servlet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CSVFileLogic;

@WebServlet("/CSVFileOperator")
public class CSVFileOperator extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doPost(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//CSVファイルの作成
		CSVFileLogic csvLogic = new CSVFileLogic();
		String csvFilePath = csvLogic.createCSVFile();
		
		//レスポンス設定 Content-Disposition attachmentでダウンロードするように指定
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=\"shohin.csv\"");
		
		//文字コード設定
		response.setCharacterEncoding("UTF-8");
		
		//ファイル内容をレスポンスに書き込む
		try{
			byte[] csvData = Files.readAllBytes(Paths.get(csvFilePath));
			response.getOutputStream().write(csvData);
		}catch(IOException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
        
	}

}
