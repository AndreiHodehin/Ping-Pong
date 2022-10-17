package game;

import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle {

    int xVelocity;
    int yVelocity;
    int initSpeedX = 4 ;
    int initSpeedY;
    Random random;

    public Ball(int x, int y, int width, int height) {

        super(x,y,width,height);

        //choose left or right direction of ball
        random = new Random();
        int randomXDir = random.nextInt(2);
        if(randomXDir == 0) {
            randomXDir--;}
        setXDir(randomXDir  * initSpeedX);

        //choose direction in Y coord
//        initSpeedY = random.nextInt(3,6);
        initSpeedY =  6 - (int) (Math.random() * 3);
        int randomYDir = random.nextInt(2);
        if(randomYDir == 0){
            randomYDir--;}
        setYDir(randomYDir  * initSpeedY);
    }

    public void setXDir(int randomXDir){
        xVelocity = randomXDir;
    }
    public void setYDir(int randomYDir){
        yVelocity = randomYDir;
    }

    public void move(){
        x += xVelocity;
        y += yVelocity;
    }
    public void draw(Graphics g){
        g.setColor(Color.lightGray);
        g.fillOval(x,y,width,height);
    }
}
