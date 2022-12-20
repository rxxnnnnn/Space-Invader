import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Alien{
    public static final double ENEMY_SPEED = 0.5;
    public static final double ENEMY_VERTICAL_SPEED = 10.0;
    Image alien1 = new Image("images/enemy1.png");
    Image alien2 = new Image("images/enemy2.png");
    Image alien3 = new Image("images/enemy3.png");
    float alien_x, alien_y;
    ImageView alienview;
    int alienwidth = 65;
    int alienheight = 45;
    int mytype;
    int mylevel;
    float dx , dy;

    public Alien(Group group, int x, int y, int type, int level){
        mylevel=level;
        dx = (float) ENEMY_SPEED;
        dy = (float) ENEMY_VERTICAL_SPEED;
        if(mylevel==2){
            dx+=1;
            dy+=1;
        }
        if(mylevel==3){
            dx+=2.5;
            dy+=2.5;
        }
        mytype = type;
        if(type==1) alienview= new ImageView(alien1);
        if(type==2) alienview= new ImageView(alien2);
        if(type==3) alienview= new ImageView(alien3);
        alienview.setFitWidth(alienwidth);
        alienview.setFitHeight(alienheight);
        alien_x = x + 290;
        alien_y = y;
        alienview.setX(alien_x);
        alienview.setY(alien_y);
        group.getChildren().add(alienview);
    }


    public ImageView getview(){
        return alienview;
    }

    public void speedup(){
        if(this.dx<0) this.dx-=0.3;
        if(this.dx>0) this.dx+=0.3;
    }

}