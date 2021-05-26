package com.game.core;

import com.game.Playercontrol;
import com.game.entity.*;
import com.game.sprite.SpriteSheet;
import com.game.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JPanel implements Runnable, Playercontrol {

    public static int width;
    public static int height;
    boolean DEBUG = false; //debug variable set, if true barrier detection debug will be painted on frame


    private Thread thread; //generating thread field
    private boolean isRunning = false; //creating variable for game running state
    private Renderable renderable = new Renderable();


    Player player;
    ArrayList<Enemy> enemies = new ArrayList<>(); //creating list to append enemy objects in to
    ArrayList<Enemy> enemyRemoveList = new ArrayList<>(); //list created to remove enemies
    int wavenumber = 0; //wave count tracker intialised
    AssetManager assetManager;


    ArrayList<Prop>props = new ArrayList<>(); //list for generating props created, barriers are added in to this
    KeyHandler key;
    private BufferedImage img; //variable to store background image

    private int killcount = 0; //variable to increment score created

    public Game(int width, int height) {

        this.width = width;
        this.height = height;
        assetManager = new AssetManager();
        assetManager.spriteSheets.put(Values.PLAYERSPRITE,new SpriteSheet("entity/player/LinkSpriteSheet.png")); //hashmap from util to load sprite sheet in to game
        assetManager.spriteSheets.put(Values.MANTISPRITE,new SpriteSheet("enemies/mantisprite.png"));
        assetManager.spriteSheets.put(Values.BEETLESPRITE,new SpriteSheet("enemies/Beetlesprite.png"));
        //sprite sheets loaded in to game
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();

        try {
            img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("props/cave.png"));
        } catch(IOException e) {
            e.printStackTrace();  //exception handing if path isnt correct
        }//image loader created to read games background image path stored in res



        player = new Player(assetManager.spriteSheets.get(Values.PLAYERSPRITE), new Vector2f(300, 300), 50); //player variable created
        player.setSpriteLocation(0,0); //set starting animation of player
        renderable.AddEntry(player); //adding player to list of renderables to render on to game


        new KeyHandler(this,this); 
        setupLevel();
        start(); //running thread and starting
    }

    void createMantis(int x, int y){
        var mantis = new Mantis(assetManager.spriteSheets.get(Values.MANTISPRITE), new Vector2f(x,y), 60, player);
        mantis.setSpriteLocation(0,0);
        renderable.AddEntry(mantis); //adding mantis to list of items to render
        enemies.add(mantis); //Adding enemy sprites to arraylist enemies
    }

    void createBeetle(int x, int y){
        var beetle = new Beetle(assetManager.spriteSheets.get(Values.BEETLESPRITE), new Vector2f(x,y), 60, player);
        beetle.setSpriteLocation(0,0);
        renderable.AddEntry(beetle);
        enemies.add(beetle);
    }

    public void setupLevel(){
        var rand = new Random();
        int propCount = rand.nextInt(20); //setting prop count to hold a random value within 20
        var rockSize = 50;
        for(int i=0;i<propCount;i++){ //

            Vector2f pos = new Vector2f();
            boolean retry = true;
            Vector2f player_pos_bottom_right = new Vector2f();
            player_pos_bottom_right.x = player.pos.x+player.size;
            player_pos_bottom_right.y = player.pos.y+player.size;

            while(retry) { //condition made to respawn props if props spawn over eachother or on player
                pos.x = rand.nextInt(width);
                pos.y = rand.nextInt(height);
                Vector2f pos_bottom_right = new Vector2f();
                pos_bottom_right.x = pos.x + rockSize;
                pos_bottom_right.y = pos.y + rockSize;
                boolean failed = false;
                for(int k=0;k<props.size();k++){ //incrementing through list props
                    Prop p = props.get(k);
                    if(overlapping(pos,pos_bottom_right, p.pos,p.bottomRightPos) || overlapping(pos,pos_bottom_right, player.pos, player_pos_bottom_right)){ //condition made to respawn props if props spawn over eachother or on player
                        System.out.println("Retrying");
                        failed=true;
                        break;
                    }

                }

                if(!failed){
                    retry = false; //if no overlap keep prop spawned where it is
                }

            }

            var rock = new Prop(new SpriteSheet("props/generic-rpg-crate03.png"), new Vector2f(pos.x,pos.y), rockSize); //setting filepath for barrier prop
            props.add(rock);
            renderable.AddEntry(rock); //rendering on to frame

        }


    }

    public void paint(Graphics g) { //paint used for debug and to paint items on to game frame
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.BLACK);
        g2D.fillRect(0, 0, width,height);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);




        //DEBUG//////////
        if(DEBUG) {
            for (Prop prop : props) {
                g.setColor(Color.RED); //debugging used for collision detection to see where contact is made with prop
                g.fillRect(Math.round(prop.pos.x), Math.round(prop.pos.y), prop.size, prop.size);
            }
            g.setColor(Color.GREEN); //setting green barrier around player for debug with collision
            var playerTopLeft = new Vector2f(player.pos.x + player.xMargin, player.pos.y + player.yMargin);
            g.fillRect(Math.round(playerTopLeft.x), Math.round(playerTopLeft.y), player.width, player.height);

            for(Entity ent: enemies){
                if(ent.is_attacking){
                    g.fillRect(Math.round(ent.pos.x), Math.round(ent.pos.y), 20, 20);;
                }
            }


        }

        var playerpos = player.getEntityCentre();
       // g.setColor(Color.blue);
      //  g.fillRect(Math.round(playerpos.x),Math.round(playerpos.y),12,12);
        for(Enemy e:enemies){
            var en = e.getEntityCentre();
        //    g.fillRect(Math.round(en.x),Math.round(en.y),12,12);
        }

        if(player.current_health==0){ //setting fonts and text size to notify player when they die
            Font currentFont = g.getFont();
            float original_size = currentFont.getSize();
            Font newFont = currentFont.deriveFont(original_size*1.4f);
            g.setFont(newFont);
            g.setColor(Color.YELLOW);
            g.drawString("YOU DIED",(int)playerpos.x-30,(int)playerpos.y-40);
            newFont = currentFont.deriveFont(original_size);
        }



        /////


        renderable.RenderAll(g);

        ///Health Bar creation for player///
        g.setColor(Color.GRAY);
        g.fillRect(Math.round(playerpos.x)-25,Math.round(playerpos.y)-40,50,8); //setting location of health bar
        float healthWidth = player.current_health / player.max_health * 50; //setting width for health bar
        g.setColor(Color.GREEN); //setting colour of health bar
        g.fillRect(Math.round(playerpos.x)-25,Math.round(playerpos.y)-40, (int) healthWidth,8);

        //Enemy health bars///
        for(Enemy en : enemies){ //adding health bar to all enemies in list enemies
            g.setColor(Color.GRAY);
            g.fillRect(Math.round(en.pos.x)-10,Math.round(en.pos.y)-20,50,8);
            float enemyHealthWidth = en.current_health / en.max_health * 50;
            g.setColor(Color.RED);
            g.fillRect(Math.round(en.pos.x)-10,Math.round(en.pos.y)-20, (int) enemyHealthWidth,8); //filling health bar for enemy and offset by -20 to stay above enemy
        }



        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize()*1.4f);
        g.setFont(newFont);
        g.drawString("Wave count: " +wavenumber,960,20);
        g.drawString("Kill count: " +killcount,200,20);




        this.repaint(); //repaint used to update graphics as game progresses
        g2D.dispose();

        for (Enemy en:enemyRemoveList){
            enemies.remove(en); //remove an enemy from list enemies
            Renderable.Renderables.remove(en); //remove enemies from list renderables
        }
    }

    private synchronized void start() { //Starts thread
        if (isRunning)
            return;

        isRunning = true;
        thread = new Thread(this); //refers to run
        thread.start(); //starts the thread
    }

    private synchronized void stop() {
        if (!isRunning)
            return;

        isRunning = false;

        try {
            thread.join(); //Joins threads together and wait til die
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(1); //stop and exit system
    }

    @Override
    public void run() {
        try {
            while (isRunning) { //Game loop
                if(enemies.isEmpty()){
                    wavenumber++; //if theres no more enemies left, increment the round counter and start a new wave
                    startwave(wavenumber);
                }

                for (Enemy en : enemies) {
                    en.update();
                    if(player.is_attacking){

                        if(Vector2f.distanceBetweenPoints(en.pos, player.pos) < player.attackRange){
                            en.current_health -=1; //if player is in range of enemy and is attacking, decrement the players health by 1 each attack
                            if(en.current_health ==0){
                                en.die();
                                killcount+=100; //incrementing the score every time an enemy dies
                                //hiscores.writeFile(Integer.toString(killcount));

                            }
                        }

                    }

                    if(!en.isAlive){
                        enemyRemoveList.add(en); //if enemy is dead remove the enemies from enemyRemoveList

                    }
                }


                if(!player.isAlive){
                    break;
                }


                long fpsTime = (long) ((1f/20) * 1000); //settingna sleep for delaying thread
                Thread.sleep(fpsTime);
            }

           // new ScoreFrame(killcount); calls score frame once player is killed to view scores
        } catch (InterruptedException e){

        }
        stop();
    }

    private void startwave(int numofenemies) {
        if(wavenumber%5==0){
            player.current_health+=player.current_health * 0.75f; //health bar regen every 5 rounds by a 0.75
        }
        if(wavenumber%3==0){
            createBeetle(-50,100); //every third round, spawn boss enemy beetle
        }
        var random = new Random();
        for(int i=0;i<numofenemies;i++){
            var y = random.nextInt(height);
            createMantis(-50,y); //spawn enemies on the left side of the screen with a random y so they come from all sides of the left screen
        }
    }

    @Override
    public void move(int direction) { //0 is right, 1 is down, 2 is left, up is 3,
        player.direction = direction;
        if(player.current_health==0){return;}///disables the players ability to move if he dies
        if(direction == player.previous_dir) {//if the prev direction is the same as next direction (if key is being pressed down..)
            player.getNextSprite(6);
        }else{
            switch (direction){
                case Values.RIGHT:
                    player.setSpriteLocation(0,0); //if right set player animation to the right movement
                    break;
                case Values.DOWN:
                    player.setSpriteLocation(0,2);//if right set player animation to the down movement
                    break;
                case Values.LEFT:
                    player.setSpriteLocation(0,1);//if right set player animation to the left movement
                    break;
                case Values.UP:
                    player.setSpriteLocation(0,3);//if right set player animation to the up movement
                    break;
            }
        }

        float newX;
        float newY;
        switch (direction){
            case 0:
                newX = player.pos.x + player.speed;
                if(canMove(newX,player.pos.y)) {
                    player.pos.x = newX;//determine the speed at which the frame moves
                }
                break;

            case 1:
                newY = player.pos.y + player.speed;
                if(canMove(player.pos.x, newY)) {
                    player.pos.y = newY;
                }
                break;

            case 2:
                newX = player.pos.x - player.speed;
                if(canMove(newX, player.pos.y)) {
                    player.pos.x = newX;
                }
                break;

            case 3:
                newY = player.pos.y - player.speed;
                if(canMove(player.pos.x, newY)) {
                    player.pos.y = newY;
                }
                break;

            default:
                break;
        }
        player.previous_dir = direction; //set to previous as we are iterating through spritesheet and previous frame need to follow the direction
    }

    @Override
    public boolean canMove(float newX, float newY) { //this function determines whether the player is able to move, if theres an overlap between prop and player, can move is set to false stopping the player from moving through it
        if(player.current_health==0){return false;} //disables the players ability to move if he dies
        for(Prop prop : props){

            var topLeft = new Vector2f(newX+player.xMargin+5,newY+player.yMargin);
            var bottomRight = new Vector2f(topLeft.x+player.width-6,topLeft.y+player.height);

            if(overlapping(prop.pos,prop.bottomRightPos,topLeft,bottomRight)){
                //System.out.println("overlapping "+prop);
                return false;
            }
        }

        return true;
    }

    @Override
    public void attack(int direction) {
        player.attack(direction); //make sure the player is in the right direction when attacking
        //insert condition for cooldown of attacking for player upon button press
    }

   public static boolean overlapping(Vector2f l1, Vector2f r1, Vector2f l2, Vector2f r2){ //maths set out for overlapping, takes 4 points to determine if theres an overlap

        var x_overlap = Math.max(0, Math.min(r1.x,r2.x) - Math.max(l1.x,l2.x));
        var y_overlap = Math.max(0, Math.min(r1.y,r2.y) - Math.max(l1.y,l2.y));
        //System.out.println("overlap x :"+x_overlap);
        //System.out.println("overlap y :"+y_overlap);

        if(x_overlap > 0 && y_overlap >0){
            return true;
        }

        return false;

    }




}
