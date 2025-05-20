import javax.swing.*;
public class GameFrame extends JFrame {
    int frameWidth = 615;
    int frameHeight = 640;

    public GameFrame(){
        this.add(new GamePanel()); //添加面板
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); //預設關閉視窗動作
        this.setSize(frameWidth, frameHeight); //設置大小
        this.setTitle("Snake Game");
        this.setResizable(false); //無法更改視窗大小
        this.setVisible(true);// 使視窗可見
    }
}
