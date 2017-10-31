package rmiclient;

import rmiserver.ChatServer;
import rmiserver.ChatServerIF;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatClient extends UnicastRemoteObject implements ChatClientIF,Runnable{
    private ChatServerIF chatServer;
    private String name=null;
    private String currentRoom="";
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
        String[] words;
        while (true){
            msg=scanner.nextLine();
            boolean clientSendCommand=false;
            try {
                words=msg.split(" ",2);
                if (msg.equals("jshowrooms"))
                {
                    msg=chatServer.ShowAllRooms();
                    clientSendCommand=true;
                }
                else if(words[0].equals("jcreate"))
                {
                    msg=chatServer.createRoom(words[1]);
                    clientSendCommand=true;
                }
                else if(words[0].equals("jjoin")){
                    boolean room_exist=false;
                    clientSendCommand=true;
                    msg=chatServer.joinRoom(words[1],this);
                }
                else if (words[0].equals("jswitch")){
                    this.currentRoom=words[1];
                    msg="you have switched to "+words[1];
                    clientSendCommand=true;
                }
                if (clientSendCommand){
                    System.out.println(msg);
                }
                else{
                    chatServer.broadcastMsgInRoom(this.currentRoom,msg);
                }
                //chatServer.broadcastMsg(this.name+":"+msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
