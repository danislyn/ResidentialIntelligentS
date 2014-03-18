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
			
			//����read�̣߳���������
			readingThread = new Thread(new ReadingThread());
			readingThread.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
