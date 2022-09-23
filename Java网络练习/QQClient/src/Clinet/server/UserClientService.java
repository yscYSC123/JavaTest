package Clinet.server;

import common.Message;
import common.MessageType;
import common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;

public class UserClientService {
    private User u = new User();
    private Socket socket;

    public boolean checkUser(String userId,String pwd) {
        boolean flag = false;
        u.setUserId(userId);
        u.setPasswd(pwd);
        try {
            socket = new Socket(InetAddress.getByName("192.168.80.1"), 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();

            if (ms.getMessageType().equals(MessageType.MESSAGE_LOGIN_SUCCESS)){
                ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);
                clientConnectServerThread.start();
                ManageClientConnectServerThread.addClientConnectServerThread(userId,clientConnectServerThread);
                flag = true;
            }else {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    public void onlineFriendList(){
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(u.getUserId());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void logout(){
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getUserId());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            System.out.println("用户 "+u.getUserId()+" 退出系统");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
