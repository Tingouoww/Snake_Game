import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Food {
    private int x;
    private int y;
    private ImageIcon img;

    public Food(){
        //img = new ImageIcon("out/production/Snake_Game/fruit.png");
        img = new ImageIcon(getClass().getResource("fruit.png"));
        this.x = (int) Math.floor(Math.random() * GamePanel.column) * GamePanel.unit_size;
        this.y = (int) Math.floor(Math.random() * GamePanel.row) * GamePanel.unit_size;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void drawFood(Graphics g){
        img.paintIcon(null, g, this.x, this.y);
    }

    public void setNewLocation(Snake s){
        int new_x;
        int new_y;
        boolean overlapping;
        do{
            new_x = (int) (Math.floor(Math.random() * GamePanel.column) * GamePanel.unit_size);
            new_y = (int) (Math.floor(Math.random() * GamePanel.row) * GamePanel.unit_size);
            overlapping = check_overlapping(new_x, new_y, s);
        }while (overlapping);

        this.x = new_x;
        this.y = new_y;
    }

    //查看是否與Snake位置重疊
    private boolean check_overlapping(int x, int y, Snake s){
        ArrayList<Point> snake_body = s.getSnakeBody();
        for(int j = 0; j < s.getSnakeBody().size(); j++){
            if(x == snake_body.get(j).x && y == snake_body.get(j).y){
                return true;
            }
        }
        return false;
    }
}
