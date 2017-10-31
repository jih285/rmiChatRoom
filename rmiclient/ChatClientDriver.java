package rmiclient;

import rmiserver.ChatServer;
import rmiserver.ChatServerIF;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatClientDriver {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        Registry registry = LocateRegistry.getRegistry(8080);
        ChatServerIF chatServer=(ChatServerIF) registry.lookup("rmi://localhost:8080/RMIChatServer");
        new Thread(new ChatClient("hoho",chatServer)).start();
    }
}
