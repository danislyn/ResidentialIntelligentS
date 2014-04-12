package server;

import gui.MainFrame;
import gui.MainPane;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import beans.Message;
import beans.MessageType;

public class ServiceHandler {
	
	private MainFrame mainFrame;

	private Socket client;
	private ObjectInputStream objInput;
	private ObjectOutputStream objOutput;
	
	private Thread readingThread;
	
	private String clientUsername;  //����P2P��lazy fetch
	
	public ServiceHandler(Socket clientSocket, MainFrame frame){
		this.client = clientSocket;
		this.mainFrame = frame;
		
		try {
			objInput = new ObjectInputStream(client.getInputStream());
			objOutput = new ObjectOutputStream(client.getOutputStream());
			
			//����read�̣߳���������
			readingThread = new Thread(new ReadingThread());
			readingThread.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//����P2P����ǰѰַ
	public boolean match(String username){
		return clientUsername.equals(username);
	}
	
	
	//����������ر�
	public void closeService(){
		//֪ͨ�ر�
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
	
	
	//�ͻ��������ر�ʱ����
	public void closeSelf(){
		
		
	}
	
	
	//�ڲ��࣬����client��������߳�
	class ReadingThread implements Runnable{

		@Override
		public void run() {
			try {
				//�ӿͻ��˶�ȡ��Message����
				Message msg = null;
				
				while(true){
					msg = (Message) objInput.readObject();
					if(msg != null){
						if(clientUsername == null){
							clientUsername = msg.sourceUsername;
						}
						
						//����msg
						if(msg.type == MessageType.ALARM){
							mainFrame.pushAlarmMessage(msg);
						}
						else if(msg.type == MessageType.CHAT){
							
							
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
