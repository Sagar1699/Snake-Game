import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Arrays;

public class Panel extends JPanel implements ActionListener {
   static int width= 1200;
    static int height = 600;
    static int unit = 50;
    boolean flag = false;
    int score =0;
     Timer timer;
     Random random;
     int fx, fy;
     int length = 3;
     char dir ='R';

     int xsnake[]= new int[288];
     int ysnake[]= new int[288];


    Panel(){
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        this.addKeyListener(new key());
        this.setFocusable(true);
        random = new Random();
        gamestart();
    }

    private void gamestart() {
        spawnfood();
        flag = true;
        timer = new Timer(160,this);
        timer.start();
    }

    private void spawnfood() {
        fx= random.nextInt(width/unit)*unit;
        fy= random.nextInt(height/unit)*unit;

    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics) {
        if(flag){
            graphics.setColor(Color.red);
            graphics.fillOval(fx, fy, 50, 50);

            for(int i=0;i<length;i++){
                graphics.setColor(Color.green);
                graphics.fillRect(xsnake[i],ysnake[i],50,50);

            }
            graphics.setColor(Color.cyan);
            graphics.setFont(new Font("Comic Sans", Font.BOLD, 40));
            FontMetrics fmt = getFontMetrics(graphics.getFont());
            graphics.drawString("Score:"+score,(width- fmt.stringWidth("Score:"+score))/2, graphics.getFont().getSize());
    }
        else{
            gameover(graphics);
        }
        }

    public void gameover(Graphics graphics) {
        graphics.setColor(Color.cyan);
        graphics.setFont(new Font("Comic Sans", Font.BOLD, 40));
        FontMetrics fmt = getFontMetrics(graphics.getFont());
        graphics.drawString("Score:"+score,(width- fmt.stringWidth("Score:"+score))/2, graphics.getFont().getSize());

        graphics.setColor(Color.red);
        graphics.setFont(new Font("Comic Sans", Font.BOLD, 80));
        fmt = getFontMetrics(graphics.getFont());
        graphics.drawString("GAME OVER",(width- fmt.stringWidth("GAME OVER"))/2, height/2);

        graphics.setColor(Color.green);
        graphics.setFont(new Font("Comic Sans", Font.BOLD, 40));
        fmt = getFontMetrics(graphics.getFont());
        graphics.drawString("Press R to replay",(width- fmt.stringWidth("Press R to replay"))/2, 3*height/4);
    }

    public void eat(){
        if((fx== xsnake[0]) && (fy== ysnake[0])){
            length++;
            score++;
            spawnfood();
        }
    }
    public void hit(){

        for(int i=length-1;i>0;i--){
            if((xsnake[0]== xsnake[i]) && (ysnake[0]==ysnake[i])){
                flag = false;
            }
        }
        if(xsnake[0]<0){
            flag=false;
        }
        else if(xsnake[0]>width){
            flag=false;
        }
        else if(ysnake[0]<0){
            flag=false;
        }
        else if(ysnake[0]>height){
            flag=false;
        }
        if(flag==false){
            timer.stop();
        }
    }

    public void move(){
        for(int i=length-1;i<0;i--){
            xsnake[i]=xsnake[i-1];
            ysnake[i]=ysnake[i-1];
        }
        switch(dir){
            case 'U':
                ysnake[0]= ysnake[0]-unit;
                break;
            case 'D':
                ysnake[0]= ysnake[0]+unit;
                break;
            case 'L':
                xsnake[0]= xsnake[0]-unit;
                break;
            case 'R':
                xsnake[0]= xsnake[0]+unit;
                break;
        }
    }

    public class key extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_UP:
                if(dir!='D'){
                    dir = 'U';
                }
                break;
                case KeyEvent.VK_DOWN:
                    if(dir!='U'){
                        dir = 'D';
                      }
                    break;
                case KeyEvent.VK_LEFT:
                    if(dir!='R'){
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir!='L'){
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_R:
                    if(flag==false){
                        score =0;
                        dir = 'R';
                        length=3;
                        Arrays.fill(xsnake ,0);
                        Arrays.fill(ysnake ,0);
                        gamestart();
                    }
                    break;

            }
        }
    }

    public void actionPerformed(ActionEvent e){
        if(flag){
            move();
            hit();
            eat();
        }
        repaint();
    }
}
