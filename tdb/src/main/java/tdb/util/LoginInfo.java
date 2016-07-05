package tdb.util;

import org.bridj.Pointer;

import tdbapi.OPEN_SETTINGS;
import tdbapi.TDBDefine_ResLogin;

public class LoginInfo {
	
	/*
		struct OPEN_SETTINGS
		{
		    char szIP[24];              //服务器ip地址
		    char szPort[8];             //服务器端口
		    char szUser[32];            //用户名
		    char szPassword[32];        //密码
		
		    unsigned int nTimeOutVal;   //获取数据时，指定网络超时（秒数，为0则设为2分钟），若超过nTimeOutVal秒后未收到回应数据包，则内部会关闭连接
		    unsigned int nRetryCount;   //获取数据时，若掉线，指定重连次数（为0则不重连），若重连nRetryCount次之后仍掉线，则返回网络错误
		    unsigned int nRetryGap;     //掉线之后重连的时间间隔（秒数，为0则设为1秒）
		};
	 */
	
	/**
	 * 获得  登录需要的 OPEN_SETTING 指针对象         这里可以设置其他的信息 ！！！！！！！！！！！！
	 * @param ip
	 * @param port
	 * @param user
	 * @param password
	 * @return
	 */
	public static Pointer<OPEN_SETTINGS> getOpsPointer(String ip,String port,String user,String password){
		
		Pointer<OPEN_SETTINGS> opsPointer = Pointer.allocate(OPEN_SETTINGS.class); //创建OPEN_SETTINGS类型的指针，方法对不对我也不知道，反正能用
		OPEN_SETTINGS ops =opsPointer.get(); //拿到OPEN_SETTING对象
		
		Pointer<Byte> ipPointer = ops.szIP(); //拿到szIP的指针对象，再设置ip
		byte[] ipBtyes = ip.getBytes();
		ipPointer.setArray(ipBtyes);
		
		Pointer<Byte> portPointer = ops.szPort();
		byte[] portBytes = port.getBytes();
		portPointer.setArray(portBytes);
		
		Pointer<Byte> userPointer = ops.szUser();
		byte[] userBytes = user.getBytes();
		userPointer.setArray(userBytes);
		
		Pointer<Byte> passwordPointer = ops.szPassword();
		byte[] passwordBytes = password.getBytes();
		passwordPointer.setArray(passwordBytes);
		
		ops.nTimeOutVal(300); //获取数据时，指定网络超时（秒数，为0则设为2分钟），若超过nTimeOutVal秒后未收到回应数据包，则内部会关闭连接
		ops.nRetryCount(100); //获取数据时，若掉线，指定重连次数（为0则不重连），若重连nRetryCount次之后仍掉线，则返回网络错误
		ops.nRetryGap(1); //掉线之后重连的时间间隔（秒数，为0则设为1秒）
		
		
		System.out.println(opsPointer);
		System.out.println(opsPointer.get().toString());
		System.out.println(opsPointer.get().szIP().toString());
		System.out.println(opsPointer.get().szIP().get(1).toString());
		
		return opsPointer;
	}
	
	/**
	 * 根据登录之后的结果 resLogin  获取账号对应的市场
	 * @param loginResPointer
	 * @return
	 */
	public static String[] getMarkets(Pointer<TDBDefine_ResLogin> loginResPointer){
		TDBDefine_ResLogin loginRes =  loginResPointer.get();
		int nMarkets = loginRes.nMarkets(); //支持市场个数
		System.out.println("支持市场个数: "+nMarkets);
		String[] result = new String[nMarkets];  //结果 支持市场的String[]数组
		byte[] szMarketByts = loginRes.szMarket().getBytes();
		for(int i=0;i<result.length;i++){
			byte[] tmp = new byte[24];
			for(int j=0;j<tmp.length;j++)
				tmp[j]=szMarketByts[i*24 + j];
			result[i] = new String(tmp).trim();
			System.out.println(result[i]);
		}
		return result;
	}
	
}
