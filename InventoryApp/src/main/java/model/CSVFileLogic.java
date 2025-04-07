package model;

import java.io.BufferedReader;
import java.io.FileReader;

import dao.ShohinDAO;

/*
 * CSVの処理を担当するクラス
 */
public class CSVFileLogic {
	
	//CSVファイルを生成して、そのファイルぱすを返す
	public String createCSVFile() {
		ShohinDAO dao = new ShohinDAO();
		
		dao.getDBData();
		
		return "shohin.CSV";
	}
	
	public String readCSVContent(String filePath) {
		StringBuilder sb = new StringBuilder();
		try( BufferedReader br = new BufferedReader(new FileReader(filePath))){
			String line;
			while((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		}catch(Exception e) {
				e.printStackTrace();
		}
		return sb.toString();
	}

}