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
			//初始化，设置服务端口为8001
			serverSocket = new ServerSocket(8001);
			System.out.println("服务器已开启，等待客户端连接......");
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						try {
							Socket socket = serverSocket.accept();
							System.out.println(socket.getInetAddress().toString() + "已连接");
							
							//启动一个hander
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
			//通知关闭
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
		//先从已有窗口里找
		InstantChatFrame aliveFrame = getChatFrame(username);
		if(aliveFrame != null){
			aliveFrame.setVisible(true);
			return aliveFrame;
		}
		
		//创建新聊天窗口
		for(ServiceHandler handler : handlerList){
			if(handler.match(username)){
				InstantChatFrame chatFrame = new InstantChatFrame(username, handler);
				chatFrame.setVisible(true);
				
				chatList.add(chatFrame);
				return chatFrame;
			}
		}
		
		//无username匹配
		return null;
	}
	
	public void pushChatMessage(Message msg){
		InstantChatFrame chatFrame = startP2P(msg.sourceUsername);
		if(chatFrame != null){
			chatFrame.refreshContent(msg);
		}
	}
	
}
