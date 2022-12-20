import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.Random;

import static javafx.scene.paint.Color.BLACK;


public class SpaceInvader extends Application{
    //Speeds
    public static final double PLAYER_BULLET_SPEED = 13.0;
    public static final double ENEMY1_BULLET_SPEED = 3.0;
    public static final double ENEMY2_BULLET_SPEED = 4.0;
    public static final double ENEMY3_BULLET_SPEED = 5.0;

    //Sizes
    int width = 1280;
    int height = 800;
    int smallwidth = 65;
    int smallheight = 45;
    int starty = 50;
    int score = 0;
    //numbers
    Label hiscore;
    int lives = 3;
    Label liveslable;
    int level = 1;
    Label levellable;
    //scenes
    Group end;
    Group group1;
    Group group2;
    Group group3;
    Scene level1;
    Scene level2;
    Scene level3;
    Scene endscene;
    Scene introduction;
    //sounds
    AudioClip shoot;
    AudioClip playerdies;
    AudioClip alinedies;
    AudioClip move1;
    AudioClip move2;
    AudioClip move3;
    AudioClip move4;

    ArrayList<Alien> aliens1 = new ArrayList();
    ArrayList<Alien> aliens2 = new ArrayList();
    ArrayList<Alien> aliens3 = new ArrayList();
    Ship player;

    @Override
    public void start(Stage stage){
        stage.setResizable(false);

        //init sounds
        String shoots = getClass().getClassLoader().getResource("sounds/shoot.wav").toString();
        shoot = new AudioClip(shoots);
        String playerdiess = getClass().getClassLoader().getResource("sounds/explosion.wav").toString();
        playerdies = new AudioClip(playerdiess);
        String alinediess = getClass().getClassLoader().getResource("sounds/invaderkilled.wav").toString();
        alinedies = new AudioClip(alinediess);
        String move1s = getClass().getClassLoader().getResource("sounds/fastinvader1.wav").toString();
        move1 = new AudioClip(move1s);
        String move2s = getClass().getClassLoader().getResource("sounds/fastinvader2.wav").toString();
        move2 = new AudioClip(move2s);
        String move3s = getClass().getClassLoader().getResource("sounds/fastinvader3.wav").toString();
        move3 = new AudioClip(move3s);
        String move4s = getClass().getClassLoader().getResource("sounds/fastinvader4.wav").toString();
        move4 = new AudioClip(move4s);


        //init score
        hiscore = new Label("Score: "+score);
        hiscore.setTextFill(Color.WHITE);
        hiscore.setFont(Font.font("Verdana",FontWeight.BOLD,25));
        hiscore.setTranslateX(110);
        hiscore.setTranslateY(10);


        //init live
        liveslable = new Label("Lives: "+lives);
        liveslable.setTextFill(Color.WHITE);
        liveslable.setFont(Font.font("Verdana",FontWeight.BOLD,25));
        liveslable.setTranslateX(880);
        liveslable.setTranslateY(10);

        //init level
        levellable = new Label("Level: "+level);
        levellable.setTextFill(Color.WHITE);
        levellable.setFont(Font.font("Verdana",FontWeight.BOLD,25));
        levellable.setTranslateX(1030);
        levellable.setTranslateY(10);

        // introduction scene
        Image logo = new Image("images/logo.png");
        ImageView logoview= new ImageView(logo);
        Label text1 = new Label("Instructions");
        text1.setFont(Font.font("Verdana",FontWeight.BOLD,50));
        Label text2 = new Label("ENTER - Start Game");
        Label text3 = new Label("A or ←, D or → - Move ship left or right");
        Label text4 = new Label("SPACE - Fire!");
        Label text5 = new Label("Q - Quit Game");
        Label text6 = new Label("1 or 2 or 3 - Start Game at specific level");
        Label text7 = new Label("Implemented by Ruoxuan Wang for CS349, University of Waterloo, W20");
        text2.setFont(Font.font(15));
        text3.setFont(Font.font(15));
        text4.setFont(Font.font(15));
        text5.setFont(Font.font(15));
        text6.setFont(Font.font(15));
        text7.setFont(Font.font(12));
        VBox box = new VBox(logoview, text1, text2, text3, text4, text5, text6,text7);
        box.setAlignment(Pos.BASELINE_CENTER);
        box.setMargin(logoview, new Insets(5));
        box.setMargin(text1, new Insets(55));
        box.setMargin(text2, new Insets(20));
        box.setMargin(text3, new Insets(3));
        box.setMargin(text4, new Insets(3));
        box.setMargin(text5, new Insets(3));
        box.setMargin(text6, new Insets(3));
        box.setMargin(text7, new Insets(110));
        introduction = new Scene(box, width, height);

        // level scenes

        group1 = new Group();
        group2 = new Group();
        group3 = new Group();
        level1 = new Scene(group1, width, height, BLACK);
        level2 = new Scene(group2, width, height, BLACK);
        level3 = new Scene(group3, width, height, BLACK);
        player = new Ship(width/2, height,level);
        //ship animate
        AnimationTimer timer1 = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (player.shipview==null||player.shipview.getParent()==null||lives==0) this.stop();
                player.move_right();
                if (player.shipview==null||player.shipview.getParent()==null||lives==0) this.stop();
            }
        };
        AnimationTimer timer2 = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (player.shipview==null||player.shipview.getParent()==null||lives==0) this.stop();
                player.move_left();
                if (player.shipview==null||player.shipview.getParent()==null||lives==0) this.stop();
            }
        };



        //key handler
        for(int i = 0 ; i < 3; i++) {
            Scene levelscene;
            Group g;
            ArrayList<Alien> aliens;
            if (i==0) levelscene = level1;
            else if (i==1) levelscene = level2;
            else levelscene = level3;

            if (i==0) g = group1;
            else if (i==1) g = group2;
            else g = group3;

            if (i==0) aliens = aliens1;
            else if (i==1) aliens = aliens2;
            else aliens = aliens3;

            //press
            levelscene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                boolean first = true;
                long lastUpdate;
                long now;
                @Override
                public void handle(KeyEvent event) {
                    now = System.currentTimeMillis();
                    if(first) lastUpdate = now;
                    //move player
                    if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) timer1.start();
                    if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) timer2.start();
                    //bullet from player
                    if (event.getCode() == KeyCode.SPACE) {
                        if (first || now - lastUpdate >= 500) {
                            if (first) first = false;
                            lastUpdate = now;
                            ImageView playerbullet = new ImageView("images/player_bullet.png");
                            playerbullet.setFitWidth(10);
                            playerbullet.setFitHeight(40);
                            playerbullet.setX(player.getShip_x() + 65);
                            playerbullet.setY(height - smallheight - 30);
                            g.getChildren().add(playerbullet);
                            shoot.play();
                            AnimationTimer timer = new AnimationTimer() {
                                @Override
                                public void handle(long now) {
                                    if (aliens.size() == 0 || lives < 0) this.stop();
                                    boolean check = move_playerbullet(playerbullet, g, stage, this, aliens);
                                    if (!check) this.stop();
                                }
                            };
                            timer.start();
                        }
                    }
                }
            });
            //release
            levelscene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) timer1.stop();
                    if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) timer2.stop();
                }
            });
        }


        introduction.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()==KeyCode.ENTER || event.getCode()==KeyCode.DIGIT1){
                    stage.setScene(level1);
                    init_group(1,stage, group1, aliens1);
                    if(player.shipview==null){
                        timer1.stop();
                        timer2.stop();
                    }
                }
                if(event.getCode()==KeyCode.DIGIT2){
                    stage.setScene(level2);
                    init_group(2,stage,group2, aliens2);
                    if(player.shipview==null){
                        timer1.stop();
                        timer2.stop();
                    }
                }
                if(event.getCode()==KeyCode.DIGIT3){
                    stage.setScene(level3);
                    init_group(3,stage,group3, aliens3);
                    if(player.shipview==null){
                        timer1.stop();
                        timer2.stop();
                    }
                }
                if(event.getCode()==KeyCode.Q){
                    stage.close();
                }
            }
        });

        //end scene
        end = new Group();
        endscene = new Scene(end, width, height, BLACK);


        endscene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()==KeyCode.ENTER || event.getCode()==KeyCode.DIGIT1){
                    score=0;
                    lives=3;
                    liveslable.setText("Lives: "+lives);
                    hiscore.setText("Score: "+score);
                    stage.setScene(level1);
                    player.shipview=null;
                    init_group(1,stage, group1, aliens1);
                    if(player.shipview==null){
                        timer1.stop();
                        timer2.stop();
                    }
                }
                if(event.getCode()==KeyCode.DIGIT2){
                    score=0;
                    lives=3;
                    liveslable.setText("Lives: "+lives);
                    hiscore.setText("Score: "+score);
                    stage.setScene(level2);
                    player.shipview=null;
                    init_group(2,stage,group2, aliens2);
                    if(player.shipview==null){
                        timer1.stop();
                        timer2.stop();
                    }
                }
                if(event.getCode()==KeyCode.DIGIT3){
                    score=0;
                    lives=3;
                    liveslable.setText("Lives: "+lives);
                    hiscore.setText("Score: "+score);
                    stage.setScene(level3);
                    player.shipview=null;
                    init_group(3,stage,group3, aliens3);
                    if(player.shipview==null){
                        timer1.stop();
                        timer2.stop();
                    }
                }
                if(event.getCode()==KeyCode.I){
                    score=0;
                    lives=3;
                    liveslable.setText("Lives: "+lives);
                    hiscore.setText("Score: "+score);
                    stage.setScene(introduction);
                }
                if(event.getCode()==KeyCode.Q){
                    stage.close();
                }
            }
        });

        // show starting scene
        stage.setTitle("Space Invaders");
        stage.setScene(introduction);
        stage.show();
    }

    //Vbox in end scene
    VBox create_end(boolean ifwin, int thescore){
        Label end1;
        if (ifwin) end1 = new Label("YOU WIN!");
        else end1 = new Label("YOU LOSE!");
        end1.setFont(Font.font("Verdana",FontWeight.BOLD,60));
        Label end2 = new Label("Final Score: "+thescore);
        Label end3 = new Label("ENTER - Start New Game");
        Label end4 = new Label("I - Back to Instructions");
        Label end5 = new Label("Q - Quit Game");
        Label end6 = new Label("1 or 2 or 3 - Start Game at specific level");
        end2.setFont(Font.font("Verdana",20));
        end3.setFont(Font.font("Verdana",20));
        end4.setFont(Font.font("Verdana",20));
        end5.setFont(Font.font("Verdana",20));
        end6.setFont(Font.font("Verdana",20));
        end1.setTextFill(Color.BLACK);
        end2.setTextFill(Color.BLACK);
        end3.setTextFill(Color.BLACK);
        end4.setTextFill(Color.BLACK);
        end5.setTextFill(Color.BLACK);
        end6.setTextFill(Color.BLACK);
        VBox endbox = new VBox(end1, end2, end3, end4, end5, end6);
        endbox.setMargin(end2, new Insets(15));
        endbox.setMargin(end3, new Insets(10));
        endbox.setMargin(end4, new Insets(7));
        endbox.setMargin(end5, new Insets(7));
        endbox.setMargin(end6, new Insets(7));
        endbox.setMinSize(width, height);
        endbox.setAlignment(Pos.CENTER);
        return endbox;
    }

    //bullet from player
    boolean move_playerbullet(ImageView playerbullet, Group g, Stage stage, AnimationTimer timer, ArrayList<Alien> aliens){
        if(playerbullet.getImage()!=null) {
            float newy = (float) (playerbullet.getY() - (PLAYER_BULLET_SPEED+3*(level-1)));
            if (newy < starty) {
                g.getChildren().remove(playerbullet);
                playerbullet.imageProperty().set(null);
                return false;
            }
            ImageView new_playerbullet = playerbullet;
            new_playerbullet.setY(newy);
            for (int i = 0; i < aliens.size(); i++) {
                if (new_playerbullet.intersects(aliens.get(i).getview().getBoundsInLocal())) {
                    g.getChildren().remove(aliens.get(i).alienview);
                    alinedies.play();
                    aliens.get(i).alienview=null;
                    score += aliens.get(i).mytype * 10;//add score
                    hiscore.setText("Score: " + score);
                    aliens.remove(i); //remove alien from the array
                    g.getChildren().remove(playerbullet);
                    playerbullet.imageProperty().set(null);
                    for (int j = 0; j < aliens.size(); j++) aliens.get(j).speedup();
                    if(aliens.size()==0){ //move to next level or game ends
                        g.getChildren().remove(player.shipview);
                        g.getChildren().clear();
                        player.shipview=null;
                        if(level+1<=3){
                            level++;
                            if (level == 2) {
                                aliens1.clear();
                                group1.getChildren().clear();
                                stage.setScene(level2);
                                init_group(2, stage, group2, aliens2);
                                timer.stop();
                            }
                            if (level == 3) {
                                aliens2.clear();
                                group2.getChildren().clear();
                                stage.setScene(level3);
                                init_group(3, stage, group3, aliens3);
                                timer.stop();
                            }
                        } else {
                            aliens3.clear();
                            group3.getChildren().clear();
                            end.getChildren().clear();
                            Rectangle end_inner = new Rectangle(width/4,height/4,width/2,height/2);
                            end_inner.setFill(Color.WHITE);
                            end_inner.setArcWidth(20);
                            end_inner.setArcHeight(20);
                            stage.setScene(endscene);
                            end.getChildren().add(end_inner);
                            hiscore.setText("Score: "+score);
                            end.getChildren().add(hiscore);
                            end.getChildren().add(liveslable);
                            end.getChildren().add(levellable);
                            end.getChildren().add(create_end(true,score));
                            timer.stop();
                        }
                    }
                    return false;
                }
            }
            playerbullet.setY(newy);
            return true;
        }
        return true;
    }

    //init a new group
    void init_group(int l, Stage stage, Group mygroup, ArrayList<Alien> aliens){
        mygroup.getChildren().remove(player.shipview);
        player.shipview=null;
        mygroup.getChildren().clear();
        aliens.clear();
        mygroup.getChildren().add(hiscore);
        mygroup.getChildren().add(liveslable);
        level = l;
        levellable.setText("Level: "+level);
        mygroup.getChildren().add(levellable);
        //initialize ship
        player = new Ship(width/2, height,l);
        mygroup.getChildren().add(player.shipview);
        //initialized aliens
        init_aliens(mygroup, aliens, l);
        //alien animate
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0 ;
            private long interval = 0;
            @Override
            public void handle(long now) {
                if (aliens.size() <= 0 || lives <0) this.stop();
                move_alien(mygroup, stage, aliens);
                if (aliens.size() <= 0 || lives <0) this.stop();
                if (level==1) interval= 2000_000_000;
                if (level==2) interval = 1600_000_000;
                if (level==3) interval = 1400_000_000;
                if (now - lastUpdate >= interval) {
                    lastUpdate = now;
                    Random random = new Random();
                    if(aliens.size()==0) return;
                    int randomInteger = random.nextInt(aliens.size());
                    initbullet_a(aliens.get(randomInteger).alien_x + smallwidth / 2, aliens.get(randomInteger).alien_y, aliens.get(randomInteger).mytype, mygroup, stage, aliens);
                }
            }
        };
        timer.start();
    }

    //init array of aliens
    void init_aliens(Group g, ArrayList<Alien> aliens, int level){
        aliens.clear();
        for(int i = 0; i < 5; i ++){
            int type = 3;
            if(i == 0) type = 3;
            if(i == 1||i == 2) type = 2;
            if(i == 3||i == 4) type = 1;
            for(int j = 0; j < 10; j++){
                Alien newalien = new Alien(g,j*(smallwidth+5),i*(smallheight+7)+starty,type,level);
                aliens.add(newalien);
            }
        }
    }

    //move a alien
    void move_alien(Group g, Stage s, ArrayList<Alien> aliens){
        for(int i = 0; i < aliens.size(); i++) {
            aliens.get(i).alien_x += aliens.get(i).dx;
        }
        for(int i = 0; i < aliens.size(); i++) {
            //get to bottom
            if (aliens.get(i).alien_y > height-2*smallheight) {
                for(int j = 0; j < aliens.size(); j++) {
                    g.getChildren().remove(aliens.get(j).alienview);
                    aliens.get(j).alienview=null;
                }
                g.getChildren().remove(player.shipview);
                player.shipview=null;
                aliens.clear();
                g.getChildren().clear();
                s.setScene(endscene);
                end.getChildren().clear();
                Rectangle end_inner = new Rectangle(width/4,height/4,width/2,height/2);
                end_inner.setFill(Color.WHITE);
                end_inner.setArcWidth(20);
                end_inner.setArcHeight(20);
                end.getChildren().add(end_inner);
                hiscore.setText("Score: "+score);
                end.getChildren().add(hiscore);
                end.getChildren().add(liveslable);
                end.getChildren().add(levellable);
                end.getChildren().add(create_end(false,score));
                return;
            }
            // get to right/left border
            if (aliens.get(i).alien_x >= width - smallwidth || aliens.get(i).alien_x <= 0) {
                for(int j = 0; j < aliens.size(); j++) {
                    if (aliens.get(j).alien_x >= width - smallwidth) aliens.get(j).alien_x = width - smallwidth;
                    if (aliens.get(j).alien_x <= 0) aliens.get(j).alien_x = 0;
                    aliens.get(j).dx *= -1;
                    aliens.get(j).alien_y += aliens.get(j).dy;
                }
                Random random = new Random();
                int randomInteger = random.nextInt(aliens.size());
                initbullet_a(aliens.get(randomInteger).alien_x + smallwidth / 2, aliens.get(randomInteger).alien_y, aliens.get(randomInteger).mytype, g, s, aliens);
                break;
            }
        }
        for(int i = 0; i < aliens.size(); i++) {
            aliens.get(i).alienview.setX(aliens.get(i).alien_x);
            aliens.get(i).alienview.setY(aliens.get(i).alien_y);
        }
    }

    //bullet from aliens
    public void initbullet_a(float x, float y, int type, Group g, Stage s, ArrayList<Alien> aliens){
        ImageView alienbullet;
        if (type==1) alienbullet = new ImageView("images/bullet1.png");
        else if (type==2) alienbullet = new ImageView("images/bullet2.png");
        else alienbullet = new ImageView("images/bullet3.png");
        alienbullet.setFitWidth(20);
        alienbullet.setFitHeight(40);
        alienbullet.setX(x);
        alienbullet.setY(y);
        g.getChildren().add(alienbullet);
        //play sound
        move1.play();
        move2.play();
        move3.play();
        move4.play();
        float speed;
        if (type==1) speed = (float) ENEMY1_BULLET_SPEED+3*(level-1);
        else if (type==2) speed = (float) ENEMY2_BULLET_SPEED+3*(level-1);
        else speed = (float) ENEMY3_BULLET_SPEED+3*(level-1);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(alienbullet.getParent()==null){
                    alienbullet.imageProperty().set(null);
                    this.stop();
                    return;
                }
                if (lives > 0 && alienbullet.getImage()!=null) {
                    alienbullet.setY(alienbullet.getY() + speed);
                    //check if hit the ship
                    if (alienbullet.intersects(player.shipview.getBoundsInLocal())) {
                        playerdies.play();
                        lives--;
                        liveslable.setText("Lives: " + lives);
                        if (lives <= 0) {
                            g.getChildren().remove(player.shipview);
                            g.getChildren().clear();
                            player.shipview=null;
                            for(int m = 0; m < aliens.size();m++) aliens.get(m).alienview=null;
                            aliens.clear();
                            alienbullet.imageProperty().set(null);
                            s.setScene(endscene);
                            end.getChildren().clear();
                            Rectangle end_inner = new Rectangle(width / 4, height / 4, width / 2, height / 2);
                            end_inner.setFill(Color.WHITE);
                            end_inner.setArcWidth(20);
                            end_inner.setArcHeight(20);
                            end.getChildren().add(end_inner);
                            hiscore.setText("Score: " + score);
                            end.getChildren().add(hiscore);
                            end.getChildren().add(liveslable);
                            end.getChildren().add(levellable);
                            end.getChildren().add(create_end(false, score));
                        }
                        g.getChildren().remove(alienbullet);
                        alienbullet.imageProperty().set(null);
                        g.getChildren().remove(player.shipview);
                        player.shipview=null;
                        player = new Ship(width / 2, height, level);
                        g.getChildren().add(player.shipview);
                        this.stop();
                        return;
                    }
                }
            }
        };
        timer.start();
    }


}