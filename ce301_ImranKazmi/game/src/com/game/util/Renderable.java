package com.game.util;

import com.game.entity.Entity;

import java.awt.*;
import java.util.ArrayList;

public class Renderable {
    public static ArrayList<Entity> Renderables = new ArrayList<Entity>();//arraylist created to store objects to be rendered


    public static int AddEntry(Entity e){//add entries to list renderables above
        Renderables.add(e);
        return Renderables.size()-1;
    }

    public static void RenderAll(Graphics g){//renderall used to render all objects in array list renderables
        for(int i=0;i<Renderables.size();i++){
            Entity ent = Renderables.get(i);
            if(ent != null){ // Check list ent isn't null otherwise errors would occur as empty list cant be rendered
                ent.render(g);
            } else {

            }
        }
    }

}
