package rmiclient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClientIF extends Remote {

    void retriveMsg(String msg) throws RemoteException;
}
