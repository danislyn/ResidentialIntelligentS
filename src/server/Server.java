package server;

import gui.InstantChatFrame;
import gui.MainFrame;
import gui.MainPane;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	
	private ServerSocket serverSocket;

	private List<ServiceHandler> handlerList;
	private MainFrame mainFrame;
	
	public Server(MainFrame frame){
		handlerList = new ArrayList<ServiceHandler>();
		mainFrame = frame;
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
	
	public void startP2P(String username){
		for(ServiceHandler handler : handlerList){
			if(handler.match(username)){
				InstantChatFrame chatFrame = new InstantChatFrame();
				chatFrame.setVisible(true);
			}
		}
	}
	
}
