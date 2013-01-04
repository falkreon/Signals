package com.thoughtcomplex.game.physics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.thoughtcomplex.image.Paintable;

public class Compartment {
    private String id = "NoID";
    private String name = null;
    
    private Point location = new Point(0,0);
    
    
    private ArrayList<Paintable> drawList = new ArrayList<Paintable>();
    private ArrayList<RigidBody> physicsList = new ArrayList<RigidBody>();
    private ArrayList<Compartment> adjacent = new ArrayList<Compartment>();
    private ArrayList<String> namedLinks = new ArrayList<String>();
    
    public String getID()       { return id; }
    public String getName()     { return name; }
    
    public Compartment(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public void add(RigidBody b) {
        if (b instanceof Paintable) drawList.add((Paintable)b);
        physicsList.add(b);
    }
    
    public void remove(RigidBody b) {
        if (b instanceof Paintable) drawList.remove((Paintable)b);
        physicsList.remove(b);
    }
    
    public void unlink() {
        adjacent.clear();
    }
    
    public void link(Compartment c) {
        adjacent.add(c);
    }
    
    public List<String> namedLinks() {
        return Collections.unmodifiableList(namedLinks);
    }
    
    public List<RigidBody> contents() {
        return Collections.unmodifiableList(physicsList);
    }
    
    //public List<Compartment> adjacent() {
    //    return Collections.unmodifiableList(adjacent);
    //}
    
    public List<RigidBody> getRelevantSet() {
        ArrayList<RigidBody> result = new ArrayList<RigidBody>();
        result.addAll(physicsList);
        for(Compartment c : adjacent) {
            result.addAll(c.contents());
        }
        return result;
    }
}
