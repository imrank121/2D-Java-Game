package com.game.sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class SpriteSheet {

    private final BufferedImage SPRITE_SHEET;
    private BufferedImage[][] spriteArray; //creating spritearray variable for x and y values
    private final int TILE_SIZE = 32; //setting tile size for sprites in spritesheet
    public int w;
    public int h;
    private int wSprite;
    private int hSprite;


    public SpriteSheet(String file) {
        w = TILE_SIZE;
        h = TILE_SIZE;
        //width and height set to tile size
        System.out.println("Loading..." + file + "...");
        SPRITE_SHEET = loadSprite(file); //loading spritesheet using file path given

        wSprite = SPRITE_SHEET.getWidth() / w; //total no. of sprites on screen
        hSprite = SPRITE_SHEET.getHeight() / h;
        loadSpriteArray();

        System.out.println("loaded!");
    }



    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setWidth(int i) {
        w = i;
        wSprite = SPRITE_SHEET.getWidth() / w;
    }

    public void setHeight(int i) {
        h = i;
        hSprite = SPRITE_SHEET.getHeight() / h;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    private BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file)); //read spritesheet file path
        } catch(Exception e) {
            System.out.println("Error, incorrect file path"); //exception handling if incorrect path is supplied
        }

        return sprite;
    }

    private void loadSpriteArray() {
        spriteArray = new BufferedImage[wSprite][hSprite];

        for (int x = 0; x < wSprite; x++) { //iterates through sprite array  along x axis
            for (int y = 0; y < hSprite; y++) { //iterates through sprite array  along y axis
                spriteArray[x][y] = getSprite(x, y); //uses values to retrieve x y pos for specific sprite in sheet
            }
        }
    }

    public BufferedImage getSpriteSheet() {
        return SPRITE_SHEET;
    }

    public BufferedImage getSprite(int x, int y) {
        return SPRITE_SHEET.getSubimage(x * w, y * h, w, h);
    }

}
