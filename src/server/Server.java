package server;

import gui.MainPane;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	
	private ServerSocket serverSocket;

	private List<ServiceHandler> handlerList;
	private MainPane mainPane;
	
	public Server(MainPane panel){
		handlerList = new ArrayList<ServiceHandler>();
		mainPane = panel;
	}
	
	public void startService(){
		try {
			//��ʼ�������÷���˿�Ϊ8001
			serverSocket = new ServerSocket(8001);
			System.out.println("�������ѿ������ȴ��ͻ�������......");
			
//			while(true){
//				Socket socket = serverSocket.accept();
//				System.out.println(socket.getInetAddress().toString() + "������");
//				
//				//����һ��hander
//				handlerList.add(new ServiceHandler(socket, mainPane));
//			}
			
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						try {
							Socket socket = serverSocket.accept();
							System.out.println(socket.getInetAddress().toString() + "������");
							
							//����һ��hander
							handlerList.add(new ServiceHandler(socket, mainPane));
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeService(){
		for(ServiceHandler handler : handlerList){
			//֪ͨ�ر�
			//TODO
		}
	}
	
}
