package tdb.save;

import java.io.IOException;
import java.util.List;

import org.bridj.Pointer;
import org.hibernate.Query;
import org.hibernate.Session;

import tdb.domain.CodeInfo;
import tdb.util.HibernateSessionFactory;
import tdb.util.PropertiesUtil;
import tdbapi.TDBDefine_Code;

public class SaveCodeTable {
	public static void saveCodeTable(Pointer<Pointer<TDBDefine_Code>> pCodeTable,int pCount,String marketName){
		
		long marketID = Long.parseLong(PropertiesUtil.GetValueByKey("resources/marketID.properties", marketName));
		long lastNumber = Long.parseLong(PropertiesUtil.GetValueByKey("resources/lastCode.properties", marketName));
		// 获取Session
		Session session = HibernateSessionFactory.currentSession(); //sf.openSession();
		// 开启事务
		session.beginTransaction();
		
		long id = lastNumber+1;
		
		for(int i=0;i<pCount;i++){
			TDBDefine_Code code = pCodeTable.get().get(i);
			CodeInfo codeInfo = new CodeInfo();
			
			String chWindCode = new String(code.chWindCode().getBytes()).trim(); //万得代码(AG1312.SHF)
			String chCode = new String(code.chCode().getBytes()).trim(); //交易所代码(ag1312)
			String chMarket = new String(code.chMarket().getBytes()).trim(); //市场代码(SHF)
			String chCNName = new String(code.chCNName().getBytes()).trim();    //证券中文名称
			String chENName = new String(code.chENName().getBytes()).trim();     //证券英文名称
			int nType = code.nType();  //证券类型
			
			codeInfo.setId(id+i);
			codeInfo.setWindcode(chWindCode);
			codeInfo.setCode(chCode);
			codeInfo.setMarket(chMarket);
			codeInfo.setCnname(chCNName);
			codeInfo.setEnname(chENName);
			codeInfo.setNtype(nType);
			codeInfo.setMarketid(1000+marketID);
			System.out.println(marketID+" : "+i+" : "+codeInfo.toString());
			session.save(codeInfo);
		}
		try {
			PropertiesUtil.WriteProperties("resources/lastCode.properties", marketName, (lastNumber + pCount)+"");
		} catch (IOException e) {
			System.out.println("写入lastCode，中对应的markerNamer的最后一个number，的时候抛出异常");
			e.printStackTrace();
		}
		
		
		// 提交事务
		session.getTransaction().commit();

		// 关闭连接
		session.close();
	}
	
	public static void saveCodeTableByString(Pointer<Pointer<TDBDefine_Code>> pCodeTable,int pCount,String marketName){
		

		long lastNumber = Long.parseLong(PropertiesUtil.GetValueByKey("resources/lastCode.properties", marketName));
		String saveTableName = "";
		if("CF-2-0".equals(marketName))
			saveTableName = "CODEINFOCF";
		if("SH-2-0".equals(marketName))
			saveTableName = "CODEINFOSH";
		if("SZ-2-0".equals(marketName))
			saveTableName = "CODEINFOSZ";
		// 获取Session
		Session session = HibernateSessionFactory.currentSession(); //sf.openSession();
		// 开启事务
		session.beginTransaction();
		
		long id = lastNumber+1;
		
		for(int i=0;i<pCount;i++){
			TDBDefine_Code code = pCodeTable.get().get(i);
			
			long chId = id+i;
			String chWindCode = "'"+new String(code.chWindCode().getBytes()).trim()+"'"; //万得代码(AG1312.SHF)
			String chCode = "'"+new String(code.chCode().getBytes()).trim()+"'"; //交易所代码(ag1312)
			String chMarket = "'"+new String(code.chMarket().getBytes()).trim()+"'"; //市场代码(SHF)
			String chCNName = "'"+new String(code.chCNName().getBytes()).trim()+"'";    //证券中文名称
			String chENName = "'"+new String(code.chENName().getBytes()).trim()+"'";     //证券英文名称
			int nType = code.nType();  //证券类型
			

			String insertSQL = "INSERT INTO "+saveTableName+" (ID,WINDCODE,CODE,MARKET,CNNAME,ENNAME,NTYPE) VALUES "+"("+chId+","+chWindCode+","+chCode+","+chMarket+","+chCNName+","+chENName+","+nType+")";
			
			Query queryCreate = session.createSQLQuery(insertSQL);
			queryCreate.executeUpdate();
		}
		try {
			PropertiesUtil.WriteProperties("resources/lastCode.properties", marketName, (lastNumber + pCount)+"");
		} catch (IOException e) {
			System.out.println("写入lastCode，中对应的markerNamer的最后一个number，的时候抛出异常");
			e.printStackTrace();
		}
		
		// 提交事务
		session.getTransaction().commit();

		// 关闭连接
		session.close();
	}
}
