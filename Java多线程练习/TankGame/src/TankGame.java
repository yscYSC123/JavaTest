import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class TankGame extends JFrame {
    //定义MyPanel
    MyPanel mp = null;
     static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        TankGame tankGame = new TankGame();
    }
    public TankGame(){
        System.out.println("请输入选择 1：新游戏 2：继续上局");
        String key = sc.next();
        mp = new MyPanel(key);
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//面板
        this.addKeyListener(mp);
        this.setSize(1350,750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();//监听退出按钮
                System.exit(0);
            }
        });
    }
}
