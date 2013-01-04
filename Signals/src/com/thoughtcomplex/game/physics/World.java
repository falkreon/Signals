package com.thoughtcomplex.game.physics;

import java.util.ArrayList;

public class World {
    ArrayList<Compartment> compartments = new ArrayList<Compartment>();
    
    /**
     * Takes this Set's Compartment list and links Compartments that refer to each other,
     * so that objects on a border between Compartments can collide.
     */
    public void link() {
        for(Compartment c : compartments) {
            for(String id : c.namedLinks()) {
                Compartment adjacent = get(id); //TODO: Make sure this isn't a ConcurrentModification problem
                if (adjacent!=null) c.link(adjacent);
            }
        }
    }
    
    public void add(Compartment c) {
        compartments.add(c);
    }
    
    public Compartment get(String compartmentID) {
        for(Compartment c : compartments) {
            if (c.getID().equals(compartmentID)) return c;
        }
        return null;
    }
    
    public void move(RigidBody b, Compartment src, Compartment dest) {
        src.remove(b);
        dest.add(b);
    }
    
    public void move(RigidBody b, String src, String dest) {
        move(b, get(src), get(dest));
    }
}
