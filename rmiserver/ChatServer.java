package rmiserver;

import rmiclient.ChatClientIF;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ChatServer extends UnicastRemoteObject implements ChatServerIF {
    private ArrayList<ChatClientIF> chatClients;

    protected ChatServer() throws RemoteException{
        chatClients=new ArrayList<ChatClientIF>();
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
}
