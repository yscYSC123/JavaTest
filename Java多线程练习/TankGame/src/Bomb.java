<<<<<<< HEAD
public class Bomb {
    int x,y;
    int life = 9;//炸弹生命周期
    boolean isLife = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void liveDown(){
        if (life > 0){
            life --;
        }else {
            isLife = false;
        }
    }
}
=======
public class Bomb {
    int x,y;
    int life = 9;//炸弹生命周期
    boolean isLife = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void liveDown(){
        if (life > 0){
            life --;
        }else {
            isLife = false;
        }
    }
}
>>>>>>> 1ea652c4390070c06e33a6875f4c2e67f53bfd38
