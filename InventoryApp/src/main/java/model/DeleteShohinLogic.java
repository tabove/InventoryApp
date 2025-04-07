package model;

import dao.ShohinDAO;

public class DeleteShohinLogic {
	public boolean delete(String id) {
	    ShohinDAO dao = new ShohinDAO();
	    boolean isDelete = dao.deleteShohin(id);	    
	    
	    if(isDelete == true) {
	    	return true;
	    }else {
	    	return false;
	    }
	    
	}
}
