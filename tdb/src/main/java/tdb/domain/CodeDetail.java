package tdb.domain;

import java.util.List;

public class CodeDetail {

	/**
	 * �г�����
	 */
	private String market;
	/**
	 * ���г�������Ʒ�ֵ�List
	 */
	private List<String> varietyList;
	/**
	 * ����code��Ʒ�ִ���+4λ����
	 */
	private List<CodeInfo> normalCodeList;
	/**
	 * ��������code����
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
