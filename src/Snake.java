import java.awt.*;
import java.nio.channels.Pipe;
import java.util.ArrayList;

public class Snake {
    private ArrayList<Point> snakeBody; //儲存Snake的身體(每個節點)

    public Snake(){
        snakeBody = new ArrayList<Point>();
        snakeBody.add(new Point(80, 0));
        snakeBody.add(new Point(60, 0));
        snakeBody.add(new Point(40, 0));
        snakeBody.add(new Point(20, 0));
    }

    public ArrayList<Point> getSnakeBody(){
        return snakeBody;
    }

    //繪出貪吃蛇本體
    public void drawSnake(Graphics g){
        for(int i = 0; i < snakeBody.size(); i++){
            if(i == 0){
                g.setColor(Color.RED);
            }
            else {
                g.setColor(Color.pink);
            }

            Point p = snakeBody.get(i);
            if(p.x >= GamePanel.panelWidth){
                p.x = 0;
            }
            if(p.x < 0){
                p.x = GamePanel.panelWidth - GamePanel.unit_size;
            }
            if(p.y >= GamePanel.panelHeight){
                p.y = 0;
            }
            if(p.y < 0){
                p.y = GamePanel.panelHeight - GamePanel.unit_size;
            }
            g.fillOval(p.x, p.y, GamePanel.unit_size, GamePanel.unit_size); //實心圓
        }
    }
}
