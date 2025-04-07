package dao;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Shohin;

public class ShohinDAO {
	//データベースに使用する情報
	private final String URL = "jdbc:postgresql://localhost:5432/shop";
    private final String USER = "postgres";
    private final String PASSWORD = "test";
    
    //コンストラクタ
    public ShohinDAO(){
    	/* JDBCドライバの準備*/
    	try {
    		Class.forName	("org.postgresql.Driver");
    	}catch(ClassNotFoundException e){
    		e.printStackTrace();
    	}
    }
    
    
    public List<Shohin> Shohin() {
    	//SQL文の準備
    	String sql = "SELECT * FROM Shohin ";
    	sql += " ORDER BY shohin_id ASC; ";
    	
    	List<Shohin> shohinList = null;
    	
    	//SQL接続
    	try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
    			PreparedStatement st = con.prepareStatement(sql);){
    		
    		//SQL文の実行
    		ResultSet rs = st.executeQuery();
    		
    		//結果の差し替え
    		shohinList = makeShohinList(rs);
    		
    	}catch(Exception e) {
    		System.out.println("DBアクセス時にエラーが発生しました");
    		e.printStackTrace();
    	}	
    	return shohinList;
    }
    
    
    //部分一致検索
    public List<Shohin> Shohin(String name, String bunrui) {
        
    	List<Shohin> shohinList = new ArrayList<>();

        String sql = "SELECT * FROM Shohin WHERE 1=1" ;
        if (name != null && !name.isEmpty()) {
            sql += " AND shohin_mei LIKE ?" ;  // 商品名の検索
        }
        if (bunrui != null && !bunrui.isEmpty()) {
            sql += " AND shohin_bunrui = ?" ;  // 商品分類の検索
        }
        sql += " ORDER BY shohin_id ASC; " ;

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement st = con.prepareStatement(sql)) {

            int index = 1;
            if (name != null && !name.isEmpty()) {
            	 // 部分一致検索
                st.setString(index++, "%" + name + "%");
            }
            if (bunrui != null && !bunrui.isEmpty()) {
                st.setString(index++, bunrui);
            }
            
            ResultSet rs = st.executeQuery();
            
            //結果の差し替え
    		shohinList = makeShohinList(rs);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shohinList;
    }

    /*
     * 「商品」テーブルから
     * 商品IDでデータを検索して出力。
     * （PreparedStatement方式）
     */
    public List<Shohin> selectShohin(String id) {
        /* 1) SQL文の準備 */
        String sql = "";
        sql = "SELECT * FROM Shohin ";
        sql += "WHERE shohin_id = ? ";
        sql += " ORDER BY shohin_id ASC; ";
        
        List<Shohin> shohinList = null;

        /* 2) PostgreSQLへの接続 */
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement st = con.prepareStatement(sql);) {

			/* 3) SQL文の?部分を置き換え */
			st.setString(1, id);

			/* 4) SQL文の実行 */
			ResultSet rs = st.executeQuery();

			/* 5) 結果の画面表示 */
			shohinList = makeShohinList(rs);

		} catch (Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}
		return shohinList;
    }

    /*
     * 「商品」テーブルから
     * 商品名と商品分類でデータを検索します。
     * （PreparedStatement方式）
     */
    public List<Shohin> selectMeiAndBunrui(String mei, String bunrui) {
        /* 1) SQL文の準備 */
        String sql = "";
        sql = "SELECT * FROM Shohin ";
        sql += "WHERE shohin_mei = ? ";
        sql += "AND shohin_bunrui = ?;";
        
        List<Shohin> shohinList = null;

        /* 2) PostgreSQLへの接続 */
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement st = con.prepareStatement(sql);) {

			/* 3) SQL文の?部分を置き換え */
			st.setString(1, mei);
			st.setString(2, bunrui);

			/* 4) SQL文の実行 */
			ResultSet rs = st.executeQuery();

			/* 5) 結果の画面表示 */
			shohinList = makeShohinList(rs);

		} catch (Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}
		return shohinList;
    }
    
    //商品登録(Register
    public boolean RegisterShohin(String id,
								  String mei,
								  String bunrui,
								  int sTankaInt,
								  int tankaInt){
	    /* SQL文の準備*/
    	String sql = "";
        sql = "INSERT INTO Shohin(shohin_id, shohin_mei, shohin_bunrui, hanbai_tanka, shiire_tanka, torokubi) ";
        sql += "VALUES(?, ?, ?, ?, ?, NULL);";
	    
	    /* PostgreSQLへの接続 */
	    try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
	    		PreparedStatement st = con.prepareStatement(sql)) {
	    	st.setString(1, id);
			st.setString(2, mei);
			st.setString(3, bunrui);
			st.setInt(4, sTankaInt);
			st.setInt(5, tankaInt);
			
			int insCnt = st.executeUpdate();			
			System.out.println(insCnt + "行登録しました。");
			return true;
			
	    }catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
    
    /*
     * 「商品」テーブルから商品IDでデータを更新します。
     * （PreparedStatement方式）
     */
    public boolean updateShohin(String id, String name, String bunrui, int sTanka, int tanka) {
        /* 1) SQL文の準備 */
        String sql = "";
        sql = "UPDATE shohin ";
        sql += "SET shohin_mei = ? , shohin_bunrui = ? , hanbai_tanka = ? , shiire_tanka = ? , torokubi = now() ";
        sql += "WHERE shohin_id = ?;";

        /* 2) PostgreSQLへの接続 */
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement st = con.prepareStatement(sql);) {

			/* 3) SQL文の?部分を置き換え */
			st.setString(1, name);
			st.setString(2, bunrui);
			st.setInt(3, sTanka);
			st.setInt(4, tanka);
			st.setString(5, id);

			/* 4) SQL文の実行 */
			int updCnt = st.executeUpdate();
			System.out.println(updCnt + "行更新されました。");
			return true;

		} catch (Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
			return false;
		}
    }
    
    //データ削除
    public boolean deleteShohin(String id) {
    	//SQL文の準備
    	String sql = "";
    	sql = "DELETE FROM Shohin ";
    	sql += "WHERE shohin_id = ?;";
    	
    	//SQLへの接続
    	try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
    		PreparedStatement st = con.prepareStatement(sql);){
    		
    		//SQL文の？を置換
    		st.setString(1, id);
    		
    		//SQL文の実行
    		int delCnt = st.executeUpdate();
    		System.out.println(delCnt + "行削除しました。");
    		return delCnt > 0;
    		
    	}catch(Exception e) {
    		System.out.println("DBアクセス時にエラーが発生しました");
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public void insertShohin(String id,
							  String name,
							  String bunrui,
							  int sTanka,
							  int tanka) {
		/* 1) SQL文の準備 */
		String sql = "";
		sql = "INSERT INTO shohin(shohin_id, shohin_mei, shohin_bunrui, hanbai_tanka, shiire_tanka, torokubi) ";
		sql += "VALUES(?, ?, ?, ?, ?, NULL);";
		
		/* 2) PostgreSQLへの接続 */
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
		PreparedStatement st = con.prepareStatement(sql);) {
		
		/* 3) SQL文の?部分を置き換え */
		st.setString(1, id);
		st.setString(2, name);
		st.setString(3, bunrui);
		st.setInt(4, sTanka);
		st.setInt(5, tanka);
		
		/* 4) SQL文の実行 */
		int insCnt = st.executeUpdate();
		System.out.println(insCnt + "行登録されました。");
		
		} catch (Exception e) {
		System.out.println("DBアクセス時にエラーが発生しました。");
		e.printStackTrace();
		}
    }
    
    
    
    //CSV出力
    public void getDBData(){
        /* 1) 準備 */
        String sql = "SELECT * FROM Shohin ORDER BY shohin_id ASC;";
        String csvFilePath = "shohin.csv";
		FileWriter fw = null;
        
        /* 2) PostgreSQLへの接続 */
    	try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement st = con.createStatement();) {
    		
    	/* 3) SQL文の実行 */
			ResultSet rs = st.executeQuery(sql);
			
		/* 4) csvファイルへの書き込み */
			fw = new FileWriter(csvFilePath);
			
			int columnCount = rs.getMetaData().getColumnCount();
			
			//ヘッダー行
			fw.write("商品ID,商品名,商品分類,販売単価,仕入単価");
			fw.append("\n");
			
			//改行確認
			boolean isFirstRow = true;
			
			// データを書き込む
			while (rs.next()) {
				
				if(!isFirstRow) {
					fw.append("\n");
				}else {
					isFirstRow = false;
				}
				
				for (int i = 1; i <= columnCount; i++) {
					fw.append(rs.getString(i));
					if (i < columnCount) {
						fw.append(",");
					}
				}

			}
			System.out.println("出力処理が完了しました。");
			
		} catch (Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}finally {
			if(fw != null) {
				try {
					fw.flush();
					fw.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
    }
    
    public List<Shohin> makeShohinList(ResultSet rs)throws Exception{
    	
    	//全検索結果を格納するリストを準備
    	List<Shohin> shohinList = new ArrayList<Shohin>();
    	
    	while(rs.next()) {
    		String shohinId = rs.getString("shohin_id");
    		String shohinMei = rs.getString("shohin_mei");
			String shohinBunrui = rs.getString("shohin_bunrui");
			int hanbaiTanka = rs.getInt("hanbai_tanka");
			int shiireTanka = rs.getInt("shiire_tanka");
    	
			//1行分のデータを格納するインスタンス
			Shohin shohin = new Shohin(shohinId,
										shohinMei,
										shohinBunrui,
										hanbaiTanka,
										shiireTanka);

			// リストに1行分のデータを追加する
			shohinList.add(shohin);
		}
    	return shohinList;
    }
}
