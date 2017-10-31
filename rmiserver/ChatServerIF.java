package rmiserver;

import rmiclient.ChatClientIF;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServerIF extends Remote {
    void registerChatClient(ChatClientIF client) throws RemoteException;
    void broadcastMsg(String msg) throws RemoteException;
    void broadcastMsgInRoom(String room,String msg) throws RemoteException;
    String createRoom(String RoomName) throws RemoteException;
    String ShowAllRooms() throws RemoteException;
    String joinRoom(String RoomName,ChatClientIF client) throws RemoteException;
    String leaveRoom(String RoomName,ChatClientIF client) throws RemoteException;

}
