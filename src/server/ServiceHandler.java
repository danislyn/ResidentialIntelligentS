package server;

import gui.MainPane;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import beans.Message;

public class ServiceHandler {
	
	private MainPane mainPane;

	private Socket client;
	private ObjectInputStream objInput;
	private ObjectOutputStream objOutput;
	
	private Thread readingThread;
	
	public ServiceHandler(Socket clientSocket, MainPane mainPane){
		this.client = clientSocket;
		this.mainPane = mainPane;
		
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
	
	
	//服务端主动关闭
	public void closeService(){
		//通知关闭
		//TODO
//		readingThread.stop();
	}
	
	
	//Respond to client
	public void writeMessage(Message msg){
		try {
			objOutput.writeObject(msg);
			objOutput.flush();
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
//						System.out.println(msg.toString());
						
						mainPane.refreshMessageList(msg);
						
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
