package server;

import gui.MainFrame;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import beans.Message;
import beans.MessageType;
import db.operation.MessageOperation;

public class ServiceHandler {
	
	private MainFrame mainFrame;

	private Socket client;
	private ObjectInputStream objInput;
	private ObjectOutputStream objOutput;
	
	private Thread readingThread;
	
	private String clientUsername;  //用于P2P，lazy fetch
	
	public ServiceHandler(Socket clientSocket, MainFrame frame){
		this.client = clientSocket;
		this.mainFrame = frame;
		
		try {
			objInput = new ObjectInputStream(client.getInputStream());
			objOutput = new ObjectOutputStream(client.getOutputStream());
			
			//启动read线程，接收请求
			readingThread = new Thread(new ReadingThread());
			readingThread.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//用于P2P启动前寻址
	public boolean match(String username){
		return clientUsername.equals(username);
	}
	
	
	//服务端主动关闭
	public void closeService(){
		//通知关闭
		//TODO
//		readingThread.stop();
		
		//发一个关闭消息给client
	}
	
	
	//Respond to client
	public void writeMessage(Message msg){
		try {
			objOutput.writeObject(msg);
			objOutput.flush();
			
			//save message
			MessageOperation.saveMessage(msg);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//客户端主动关闭时触发
	public void closeSelf(){
		
		
	}
	
	
	//内部类，处理client请求的主线程
	class ReadingThread implements Runnable{

		@Override
		public void run() {
			try {
				//从客户端读取的Message对象
				Message msg = null;
				
				while(true){
					msg = (Message) objInput.readObject();
					if(msg != null){
						if(msg.type == MessageType.ALARM){
							mainFrame.pushAlarmMessage(msg);
						}
						else if(msg.type == MessageType.CHAT){
							mainFrame.pushChatMessage(msg);
						}
						else if(msg.type == MessageType.LOGIN){
							clientUsername = msg.sourceUsername;
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
