package com.thoughtcomplex.test;

import com.thoughtcomplex.signals.Slot;

public class TestModel {
    private String modelID = "?";
    
    public TestModel(String id) {
        modelID = id;
    }
    
    @Slot()
    public void processClick(int x, int y, int button) {
        System.out.println(modelID + ": Click Start");
        long accumulator = 1;
        for(long a = 0; a<100L; a++) {
            System.out.println(modelID + " is processing: "+accumulator);
            accumulator += (accumulator / 2L) + 1L;
        }
    }
    
    @Slot("press")
    public void processPress(int x, int y, int button) {
        System.out.println(modelID + ": Press");
    }
    
    @Slot("release")
    public void processRelease(int x, int y, int button) {
        System.out.println(modelID + ": Release");
    }
    
    @Slot("enter")
    public void processEnter(int x, int y) {
        System.out.println(modelID + ": Enter");
    }
    
    @Slot("leave")
    public void processLeave(int x, int y) {
        System.out.println(modelID + ": Leave");
    }
}
