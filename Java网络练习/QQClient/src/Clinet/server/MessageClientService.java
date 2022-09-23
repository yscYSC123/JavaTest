<<<<<<< HEAD
package Clinet.server;

import common.Message;
import common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class MessageClientService {

    public void sendMessageToAll(String content,String senderId){
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_TO_ALL_MES);
        message.setSender(senderId);
        message.setContent(content);
        message.setSendTime(new java.util.Date().toString());
        System.out.println("群发信息");
        System.out.println(senderId+" 对大家说 "+content);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToOne(String content,String senderId,String getterId){
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_COMM_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setContent(content);
        message.setSendTime(new java.util.Date().toString());
        System.out.println("私聊信息");
        System.out.println(senderId+" 对 "+getterId+" 说 "+content);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
=======
package Clinet.server;

import common.Message;
import common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class MessageClientService {

    public void sendMessageToAll(String content,String senderId){
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_TO_ALL_MES);
        message.setSender(senderId);
        message.setContent(content);
        message.setSendTime(new java.util.Date().toString());
        System.out.println("群发信息");
        System.out.println(senderId+" 对大家说 "+content);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToOne(String content,String senderId,String getterId){
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_COMM_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setContent(content);
        message.setSendTime(new java.util.Date().toString());
        System.out.println("私聊信息");
        System.out.println(senderId+" 对 "+getterId+" 说 "+content);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
>>>>>>> 1ea652c4390070c06e33a6875f4c2e67f53bfd38
