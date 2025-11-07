package entity;

import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;
import java.awt.Color;

public class Snake {

    private int body_length;
    private List<Segment> segments;
    private char direction; // 'U' = UP, 'D' = DOWN, 'L' = LEFT, 'R' = RIGHT

    public Snake(){
        segments = new ArrayList<>();
        initializeSnake();
    }

    public void initializeSnake(){
        direction = 'R';
        segments.add(new Segment(1,3)); //init position of head
        segments.add(new Segment(1,2)); //init position of body
        segments.add(new Segment(1,1)); //init position of tail
    }

    public void move(){
        int headX = segments.get(0).getX();
        int headY = segments.get(0).getY();

        //Assuming 0,0 is the top left corner
        switch (direction) {
            case 'U': headY--; break; //head will go up, because the limit on top is considered to be y=0
            case 'D': headY++; break;
            case 'L': headX--; break;
            case 'R': headX++; break;
        }

        //Move every other body and tail segments
        for (int i = segments.size() - 1; i > 0; i--) {
            segments.get(i).set_Position(segments.get(i - 1).getX(), segments.get(i - 1).getY());
        }

        //Update head with its new position
        segments.get(0).set_Position(headX, headY);

    }

    public void setDirection(char newDirection) {
        //Check if new direction is opposite of current direction
        if ((direction == 'U' && newDirection == 'D') ||
                (direction == 'D' && newDirection == 'U') ||
                (direction == 'L' && newDirection == 'R') ||
                (direction == 'R' && newDirection == 'L')) {
            return; //Ignore new direction if it is opposite of current direction
        }
        direction = newDirection; //Updates direction if it is valid
    }

    public void grow(){
        //Get tail's position
        Segment tail = segments.get(segments.size() - 1);
        int tailX = tail.getX();
        int tailY = tail.getY();

        segments.add(new Segment(tailX, tailY));
    }

    public void rendering(Graphics g, int unitSize){
        for (int i = 0; i < segments.size(); i++) {
            Segment s = segments.get(i);
            g.fillOval(s.getX() * unitSize, s.getY() * unitSize, unitSize, unitSize);
        }
    }
}
