package Util;

import java.util.Date;
import javafx.scene.control.Button;
public class FutureContractTw {
	
	private int id;
	private String symbol;
	private Double maturityPrice;
	private Date dateMaturite;
	private Integer size;
	private Double currentPrice;
	private String netChange;
	private String percentChange;
	private Button delete;
	private Button update;
	private Button speculation;
	/******/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Double getMaturityPrice() {
		return maturityPrice;
	}
	public void setMaturityPrice(Double maturityPrice) {
		this.maturityPrice = maturityPrice;
	}
	public Date getDateMaturite() {
		return dateMaturite;
	}
	public void setDateMaturite(Date dateMaturite) {
		this.dateMaturite = dateMaturite;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}
	public String getNetChange() {
		return netChange;
	}
	public void setNetChange(String netChange) {
		this.netChange = netChange;
	}
	public String getPercentChange() {
		return percentChange;
	}
	public void setPercentChange(String percentChange) {
		this.percentChange = percentChange;
	}
	public Button getDelete() {
		return delete;
	}
	public void setDelete(Button delete) {
		this.delete = delete;
	}
	public Button getUpdate() {
		return update;
	}
	public void setUpdate(Button update) {
		this.update = update;
	}
	public Button getSpeculation() {
		return speculation;
	}
	public void setSpeculation(Button speculation) {
		this.speculation = speculation;
	}
	
	
	

	



	
}
