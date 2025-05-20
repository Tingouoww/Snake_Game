import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

public class GamePanel extends JPanel implements KeyListener {
    public static int panelWidth = 600;
    public static int panelHeight = 600;
    public static int unit_size = 20;
    public static int row = (panelHeight / unit_size) - 1;
    public static int column = (panelWidth / unit_size) - 1;

    private Snake snake;
    private Food food;
    private Timer timer;
    private int speed = 150; //ms
    private static String direction;
    private boolean allowKeyPress;
    private int score;
    private int highest_score;
    String desktop = System.getProperty("user.home") + "/Desktop/";
    String myFile = desktop + "filename.txt";

    public GamePanel(){
        read_highest_score();
        reset();

        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        addKeyListener(this);
        allowKeyPress = true;
    }

    private void reset(){
        score = 0;
        if(snake != null){
            snake.getSnakeBody().clear();
        }
        allowKeyPress = true;
        direction = "right";
        snake = new Snake();
        food = new Food();
        setTimer();
    }

    private void setTimer(){
        timer = new Timer();

        // 在每個固定時間，Timer執行某件事情
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0, speed);
    }

    //自訂繪畫內容
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //確認是否會咬到自己
        ArrayList<Point> snake_body = snake.getSnakeBody();
        Point head = snake_body.get(0);
        for(int i = 1; i < snake_body.size(); i++){
            if(snake_body.get(i).x == head.x && snake_body.get(i).y == head.y){
                allowKeyPress = false;
                timer.cancel();
                timer.purge();
                int resoponse = JOptionPane.showOptionDialog(this, "Game Over! Your score is "+ score +".\nWould you like to start again?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,null, null, JOptionPane.YES_OPTION);
                write_a_file(score);
                switch (resoponse){
                    case JOptionPane.CLOSED_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.NO_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.YES_OPTION:
                        reset();
                        return;
                }
            }
        }
        //System.out.println("TESTING");
        g.setColor(Color.gray);
        g.fillRect(0, 0, panelWidth, panelHeight); //畫方形
        g.setColor(Color.black);

        for (int i = 0; i < panelHeight / unit_size; i++) {
            g.drawLine(0, i * unit_size, panelWidth, i * unit_size); // 水平線
        }
        for (int i = 0; i < panelWidth / unit_size; i++) {
            g.drawLine(i * unit_size, 0, i * unit_size, panelHeight); // 垂直線
        }
        snake.drawSnake(g);
        food.drawFood(g);

        //移除貪吃蛇尾巴接至攤吃蛇頭部
        int snakeX = snake.getSnakeBody().get(0).x;
        int snakeY = snake.getSnakeBody().get(0).y;
        if(direction.equals("left")){
            snakeX -= unit_size;
        } else if (direction.equals("up")) {
            snakeY -= unit_size;
        } else if (direction.equals("right")) {
            snakeX += unit_size;
        } else if(direction.equals("down")) {
            snakeY += unit_size;
        }
        Point newHead = new Point(snakeX, snakeY);

        //確認貪吃蛇是否有吃到食物
        if(snake.getSnakeBody().get(0).x == food.getX() && snake.getSnakeBody().get(0).y == food.getY()){
            //吃到食物
            score++;
            int snakeTailX = snake.getSnakeBody().get(snake.getSnakeBody().size() - 1).x;
            int snakeTailY = snake.getSnakeBody().get(snake.getSnakeBody().size() - 1).y;
            Point tailP = new Point(snakeTailX, snakeTailY);
            snake.getSnakeBody().add(snake.getSnakeBody().size(), tailP);

            food.setNewLocation(snake);
        }

        snake.getSnakeBody().remove(snake.getSnakeBody().size() - 1);
        snake.getSnakeBody().add(0, newHead);

        allowKeyPress = true;
        requestFocusInWindow();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(allowKeyPress){
            if(e.getKeyCode() == 40 && !Objects.equals(direction, "up")){
                direction = "down";
            } else if (e.getKeyCode() == 39 && !direction.equals("left")) {
                direction = "right";
            } else if (e.getKeyCode() == 38 && !direction.equals("down")) {
                direction = "up";
            } else if (e.getKeyCode() == 37 && !direction.equals("right")) {
                direction = "left";
            }
            allowKeyPress = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void read_highest_score(){
        try {
            File myObj = new File(myFile);
            Scanner myReader = new Scanner(myObj);
            if(myReader.hasNext()){
                highest_score = myReader.nextInt();
            }
            myReader.close();
        }catch (FileNotFoundException e){
            highest_score = 0;
            try {
                File myObj = new File(myFile);
                if(myObj.createNewFile()){
                    System.out.println("file created: " + myObj.getName());
                }
                FileWriter myWriter = new FileWriter(myObj.getName());
                myWriter.write("" + 0);
                myWriter.close();
            }catch (IOException err){
                System.out.println("An error occurred");
                err.printStackTrace();
            }

        }
    }

    public void write_a_file(int score){
        try{
            FileWriter myWriter = new FileWriter(myFile);
            if(score > highest_score){
                myWriter.write("" + score);
                highest_score = score;
            }else{
                myWriter.write("" + highest_score);
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
