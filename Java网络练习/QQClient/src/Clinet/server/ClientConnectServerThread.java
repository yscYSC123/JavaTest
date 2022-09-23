package Clinet.server;

import common.Message;
import common.MessageType;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread{
    private Socket socket;

    public  ClientConnectServerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true){
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                if(message.getMessageType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)){
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("\n=======当前在线用户列表=======");
                    for (int i = 0; i < onlineUsers.length; i++) {
                        System.out.println("用户："+onlineUsers[i]);
                    }
                }else if (message.getMessageType().equals(MessageType.MESSAGE_COMM_MES)) {
                    System.out.println("\n"+"私聊信息\n"+message.getSender()+" 对 "+message.getGetter()+" 说 "+message.getContent());
                }else if (message.getMessageType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    System.out.println("\n"+"群发信息\n"+message.getSender()+" 对大家说 "+message.getContent());
                }else if (message.getMessageType().equals(MessageType.MESSAGE_FILE_MES)) {
                    System.out.println("\n"+message.getGetter()+" 收到 "+message.getSender()+" 发送的文件 "+message.getSrc()+" 到 "+message.getDest());
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDest());
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.close();
                    System.out.println("\n保持文件成功");
                }else {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public Socket getSocket() {
        return socket;
    }

}
