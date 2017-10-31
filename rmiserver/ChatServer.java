package rmiserver;

import rmiclient.ChatClientIF;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
public class ChatServer extends UnicastRemoteObject implements ChatServerIF {
    private ArrayList<ChatClientIF> chatClients;
    private ArrayList<room> rooms;

    protected ChatServer() throws RemoteException{
        chatClients=new ArrayList<ChatClientIF>();
        rooms=new ArrayList<room>();
    }

    @Override
    public void registerChatClient(ChatClientIF client) throws RemoteException {
        this.chatClients.add(client);
    }

    @Override
    public void broadcastMsg(String msg) throws RemoteException {
        for (int i=0;i<chatClients.size();i++){
            chatClients.get(i).retriveMsg(msg);
        }
    }
    @Override
    public void broadcastMsgInRoom(String room,String msg) throws RemoteException {

        if (rooms.size()!=0) {
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getRoom_name().equals(room)) {
                    rooms.get(i).chatlog+=msg+"\n";
                    for (int j = 0; j < rooms.get(i).size(); j++) {
                        rooms.get(i).getMember(j).retriveMsg(msg);
                    }
                    break;
                }
            }
        }
    }

    @Override
    public String createRoom(String RoomName) throws RemoteException {
        boolean roomexist=false;
        String msg="";
        //boolean clientSendCommand=true;
        if (rooms.size()!=0) {
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).getRoom_name().equals(RoomName))
                    roomexist = true;
            }
        }
        if (!roomexist)
        {
            rooms.add(new room(RoomName));
            System.out.println("[room:" + RoomName + "] has been created");
            msg = "[room:" + RoomName + "] has been created";
        }
        else
        {
            msg ="[room: "+RoomName+"] has been created, please choose another name";
        }

        return msg;
    }
    public String ShowAllRooms()
    {
        if (rooms.size()==0)
        {
            return "Currently, there is no chat room in this server, please create one by yourself. Thank you";
        }
        String CurrentRooms="room list: ";
        for (int i=0;i<rooms.size();i++)
        {
            CurrentRooms+=(i+1)+". "+rooms.get(i).getRoom_name()+" ";
        }
        return CurrentRooms;
    }

    @Override
    public String joinRoom(String RoomName, ChatClientIF client) throws RemoteException {
        String msg="";
        boolean room_exist=false;
        for(int i=0;i<rooms.size();i++)
        {
            String chatHistory="";
            if (rooms.get(i).getRoom_name().equals(RoomName))
            {
                System.out.println("user joined chat room "+RoomName);
                rooms.get(i).add_member(client);
                if (!rooms.get(i).chatlog.equals(""))
                    chatHistory=rooms.get(i).chatlog+"\n---------------------------------------chat history--------------------------------------------";
                msg="you have join in chat room "+RoomName+"\n"+chatHistory;
                client.retriveMsg(chatHistory);
                room_exist=true;
            }
        }
        if (!room_exist) {
            msg = "Chat room does not exist!";
        }
        return msg;
    }

    public String leaveRoom(String RoomName,ChatClientIF client) throws RemoteException{
        boolean roomFound=false;
        for(int i=0;i<rooms.size();i++)
        {
            if (rooms.get(i).getRoom_name().equals(RoomName))
            {
                System.out.println("user joined chat room "+RoomName);
                rooms.get(i).remove_member(client);
                roomFound=true;
            }
        }
        if (roomFound) {
            return "you have left room: " + RoomName;
        }
        else {
            return "Error! Room exist in client-end, but not in server-end";
        }
    }
}
