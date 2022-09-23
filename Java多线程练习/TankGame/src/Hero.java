import sun.awt.windows.ThemeReader;

import javax.swing.plaf.TableHeaderUI;
import java.util.Vector;

public class Hero extends Tank{
    Shot shot = null;//表示一个射击行为
    //多颗子弹
    Vector<Shot> shots = new Vector<>();
    public Hero(int x, int y) {
        super(x, y);
    }
    public void shotEnemyTank(){
        if(shots.size() == 6)
            return;
        switch (getDirect()){
            case 0:
                shot = new Shot(getX()+20,getY(),0);
                break;
            case 1:
                shot = new Shot(getX()+60,getY()+20,1);
                break;
            case 2:
                shot = new Shot(getX()+20,getY()+60,2);
                break;
            case 3:
                shot = new Shot(getX(),getY()+20,3);
                break;
        }
        shots.add(shot);
        new Thread(shot).start();
    }
}
