package tdb.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bridj.Pointer;

import tdb.domain.CodeDetail;
import tdb.domain.CodeInfo;
import tdbapi.TDBDefine_Code;

public class CodeUtil {
	/**
	 * �����г���Ϣ����ȡ���е�CodeInfo���ŵ�List����  ͬ�¹��˵�����
	 */
	public static List<CodeInfo> getCodes(Pointer<Pointer<TDBDefine_Code>> pCodeTable,int pCount){
		List<CodeInfo> resultList = new LinkedList<CodeInfo>();
		System.out.println("��ô���(AG1312.SHF),����������(ag1312),�г�����(SHF),֤ȯ��������,֤ȯӢ������");
		for(int i=0;i<pCount;i++){
			TDBDefine_Code codeInfo = pCodeTable.get().get(i);
			String chMarket = new String(codeInfo.chMarket().getBytes()).trim(); //�г�����(SHF)		
			if(!chMarket.contains("����")){  //���˵� ����
				CodeInfo codeObject = new CodeInfo(); //û��id��û��marketid��
				String chCNName = new String(codeInfo.chCNName().getBytes()).trim();    //֤ȯ��������
				String chWindCode = new String(codeInfo.chWindCode().getBytes()).trim(); //��ô���(AG1312.SHF)
				String chCode = new String(codeInfo.chCode().getBytes()).trim(); //����������(ag1312)
	
				String chENName = new String(codeInfo.chENName().getBytes()).trim();     //֤ȯӢ������
				int nType = codeInfo.nType();  //֤ȯ����
				
				//System.out.println(i+" : "+chWindCode+","+chCode+","+chMarket+","+chCNName+","+chENName+","+nType);
				codeObject.setWindcode(chWindCode);
				codeObject.setCode(chCode);
				codeObject.setMarket(chMarket);
				codeObject.setCnname(chCNName);
				codeObject.setEnname(chENName);
				codeObject.setNtype(nType);
				resultList.add(codeObject);
			}
		}
		return resultList;
	}
	
	/**
	 * ��ȡ�����list
	 * @param codeList
	 * @return
	 */
	public static List<String> getVariety(List<CodeInfo> codeList){
		Set<String> resultSet = new HashSet<String>();
		for(int i=0;i<codeList.size();i++){
			CodeInfo codeInfo = codeList.get(i);
			String code = codeInfo.getCode();
			if(DateUtil.isNormalCode(code)){
				String variety = code.substring(0,code.length()-4);
				//System.out.println(variety);
				resultSet.add(variety);
			}
		}
		
		return new ArrayList<>(resultSet);
	}
	
	/**
	 * ����CodeList ����CodeDetail����market���� ��Ʒ��list��������code���룬��������code����
	 * @param codeList
	 * @return
	 */
	public static CodeDetail getCodeDetailByList(List<CodeInfo> codeList,String market){
		CodeDetail result = new CodeDetail(); //���ؽ��
		result.setMarket(market); //�г�market����
		Set<String> resultSet = new HashSet<String>(); //Ʒ��set
		List<CodeInfo> normalCodeList = new LinkedList<>();
		List<CodeInfo> unNormalCodeList = new LinkedList<>();
		
		for(int i=0;i<codeList.size();i++){
			CodeInfo codeInfo = codeList.get(i);
			String code = codeInfo.getCode();
			if(DateUtil.isNormalCode(code)){ //����code������ַ������ж��ǲ��������� code
				String variety = code.substring(0,code.length()-4);
				//System.out.println(variety);
				resultSet.add(variety);
				normalCodeList.add(codeInfo);
			}else{
				unNormalCodeList.add(codeInfo);
			}
		}
		result.setVarietyList(new ArrayList<>(resultSet));
		result.setNormalCodeList(normalCodeList);
		result.setUnNormalCodeList(unNormalCodeList);
		
		return result;
		
		
	}
	
	
	public static List<String> getUpdateSHFCodeList(){
		List<String> result = new LinkedList<>();
/*		result.add("cu.SHF");
		result.add("ru.SHF");
		result.add("ni.SHF");
		result.add("rb.SHF");*/
		result.add("ag1601.SHF");
		result.add("ag1602.SHF");
		result.add("ag1603.SHF");
		result.add("ag1604.SHF");
		result.add("ag1605.SHF");
		result.add("ag1606.SHF");
		result.add("ag1607.SHF");
		result.add("ag1608.SHF");
		result.add("ag1609.SHF");
		result.add("ag1610.SHF");
		result.add("ag1611.SHF");
		result.add("ag1612.SHF");

		result.add("al1601.SHF");
		result.add("al1602.SHF");
		result.add("al1603.SHF");
		result.add("al1604.SHF");
		result.add("al1605.SHF");
		result.add("al1606.SHF");
		result.add("al1607.SHF");
		result.add("al1608.SHF");
		result.add("al1609.SHF");
		result.add("al1610.SHF");
		result.add("al1611.SHF");
		result.add("al1612.SHF");
		
		result.add("au1601.SHF");
		result.add("au1602.SHF");
		result.add("au1603.SHF");
		result.add("au1604.SHF");
		result.add("au1605.SHF");
		result.add("au1606.SHF");
		result.add("au1607.SHF");
		result.add("au1608.SHF");
		result.add("au1609.SHF");
		result.add("au1610.SHF");
		result.add("au1611.SHF");
		result.add("au1612.SHF");
		
		result.add("bu1601.SHF");
		result.add("bu1602.SHF");
		result.add("bu1603.SHF");
		result.add("bu1604.SHF");
		result.add("bu1605.SHF");
		result.add("bu1606.SHF");
		result.add("bu1607.SHF");
		result.add("bu1608.SHF");
		result.add("bu1609.SHF");
		result.add("bu1610.SHF");
		result.add("bu1611.SHF");
		result.add("bu1612.SHF");
		
		result.add("cu1601.SHF");
		result.add("cu1602.SHF");
		result.add("cu1603.SHF");
		result.add("cu1604.SHF");
		result.add("cu1605.SHF");
		result.add("cu1606.SHF");
		result.add("cu1607.SHF");
		result.add("cu1608.SHF");
		result.add("cu1609.SHF");
		result.add("cu1610.SHF");
		result.add("cu1611.SHF");
		result.add("cu1612.SHF");
		
		result.add("fu1601.SHF");
		result.add("fu1602.SHF");
		result.add("fu1603.SHF");
		result.add("fu1604.SHF");
		result.add("fu1605.SHF");
		result.add("fu1606.SHF");
		result.add("fu1607.SHF");
		result.add("fu1608.SHF");
		result.add("fu1609.SHF");
		result.add("fu1610.SHF");
		result.add("fu1611.SHF");
		result.add("fu1612.SHF");
		
		result.add("hc1601.SHF");
		result.add("hc1602.SHF");
		result.add("hc1603.SHF");
		result.add("hc1604.SHF");
		result.add("hc1605.SHF");
		result.add("hc1606.SHF");
		result.add("hc1607.SHF");
		result.add("hc1608.SHF");
		result.add("hc1609.SHF");
		result.add("hc1610.SHF");
		result.add("hc1611.SHF");
		result.add("hc1612.SHF");
		
		result.add("im1601.SHF");
		result.add("im1602.SHF");
		result.add("im1603.SHF");
		result.add("im1604.SHF");
		result.add("im1605.SHF");
		result.add("im1606.SHF");
		result.add("im1607.SHF");
		result.add("im1608.SHF");
		result.add("im1609.SHF");
		result.add("im1610.SHF");
		result.add("im1611.SHF");
		result.add("im1612.SHF");
		
		result.add("ni1601.SHF");
		result.add("ni1602.SHF");
		result.add("ni1603.SHF");
		result.add("ni1604.SHF");
		result.add("ni1605.SHF");
		result.add("ni1606.SHF");
		result.add("ni1607.SHF");
		result.add("ni1608.SHF");
		result.add("ni1609.SHF");
		result.add("ni1610.SHF");
		result.add("ni1611.SHF");
		result.add("ni1612.SHF");
		
		result.add("pb1601.SHF");
		result.add("pb1602.SHF");
		result.add("pb1603.SHF");
		result.add("pb1604.SHF");
		result.add("pb1605.SHF");
		result.add("pb1606.SHF");
		result.add("pb1607.SHF");
		result.add("pb1608.SHF");
		result.add("pb1609.SHF");
		result.add("pb1610.SHF");
		result.add("pb1611.SHF");
		result.add("pb1612.SHF");
		
		result.add("rb1601.SHF");
		result.add("rb1602.SHF");
		result.add("rb1603.SHF");
		result.add("rb1604.SHF");
		result.add("rb1605.SHF");
		result.add("rb1606.SHF");
		result.add("rb1607.SHF");
		result.add("rb1608.SHF");
		result.add("rb1609.SHF");
		result.add("rb1610.SHF");
		result.add("rb1611.SHF");
		result.add("rb1612.SHF");
		
		result.add("ru1601.SHF");
		result.add("ru1602.SHF");
		result.add("ru1603.SHF");
		result.add("ru1604.SHF");
		result.add("ru1605.SHF");
		result.add("ru1606.SHF");
		result.add("ru1607.SHF");
		result.add("ru1608.SHF");
		result.add("ru1609.SHF");
		result.add("ru1610.SHF");
		result.add("ru1611.SHF");
		result.add("ru1612.SHF");
		
		result.add("sn1601.SHF");
		result.add("sn1602.SHF");
		result.add("sn1603.SHF");
		result.add("sn1604.SHF");
		result.add("sn1605.SHF");
		result.add("sn1606.SHF");
		result.add("sn1607.SHF");
		result.add("sn1608.SHF");
		result.add("sn1609.SHF");
		result.add("sn1610.SHF");
		result.add("sn1611.SHF");
		result.add("sn1612.SHF");
		
		result.add("wr1601.SHF");
		result.add("wr1602.SHF");
		result.add("wr1603.SHF");
		result.add("wr1604.SHF");
		result.add("wr1605.SHF");
		result.add("wr1606.SHF");
		result.add("wr1607.SHF");
		result.add("wr1608.SHF");
		result.add("wr1609.SHF");
		result.add("wr1610.SHF");
		result.add("wr1611.SHF");
		result.add("wr1612.SHF");
		
		result.add("zn1601.SHF");
		result.add("zn1602.SHF");
		result.add("zn1603.SHF");
		result.add("zn1604.SHF");
		result.add("zn1605.SHF");
		result.add("zn1606.SHF");
		result.add("zn1607.SHF");
		result.add("zn1608.SHF");
		result.add("zn1609.SHF");
		result.add("zn1610.SHF");
		result.add("zn1611.SHF");
		result.add("zn1612.SHF");
		
		return result;
	}
}
