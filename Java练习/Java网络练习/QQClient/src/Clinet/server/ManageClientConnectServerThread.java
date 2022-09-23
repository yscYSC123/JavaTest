<<<<<<< HEAD
package Clinet.server;

import java.util.HashMap;

public class ManageClientConnectServerThread {
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    public static void addClientConnectServerThread(String userId,ClientConnectServerThread clientConnectServerThread){
        hm.put(userId,clientConnectServerThread);
    }
    public static ClientConnectServerThread getClientConnectServerThread(String userId){
        return hm.get(userId);
    }
}
=======
package Clinet.server;

import java.util.HashMap;

public class ManageClientConnectServerThread {
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    public static void addClientConnectServerThread(String userId,ClientConnectServerThread clientConnectServerThread){
        hm.put(userId,clientConnectServerThread);
    }
    public static ClientConnectServerThread getClientConnectServerThread(String userId){
        return hm.get(userId);
    }
}
>>>>>>> 1ea652c4390070c06e33a6875f4c2e67f53bfd38
