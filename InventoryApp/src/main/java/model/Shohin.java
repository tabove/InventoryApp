package model;

import java.io.Serializable;

public class Shohin implements Serializable{
	private int id;
	private String shohinId;
	private String shohinMei;
	private String shohinBunrui;
	private int hanbaiTanka;
	private int shiireTanka;
	
	public Shohin() {}
	
	public Shohin(String shohinId, String shohinMei, String shohinBunrui, int hanbaiTanka ,int shiireTanka) {
		this.shohinId = shohinId;
		this.shohinMei = shohinMei;
		this.shohinBunrui = shohinBunrui;
		this.hanbaiTanka = hanbaiTanka;
		this.shiireTanka = shiireTanka;
	}
	
	// getter、setter
	public String getShohinId() {
		return shohinId;
	}

	public void setShohinId(String shohinId) {
		this.shohinId = shohinId;
	}

	public String getShohinMei() {
		return shohinMei;
	}

	public void setShohinMei(String shohinMei) {
		this.shohinMei = shohinMei;
	}

	public String getShohinBunrui() {
		return shohinBunrui;
	}

	public void setShohinBunrui(String shohinBunrui) {
		this.shohinBunrui = shohinBunrui;
	}

	public int getHanbaiTanka() {
		return hanbaiTanka;
	}

	public void setHanbaiTanka(int hanbaiTanka) {
		this.hanbaiTanka = hanbaiTanka;
	}

	public int getShiireTanka() {
		return shiireTanka;
	}

	public void setShiireTanka(int shiireTanka) {
		this.shiireTanka = shiireTanka;
	}
}
