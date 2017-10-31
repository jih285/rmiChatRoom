package rmiserver;

import rmiclient.ChatClientIF;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServerIF extends Remote {
    void registerChatClient(ChatClientIF client) throws RemoteException;
    void broadcastMsg(String msg) throws RemoteException;
}
