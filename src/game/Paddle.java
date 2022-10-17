package game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Paddle extends Rectangle {

    int yVelocity;
    int id;
    int speed = 10;

    Paddle(int x, int y, int width, int height,int id) {
        super(x,y,width,height);
        this.id = id;
    }

    public void draw(Graphics g){
        if(id == 1){
            g.setColor(Color.red);
        } else {
        g.setColor(Color.blue);}
        g.fillRect(x,y,width,height);
    }

    public void keyPressed(KeyEvent e) {
        switch (id) {
            case 1 :
                if(e.getKeyCode() == KeyEvent.VK_W){
                    setYDir(-speed);
                    move();
                }
                if(e.getKeyCode() == KeyEvent.VK_S){
                    setYDir(speed);
                    move();
                }
                break;

            case 2 :
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    setYDir(-speed);
                    move();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    setYDir(speed);
                    move();
                }
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (id) {
            case 1 :
                if(e.getKeyCode() == KeyEvent.VK_W){
                    setYDir(0);
                    move();
                }
                if(e.getKeyCode() == KeyEvent.VK_S){
                    setYDir(0);
                    move();
                }
                break;

            case 2 :
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    setYDir(0);
                    move();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    setYDir(0);
                    move();
                }
                break;
        }
    }
    private void setYDir(int yDir){
        yVelocity = yDir;
    }
    public void move(){
        y += yVelocity;
    }
    //for solo game
    public void move(int yCoord){
        y = (yCoord - 50);
    }

}
