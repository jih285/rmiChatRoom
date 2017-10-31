package rmiclient;

import rmiserver.ChatServer;
import rmiserver.ChatServerIF;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatClient extends UnicastRemoteObject implements ChatClientIF,Runnable{
    private ChatServerIF chatServer;
    private String name=null;
    protected ChatClient(String name, ChatServerIF chatServer) throws RemoteException{
        this.name=name;
        this.chatServer=chatServer;
        chatServer.registerChatClient(this);
    }
    @Override
    public void retriveMsg(String msg) throws RemoteException {
        System.out.println(msg);
    }
    public  void  run(){
        Scanner scanner=new Scanner(System.in);
        String msg;
        while (true){
            msg=scanner.nextLine();
            try {
                chatServer.broadcastMsg(this.name+":"+msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
