package server;

import gui.InstantChatFrame;
import gui.MainFrame;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import beans.Message;

public class Server {
	
	private ServerSocket serverSocket;

	private List<ServiceHandler> handlerList;
	private MainFrame mainFrame;
	
	private List<InstantChatFrame> chatList;
	
	public Server(MainFrame frame){
		handlerList = new ArrayList<ServiceHandler>();
		mainFrame = frame;
		chatList = new ArrayList<InstantChatFrame>();
	}
	
	public void startService(){
		try {
			//��ʼ�������÷���˿�Ϊ8001
			serverSocket = new ServerSocket(8001);
			System.out.println("�������ѿ������ȴ��ͻ�������......");
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						try {
							Socket socket = serverSocket.accept();
							System.out.println(socket.getInetAddress().toString() + "������");
							
							//����һ��hander
							handlerList.add(new ServiceHandler(socket, mainFrame));
							
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
	
	
	//============================================
	private InstantChatFrame getChatFrame(String toUsername){
		for(InstantChatFrame chatFrame : chatList){
			if(chatFrame.getToUsername().equals(toUsername)){
				return chatFrame;
			}
		}
		return null;
	}
	
	public InstantChatFrame startP2P(String username){
		//�ȴ����д�������
		InstantChatFrame aliveFrame = getChatFrame(username);
		if(aliveFrame != null){
			aliveFrame.setVisible(true);
			return aliveFrame;
		}
		
		//���������촰��
		for(ServiceHandler handler : handlerList){
			if(handler.match(username)){
				InstantChatFrame chatFrame = new InstantChatFrame(username, handler);
				chatFrame.setVisible(true);
				
				chatList.add(chatFrame);
				return chatFrame;
			}
		}
		
		//��usernameƥ��
		return null;
	}
	
	public void pushChatMessage(Message msg){
		InstantChatFrame chatFrame = startP2P(msg.sourceUsername);
		if(chatFrame != null){
			chatFrame.refreshContent(msg);
		}
	}
	
}
