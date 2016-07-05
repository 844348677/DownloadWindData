package tdb.domain;

public class CodeInfo {
	private long id;
	private String windcode;
	private String code;
	private String market;
	private String cnname;
	private String enname;
	private int ntype;
	private long marketid;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getWindcode() {
		return windcode;
	}
	public void setWindcode(String windcode) {
		this.windcode = windcode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getCnname() {
		return cnname;
	}
	public void setCnname(String cnname) {
		this.cnname = cnname;
	}
	public String getEnname() {
		return enname;
	}
	public void setEnname(String enname) {
		this.enname = enname;
	}
	public int getNtype() {
		return ntype;
	}
	public void setNtype(int ntype) {
		this.ntype = ntype;
	}
	public long getMarketid() {
		return marketid;
	}
	public void setMarketid(long marketid) {
		this.marketid = marketid;
	}
	@Override
	public String toString() {
		return "CodeInfo [id=" + id + ", windcode=" + windcode + ", code=" + code + ", market=" + market + ", cnname="
				+ cnname + ", enname=" + enname + ", ntype=" + ntype + ", marketid=" + marketid + "]";
	}
	
	
	
}
