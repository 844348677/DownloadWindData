package tdb.util;

import org.bridj.Pointer;

import tdbapi.OPEN_SETTINGS;
import tdbapi.TDBDefine_ResLogin;

public class LoginInfo {
	
	/*
		struct OPEN_SETTINGS
		{
		    char szIP[24];              //������ip��ַ
		    char szPort[8];             //�������˿�
		    char szUser[32];            //�û���
		    char szPassword[32];        //����
		
		    unsigned int nTimeOutVal;   //��ȡ����ʱ��ָ�����糬ʱ��������Ϊ0����Ϊ2���ӣ���������nTimeOutVal���δ�յ���Ӧ���ݰ������ڲ���ر�����
		    unsigned int nRetryCount;   //��ȡ����ʱ�������ߣ�ָ������������Ϊ0����������������nRetryCount��֮���Ե��ߣ��򷵻��������
		    unsigned int nRetryGap;     //����֮��������ʱ������������Ϊ0����Ϊ1�룩
		};
	 */
	
	/**
	 * ���  ��¼��Ҫ�� OPEN_SETTING ָ�����         �������������������Ϣ ������������������������
	 * @param ip
	 * @param port
	 * @param user
	 * @param password
	 * @return
	 */
	public static Pointer<OPEN_SETTINGS> getOpsPointer(String ip,String port,String user,String password){
		
		Pointer<OPEN_SETTINGS> opsPointer = Pointer.allocate(OPEN_SETTINGS.class); //����OPEN_SETTINGS���͵�ָ�룬�����Բ�����Ҳ��֪������������
		OPEN_SETTINGS ops =opsPointer.get(); //�õ�OPEN_SETTING����
		
		Pointer<Byte> ipPointer = ops.szIP(); //�õ�szIP��ָ�����������ip
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
		
		ops.nTimeOutVal(300); //��ȡ����ʱ��ָ�����糬ʱ��������Ϊ0����Ϊ2���ӣ���������nTimeOutVal���δ�յ���Ӧ���ݰ������ڲ���ر�����
		ops.nRetryCount(100); //��ȡ����ʱ�������ߣ�ָ������������Ϊ0����������������nRetryCount��֮���Ե��ߣ��򷵻��������
		ops.nRetryGap(1); //����֮��������ʱ������������Ϊ0����Ϊ1�룩
		
		
		System.out.println(opsPointer);
		System.out.println(opsPointer.get().toString());
		System.out.println(opsPointer.get().szIP().toString());
		System.out.println(opsPointer.get().szIP().get(1).toString());
		
		return opsPointer;
	}
	
	/**
	 * ���ݵ�¼֮��Ľ�� resLogin  ��ȡ�˺Ŷ�Ӧ���г�
	 * @param loginResPointer
	 * @return
	 */
	public static String[] getMarkets(Pointer<TDBDefine_ResLogin> loginResPointer){
		TDBDefine_ResLogin loginRes =  loginResPointer.get();
		int nMarkets = loginRes.nMarkets(); //֧���г�����
		System.out.println("֧���г�����: "+nMarkets);
		String[] result = new String[nMarkets];  //��� ֧���г���String[]����
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
