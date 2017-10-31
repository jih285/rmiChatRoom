package rmiserver;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatServerDriver {

    public static void main(String[] args) throws RemoteException, MalformedURLException{
        Registry registry= LocateRegistry.createRegistry(8080);
        //registry.rebind("http://localhost:8080/Hello",stub);
        registry.rebind("rmi://localhost:8080/RMIChatServer",new ChatServer());
    }
}
