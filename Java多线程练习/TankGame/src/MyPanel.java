import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener ,Runnable{
    //定义我的坦克
    Hero hero = null;
    //定义敌人坦克
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义一个存放Node对象的Vector，用于恢复坦克的坐标和方向
    Vector<Node> nodes = new Vector<>();
    //爆炸效果
    Vector<Bomb> bombs = new Vector<>();
    int enemyTanksSize = 4;
    //定义3张炸弹图片
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    public MyPanel(String key){
        nodes = Recorder.getNodesAndEnemyTankRec();
        //将MyPanel的enemyTanks设置给Record的enemyTanks
        Recorder.setEnemyTanks(enemyTanks);
        hero = new Hero(60,60);//初始化自己的坦克坐标
        hero.setSpeed(4);
        switch (key){
            case "1":
                //初始化敌人的坦克
                for (int i = 0; i < enemyTanksSize; i++) {
                    EnemyTank enemyTank = new EnemyTank(200 * (i + 1), 0);
                    enemyTank.setEnemyTanks(enemyTanks);
                    //方向
                    enemyTank.setDirect(2);
                    new Thread(enemyTank).start();
                    //给坦克加子弹
                    Shot shot = new Shot(enemyTank.getX()+20,enemyTank.getY()+60,enemyTank.getDirect());
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2":
                //初始化敌人的坦克
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    enemyTank.setEnemyTanks(enemyTanks);
                    //方向
                    enemyTank.setDirect(node.getDirect());
                    new Thread(enemyTank).start();
                    //给坦克加子弹
                    Shot shot = new Shot(enemyTank.getX()+20,enemyTank.getY()+60,enemyTank.getDirect());
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("输入有误");
                break;
        }

        //初始化图片
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/1.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/2.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/3.png"));

        //音乐
        new AePlayWave("src\\a.wav").start();
    }

    public void showInfo(Graphics g){
        //画出玩家总成绩
        g.setColor(Color.BLACK);
        Font font = new Font("宋体",Font.BOLD,25);
        g.setFont(font);
        g.drawString("累计击毁的坦克数：",1020,30);
        drawTank(1020,60,g,0,0);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum()+"",1080,100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        showInfo(g);
        g.fillRect(0,0,1000,750);//填充矩形，默认黑色
        //自己的坦克
        if(hero!=null&&hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);
        }
        //子弹
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isLive == true){
                g.draw3DRect(shot.x,shot.y,3,3,false);
            }else {
                hero.shots.remove(shot);
            }
        }
        //爆炸
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if(bomb.life > 6){
                g.drawImage(image3, bomb.x, bomb.y,60,60,this);
            }else if(bomb.life > 3){
                g.drawImage(image2, bomb.x, bomb.y,60,60,this);
            }else {
                g.drawImage(image1, bomb.x, bomb.y,60,60,this);
            }
            bomb.liveDown();
            //生命结束移出集合
            if(bomb.life == 0){
                bombs.remove(bomb);
            }
        }
        //敌人的坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive) { //判断坦克是否存活
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    if (shot.isLive) {
                        g.draw3DRect(shot.x, shot.y, 3, 3, false);
                    } else {
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }
    }
    //坦克
    /**
     *
     * @param x 坦克左上角x坐标
     * @param y 坦克右下角y坐标
     * @param g 画笔
     * @param direct 坦克方向
     * @param type 坦克类型
     */
    public void drawTank(int x,int y,Graphics g,int direct,int type){
        //判断坦克类型，设置坦克颜色
        switch (type){
            case 0://自己的坦克
                g.setColor(Color.cyan);
                break;
            case 1://敌人的坦克
                g.setColor(Color.red);
                break;
        }
        //根据坦克方向绘制坦克
        switch (direct){
            case 0://坦克向上
                g.fill3DRect(x,y,10,60,false);//坦克左轮
                g.fill3DRect(x+30,y,10,60,false);//坦克右轮
                g.fill3DRect(x+10,y+10,20,40,false);//坦克中间
                g.fillOval(x+10,y+20,20,20);//坦克中间
                g.drawLine(x+20,y+30,x+20,y);//坦克炮管
                break;
            case 1://坦克向右
                g.fill3DRect(x,y,60,10,false);//坦克左轮
                g.fill3DRect(x,y+30,60,10,false);//坦克右轮
                g.fill3DRect(x+10,y+10,40,20,false);//坦克中间
                g.fillOval(x+20,y+10,20,20);//坦克中间
                g.drawLine(x+30,y+20,x+60,y+20);//坦克炮管
                break;
            case 2://坦克向下
                g.fill3DRect(x,y,10,60,false);//坦克左轮
                g.fill3DRect(x+30,y,10,60,false);//坦克右轮
                g.fill3DRect(x+10,y+10,20,40,false);//坦克中间
                g.fillOval(x+10,y+20,20,20);//坦克中间
                g.drawLine(x+20,y+30,x+20,y+60);//坦克炮管
                break;
            case 3://坦克向左
                g.fill3DRect(x,y,60,10,false);//坦克左轮
                g.fill3DRect(x,y+30,60,10,false);//坦克右轮
                g.fill3DRect(x+10,y+10,40,20,false);//坦克中间
                g.fillOval(x+20,y+10,20,20);//坦克中间
                g.drawLine(x+30,y+20,x,y+20);//坦克炮管
                break;
        }
    }

    public void hitEnemyTank(){
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot!=null&&shot.isLive) {
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }

    public void hitHero(){
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                if(hero.isLive && shot.isLive){
                    hitTank(shot,hero);
                }
            }
        }
    }

    public void hitTank(Shot s,Tank enemyTank){
        switch (enemyTank.getDirect()) {
            case 0:
            case 1:
            case 2:
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 40 && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    enemyTanks.remove(enemyTank);
                    if(enemyTank instanceof EnemyTank){
                        Recorder.addEnemyTankNum();
                    }
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
            case 3:
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 60 && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 40) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    enemyTanks.remove(enemyTank);
                    if(enemyTank instanceof EnemyTank){
                        Recorder.addEnemyTankNum();
                    }
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    //处理wdsa
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            hero.setDirect(0);
            if (hero.getY()>0) {
                hero.moveUp();
            }
        }else if(e.getKeyCode() == KeyEvent.VK_D){
            hero.setDirect(1);
            if (hero.getX()+80<1000) {
                hero.moveRigth();
            }
        }else if(e.getKeyCode() == KeyEvent.VK_S){
            hero.setDirect(2);
            if (hero.getY()+100<750) {
                hero.moveDown();
            }
        }else if(e.getKeyCode() == KeyEvent.VK_A){
            hero.setDirect(3);
            if (hero.getX()>0) {
                hero.moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_J){
                hero.shotEnemyTank();
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() { ///每隔100ms重绘区域
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hitEnemyTank();
            hitHero();
            this.repaint();
        }
    }
}
