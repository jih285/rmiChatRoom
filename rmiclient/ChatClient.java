package rmiclient;

import rmiserver.ChatServer;
import rmiserver.ChatServerIF;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatClient extends UnicastRemoteObject implements ChatClientIF,Runnable{
    private ChatServerIF chatServer;
    private String name=null;
    private String currentRoom;
    private ArrayList<String>myRooms;
    protected ChatClient(ChatServerIF chatServer) throws RemoteException{
        this.name="";
        this.chatServer=chatServer;
        chatServer.registerChatClient(this);
        this.currentRoom="";
        this.myRooms=new ArrayList<String>();

    }
    @Override
    public void retriveMsg(String msg) throws RemoteException {
        System.out.println(msg);
    }
    public  void  run(){
        Scanner scanner=new Scanner(System.in);
        String msg;
        String[] words;

        System.out.println("System: please enter your name");
        this.name=scanner.nextLine();
        System.out.println("System: Hello "+this.name+"! welcome to ji's chat lobby!");
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
                    String[] res=msg.split(" ",2);
                    if(res[0].equals("you")) {
                        this.myRooms.add(words[1]);
                    }
                }
                else if (words[0].equals("jswitch")){
                    boolean joinedRoom=false;
                    for (int i=0;i<this.myRooms.size();i++){
                        if(myRooms.get(i).equals(words[1])){
                            this.currentRoom=words[1];
                            msg="you have switched to "+words[1];
                            joinedRoom=true;
                        }
                    }
                    if (!joinedRoom){
                        msg="you should join this room first";
                    }
                    clientSendCommand=true;
                }
                if (clientSendCommand){
                    System.out.println("System: "+msg);
                }
                else{
                    if (!this.currentRoom.equals("")) {
                        msg = this.name + " from[" + this.currentRoom + "] said: " + msg;
                        chatServer.broadcastMsgInRoom(this.currentRoom, msg);
                    }
                    else {
                        System.out.println("System: Please switch to a room, or enter a valid command");
                    }
                }
                //chatServer.broadcastMsg(this.name+":"+msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
