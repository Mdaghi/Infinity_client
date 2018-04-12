package Util;

public class Future {
	private String Ref;
	private String symbol;	
	private String name;	
	private String lastPrice;
	private String tradeTimestamp;
	private String netChange;
	private String percentChange;
	private String open;
	private String high;
	private String low;
	private String close;
	private String volume;
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}
	public String getTradeTimestamp() {
		return tradeTimestamp;
	}
	public void setTradeTimestamp(String tradeTimestamp) {
		this.tradeTimestamp = tradeTimestamp;
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
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getClose() {
		return close;
	}
	public void setClose(String close) {
		this.close = close;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getRef() {
		return Ref;
	}
	public void setRef(String ref) {
		Ref = ref;
	}
	@Override
	public String toString() {
		return "Future [Ref=" + Ref + ", symbol=" + symbol + ", name=" + name + ", lastPrice=" + lastPrice
				+ ", tradeTimestamp=" + tradeTimestamp + ", netChange=" + netChange + ", percentChange=" + percentChange
				+ ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close + ", volume=" + volume
				+ "]";
	}
	

}
