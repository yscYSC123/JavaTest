package Clinet.view;

import Clinet.server.FileClientService;
import Clinet.server.MessageClientService;
import Clinet.server.UserClientService;
import Clinet.untils.Utility;

public class QQView {
    public static void main(String[] args) {
        new QQView().mainMenu();
    }

    private boolean loop = true;
    private String key = "";
    private UserClientService userClientService = new UserClientService();
    private MessageClientService messageClientService = new MessageClientService();
    private FileClientService fileClientService = new FileClientService();

    private void mainMenu(){
        while (loop){
            System.out.println("=======欢迎登录网络通信系统=======");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 9 退出系统");
            System.out.print("请输入（1或9）：");

            key = Utility.readString(1);
            switch (key){
                case "1":
                    System.out.print("请输入用户名：");
                    String userId = Utility.readString(15);
                    System.out.print("请输入密  码：");
                    String pwd= Utility.readString(15);

                    if(userClientService.checkUser(userId,pwd)){
                        System.out.println("=======欢迎 "+ userId + " 使用网络通信系统=======");
                        while (loop) {
                            System.out.println("=======网络通信系统菜单=======");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择：");
                            key = Utility.readString(1);
                            switch (key){
                                case "1":
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.print("请输入想对大家说的话：");
                                    String s = Utility.readString(100);
                                    messageClientService.sendMessageToAll(s,userId);
                                    break;
                                case "3":
                                    System.out.print("请输入想聊天的用户号（在线）：");
                                    String getterId = Utility.readString(50);
                                    System.out.print("请输入想说的话：");
                                    String content = Utility.readString(100);
                                    messageClientService.sendMessageToOne(content,userId,getterId);
                                    break;
                                case "4":
                                    System.out.print("请输入想发送文件的用户号（在线）：");
                                    getterId = Utility.readString(50);
                                    System.out.println("请输入发送文件的路径：");
                                    String src = Utility.readString(100);
                                    System.out.println("请接收文件的路径：");
                                    String dest = Utility.readString(100);
                                    fileClientService.sendFileToOne(src,dest,userId,getterId);
                                    break;
                                case "9":
                                    userClientService.logout();
                                    loop = false;
                                    break;
                                default:
                                    System.out.println("输入错误，请重新输入");
                                    break;
                            }
                        }
                    }else {
                        System.out.println("登录失败");
                    }
                    break;
                case "9":
                    loop = false;
                    System.out.println("退出系统");
                    break;
                default:
                    System.out.println("输入错误，请重新输入");
                    break;
            }
        }
    }
}
