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
	 * 根据市场信息，获取所有的CodeInfo，放到List里面  同事过滤掉仿真
	 */
	public static List<CodeInfo> getCodes(Pointer<Pointer<TDBDefine_Code>> pCodeTable,int pCount){
		List<CodeInfo> resultList = new LinkedList<CodeInfo>();
		System.out.println("万得代码(AG1312.SHF),交易所代码(ag1312),市场代码(SHF),证券中文名称,证券英文名称");
		for(int i=0;i<pCount;i++){
			TDBDefine_Code codeInfo = pCodeTable.get().get(i);
			String chMarket = new String(codeInfo.chMarket().getBytes()).trim(); //市场代码(SHF)		
			if(!chMarket.contains("仿真")){  //过滤掉 仿真
				CodeInfo codeObject = new CodeInfo(); //没有id，没有marketid。
				String chCNName = new String(codeInfo.chCNName().getBytes()).trim();    //证券中文名称
				String chWindCode = new String(codeInfo.chWindCode().getBytes()).trim(); //万得代码(AG1312.SHF)
				String chCode = new String(codeInfo.chCode().getBytes()).trim(); //交易所代码(ag1312)
	
				String chENName = new String(codeInfo.chENName().getBytes()).trim();     //证券英文名称
				int nType = codeInfo.nType();  //证券类型
				
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
	 * 获取种类的list
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
	 * 根据CodeList 构造CodeDetail，有market代买 ，品种list，正常的code代码，不正常的code代码
	 * @param codeList
	 * @return
	 */
	public static CodeDetail getCodeDetailByList(List<CodeInfo> codeList,String market){
		CodeDetail result = new CodeDetail(); //返回结果
		result.setMarket(market); //市场market代码
		Set<String> resultSet = new HashSet<String>(); //品种set
		List<CodeInfo> normalCodeList = new LinkedList<>();
		List<CodeInfo> unNormalCodeList = new LinkedList<>();
		
		for(int i=0;i<codeList.size();i++){
			CodeInfo codeInfo = codeList.get(i);
			String code = codeInfo.getCode();
			if(DateUtil.isNormalCode(code)){ //根据code代码的字符串，判断是不是正常的 code
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
	
	public static List<String> get50CodeList(){
		List<String> result = new LinkedList<>();
		result.add("600000.SH");
		result.add("600010.SH");
		result.add("600015.SH");
		result.add("600016.SH");
		result.add("600018.SH");
		result.add("600028.SH");
		result.add("600030.SH");
		result.add("600036.SH");
		result.add("600048.SH");
		result.add("600050.SH");
		result.add("600104.SH");
		result.add("600109.SH");
		result.add("600111.SH");
		result.add("600150.SH");
		result.add("600518.SH");
		result.add("600519.SH");
		result.add("600585.SH");
		result.add("600637.SH");
		result.add("600795.SH");
		result.add("600837.SH");
		result.add("600887.SH");
		result.add("600893.SH");
		result.add("600958.SH");
		result.add("600999.SH");
		result.add("601006.SH");
		result.add("601088.SH");
		result.add("601166.SH");
		result.add("601169.SH");
		result.add("601186.SH");
		result.add("601211.SH");
		result.add("601288.SH");
		result.add("601318.SH");
		result.add("601328.SH");
		result.add("601336.SH");
		result.add("601390.SH");
		result.add("601398.SH");
		result.add("601601.SH");
		result.add("601628.SH");
		result.add("601668.SH");
		result.add("601669.SH");
		result.add("601688.SH");
		result.add("601766.SH");
		result.add("601800.SH");
		result.add("601818.SH");
		result.add("601857.SH");
		result.add("601901.SH");
		result.add("601985.SH");
		result.add("601988.SH");
		result.add("601989.SH");
		result.add("601998.SH");
		
		return result;
	}
	public static List<String> getMoreCodeList(){
		List<String> result = new LinkedList<>();
		result.add("510050.SH");
		result.add("999987.SH");
		
		return result;
	}
	
	public static List<String> getIHCodeList(){
		List<String> result = new LinkedList<>();
		result.add("IH1404.CF");
		result.add("IH1405.CF");
		result.add("IH1406.CF");
		result.add("IH1407.CF");
		result.add("IH1408.CF");
		result.add("IH1409.CF");
		result.add("IH1410.CF");
		result.add("IH1411.CF");
		result.add("IH1412.CF");
		result.add("IH1501.CF");
		result.add("IH1503.CF");
		result.add("IH1504.CF");
		result.add("IH1505.CF");
		result.add("IH1505-S.CF");
		result.add("IH1506.CF");
		result.add("IH1506-S.CF");
		result.add("IH1507.CF");
		result.add("IH1508.CF");
		result.add("IH1509.CF");
		result.add("IH1509-S.CF");
		result.add("IH1510.CF");
		result.add("IH1511.CF");
		result.add("IH1512.CF");
		result.add("IH1601.CF");
		result.add("IH1602.CF");
		result.add("IH1603.CF");
		result.add("IH1604.CF");
		result.add("IH1605.CF");
		result.add("IH1606.CF");
		result.add("IH1607.CF");
		result.add("IH1609.CF");
		result.add("IH1612.CF");
		return result;
	}

	public static List<String> getIFCodeList(List<CodeInfo> codeList){
		List<String> result = new LinkedList<>();
		for(int i=0;i<codeList.size();i++){
			String codeName = codeList.get(i).getWindcode();
			if(codeName.startsWith("IF"))
				result.add(codeName);
		}
		return result;
	}

	public static List<String> getUpdateSHCodeList(){
		List<String> result = new LinkedList<>();
/*		result.add("000300.SH");
		result.add("600000.SH");
		result.add("600010.SH");
		result.add("600015.SH");
		result.add("600016.SH");
		result.add("600018.SH");
		result.add("600028.SH");
		result.add("600030.SH");
		result.add("600036.SH");
		result.add("600048.SH");
		result.add("600050.SH");
		result.add("600104.SH");
		result.add("600109.SH");
		result.add("600111.SH");
		result.add("600150.SH");
		result.add("600518.SH");
		result.add("600519.SH");*/
		result.add("600585.SH");
		result.add("600637.SH");
		result.add("600795.SH");
		result.add("600837.SH");
		result.add("600887.SH");
		result.add("600893.SH");
		result.add("600958.SH");
		result.add("600999.SH");
		result.add("601006.SH");
		result.add("601088.SH");
		result.add("601166.SH");
		result.add("601169.SH");
		result.add("601186.SH");
		result.add("601211.SH");
		result.add("601288.SH");
		result.add("601318.SH");
		result.add("601328.SH");
		result.add("601336.SH");
		result.add("601390.SH");
		result.add("601398.SH");
		result.add("601601.SH");
		result.add("601628.SH");
		result.add("601668.SH");
		result.add("601669.SH");
		result.add("601688.SH");
		result.add("601766.SH");
		result.add("601800.SH");
		result.add("601818.SH");
		result.add("601857.SH");
		result.add("601901.SH");
		result.add("601985.SH");
		result.add("601988.SH");
		result.add("601989.SH");
		result.add("601998.SH");
		result.add("510050.SH");
		result.add("999987.SH");
		result.add("600590.SH");
		return result;
	}
	
	public static List<String> getUpdateCFCodeList(){
		List<String> result = new LinkedList<>();
		result.add("IF1601.CF");
		result.add("IF1602.CF");
		result.add("IF1603.CF");
		result.add("IF1604.CF");
		result.add("IF1605.CF");
		result.add("IF1606.CF");
		result.add("IF1607.CF");
		result.add("IF1608.CF");
		result.add("IF1609.CF");
		result.add("IF1610.CF");
		result.add("IF1611.CF");
		result.add("IF1612.CF");
		result.add("CFIFC1.CF");
		result.add("CFIFC2.CF");
		result.add("CFIFC3.CF");
		result.add("CFIFC4.CF");
		result.add("CFIFCC.CF");
		
		return result;
	}
	
}
