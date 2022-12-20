import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ship{
    int width = 1280;
    int smallwidth = 70;
    int smallheight = 45;
    public static final double PLAYER_SPEED = 6.0;
    Image ship = new Image("images/player.png");
    float ship_x;
    ImageView shipview;
    int mylevel;

    public Ship(int x, int y, int level){
        mylevel = level;
        shipview= new ImageView(ship);
        shipview.setFitWidth(70);
        shipview.setFitHeight(45);
        ship_x = x-35;
        shipview.setX(ship_x);
        shipview.setY(y-smallheight);
    }

    //animate
    public void move_right(){
        if(shipview!=null) {
            ship_x += PLAYER_SPEED + (mylevel - 1) * 2;
            if (ship_x <= width - smallwidth) {
                shipview.setX(ship_x);
            } else ship_x = width - smallwidth;
        }
    };

    public void move_left(){
        if(shipview!=null) {
            ship_x -= PLAYER_SPEED + (mylevel - 1) * 2;
            if (ship_x >= 0) {
                shipview.setX(ship_x);
            } else ship_x = 0;
        }
    };

    public float getShip_x(){
        float v = ship_x - smallwidth / 2;
        return v;
    }

}