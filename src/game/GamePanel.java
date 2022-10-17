package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GamePanel extends JPanel implements Runnable{

    Logger logger;
    final boolean SOLO_GAME ;
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int) (GAME_WIDTH * 0.5555);
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 18;
    static final int PADDLE_HEIGHT = 100;
    static int numOfTouchBallAndPaddle = 0;
    Image image;
    Graphics graphics;
    Ball ball;
    Paddle paddle1,paddle2;
    Thread gameThread;
    Score score;
    Random random;
    public GamePanel(boolean soloGame){
        this.SOLO_GAME = soloGame;

        logger = Logger.getLogger("logger");
        random = new Random();
        score = new Score(GAME_WIDTH,GAME_HEIGHT);

        newBall();
        newPaddle();

        this.setFocusable(true);
        this.addKeyListener(new ActionListener());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newBall(){
        ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),(GAME_HEIGHT/2)-(BALL_DIAMETER/2),BALL_DIAMETER,BALL_DIAMETER);
    }

    public void newPaddle(){
        paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
        paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);
    }

    public void paint(Graphics g){
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
    }

    public void draw(Graphics g){
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
    }

    public void move(){
        ball.move();
        paddle1.move();
        //for solo game
        if(SOLO_GAME){paddle2.move(ball.y);}
        else {paddle2.move();}
    }

    public void checkCollision() {

        //collision  paddles with border
        if(paddle1.y <= 0) {
            paddle1.y = 0;
        }
        if(paddle1.y >= GAME_HEIGHT-PADDLE_HEIGHT){
            paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
        }

        if(paddle2.y <= 0) {
            paddle2.y = 0;
        }
        if(paddle2.y >= GAME_HEIGHT-PADDLE_HEIGHT){
            paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;
        }

        //collision of ball upper and lower bounds
        if(ball.y <= 0){
            ball.setYDir(-ball.yVelocity);

            //random change ySpeed but not less than 5 and more than 11
            int num = random.nextInt(2);
            if(num == 0 || Math.abs(ball.yVelocity) < 5){
                ball.yVelocity++;} else {ball.yVelocity--;}
            if(ball.yVelocity > 11){
                ball.yVelocity = 11;
            }
            logger.log(Level.INFO,"Upper bound yVelocity " + ball.yVelocity);
        }

        if(ball.y >= GAME_HEIGHT-BALL_DIAMETER){
            ball.setYDir(-ball.yVelocity);

            //random change ySpeed but not less than 4 and more than 11
            int num = random.nextInt(2);
            if(num == 0 || Math.abs(ball.yVelocity) < 5){
                ball.yVelocity--;} else {ball.yVelocity++;}
            if(ball.yVelocity > 11){
                ball.yVelocity = 11;
            }
            logger.log(Level.INFO,"lower bound yVelocity " + ball.yVelocity);
        }

        //collision ball and paddle.
        if(ball.intersects(paddle1)){

            //increases speed by one every 4 touches 7 times
            increaseSpeedByNumOfTouch();

            ball.setXDir(ball.xVelocity);
            ball.setYDir(ball.yVelocity);
        }

        if(ball.intersects(paddle2)){
            //increases speed by one every 4 touches 7 times
            increaseSpeedByNumOfTouch();

            ball.setXDir(-ball.xVelocity);
            ball.setYDir(ball.yVelocity);
        }

        //collision of defending area
        if(ball.x <= 0){
            numOfTouchBallAndPaddle = 0;
            score.player2++;
            newBall();
            newPaddle();
        }

        if(ball.x >= GAME_WIDTH){
            numOfTouchBallAndPaddle = 0;
            score.player1++;
            newBall();
            newPaddle();
        }
    }

    private void increaseSpeedByNumOfTouch() {
        numOfTouchBallAndPaddle++;
        ball.xVelocity = Math.abs(ball.xVelocity);
        if(numOfTouchBallAndPaddle <= 28)
            if(numOfTouchBallAndPaddle%4 == 0){
                ball.xVelocity++;
            }
        logger.log(Level.INFO,"Tuchs: " + numOfTouchBallAndPaddle + " Xspeed: " + ball.xVelocity + " Yspeed: " + ball.yVelocity);
    }

    @Override
    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1_000_000_000 / amountOfTicks;
        double delta = 0;
        while (true){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }
    public class ActionListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}
