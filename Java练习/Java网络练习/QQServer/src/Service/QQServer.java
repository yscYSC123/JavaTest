<<<<<<< HEAD
package Service;

import common.Message;
import common.MessageType;
import common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class QQServer {
    private ServerSocket ss = null;
    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();
    static {
        validUsers.put("a",new User("a","1111"));
        validUsers.put("b",new User("b","2222"));
        validUsers.put("c",new User("c","3333"));
        validUsers.put("d",new User("d","4444"));
    }

    public static void main(String[] args) {
        new QQServer();
    }

    private boolean checkUser(String userId,String passwd){
        User user = validUsers.get(userId);
        if(user == null){
            return false;
        }
        if (!user.getPasswd().equals(passwd)){
            return false;
        }
        return true;
    }

    public QQServer(){
        try {
            System.out.println("服务端在9999端口监听");
            ss = new ServerSocket(9999);
            while (true){
                Socket socket = ss.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                User u = (User) ois.readObject();
                Message message = new Message();
                if (checkUser(u.getUserId(),u.getPasswd())){
                    message.setMessageType(MessageType.MESSAGE_LOGIN_SUCCESS);
                    oos.writeObject(message);
                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, u.getUserId());
                    serverConnectClientThread.start();
                    ManageClientThreads.addClientThread(u.getUserId(),serverConnectClientThread);
                }else{
                    System.out.println("用户："+u.getUserId()+" 密码："+u.getPasswd()+"\t验证失败");
                    message.setMessageType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);
                    socket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
=======
package Service;

import common.Message;
import common.MessageType;
import common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class QQServer {
    private ServerSocket ss = null;
    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();
    static {
        validUsers.put("a",new User("a","1111"));
        validUsers.put("b",new User("b","2222"));
        validUsers.put("c",new User("c","3333"));
        validUsers.put("d",new User("d","4444"));
    }

    public static void main(String[] args) {
        new QQServer();
    }

    private boolean checkUser(String userId,String passwd){
        User user = validUsers.get(userId);
        if(user == null){
            return false;
        }
        if (!user.getPasswd().equals(passwd)){
            return false;
        }
        return true;
    }

    public QQServer(){
        try {
            System.out.println("服务端在9999端口监听");
            ss = new ServerSocket(9999);
            while (true){
                Socket socket = ss.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                User u = (User) ois.readObject();
                Message message = new Message();
                if (checkUser(u.getUserId(),u.getPasswd())){
                    message.setMessageType(MessageType.MESSAGE_LOGIN_SUCCESS);
                    oos.writeObject(message);
                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, u.getUserId());
                    serverConnectClientThread.start();
                    ManageClientThreads.addClientThread(u.getUserId(),serverConnectClientThread);
                }else{
                    System.out.println("用户："+u.getUserId()+" 密码："+u.getPasswd()+"\t验证失败");
                    message.setMessageType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);
                    socket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
>>>>>>> 1ea652c4390070c06e33a6875f4c2e67f53bfd38
