package game;

import java.awt.*;

public class Score extends Rectangle {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int player1,player2;

    public Score(int width, int height) {
        Score.GAME_WIDTH = width;
        Score.GAME_HEIGHT = height;
    }
    public void draw(Graphics g){
        g.setColor(Color.CYAN);
        g.setFont(new Font("TimesNewRoman",Font.PLAIN,50));

        g.drawLine(GAME_WIDTH/2,0,GAME_WIDTH/2,GAME_HEIGHT);
        g.drawString(player1 / 10 +String.valueOf(player1%10),GAME_WIDTH/2-85,50);
        g.drawString(player2 / 10 +String.valueOf(player2%10),GAME_WIDTH/2+30,50);
    }
}
