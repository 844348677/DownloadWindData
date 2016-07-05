package tdb.domain;

import java.util.List;

public class CodeDetail {

	/**
	 * 市场代码
	 */
	private String market;
	/**
	 * 改市场包含的品种的List
	 */
	private List<String> varietyList;
	/**
	 * 正常code，品种代码+4位日期
	 */
	private List<CodeInfo> normalCodeList;
	/**
	 * 非正常的code代码
	 */
	private List<CodeInfo> unNormalCodeList;
	
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public List<String> getVarietyList() {
		return varietyList;
	}
	public void setVarietyList(List<String> varietyList) {
		this.varietyList = varietyList;
	}
	public List<CodeInfo> getNormalCodeList() {
		return normalCodeList;
	}
	public void setNormalCodeList(List<CodeInfo> normalCodeList) {
		this.normalCodeList = normalCodeList;
	}
	public List<CodeInfo> getUnNormalCodeList() {
		return unNormalCodeList;
	}
	public void setUnNormalCodeList(List<CodeInfo> unNormalCodeList) {
		this.unNormalCodeList = unNormalCodeList;
	}
	
}
