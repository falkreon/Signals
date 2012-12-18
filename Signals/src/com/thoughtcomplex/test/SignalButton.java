package com.thoughtcomplex.test;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import com.thoughtcomplex.signals.Signal;

/**
 * Example class which forwards AWT events into Signals. These Signals are still
 * executing on the AWT Event Dispatch Thread, but much easier to hook.
 * @author Isaac Ellingson
 */
public class SignalButton extends JButton implements MouseListener {
    public Signal onClick      = new Signal("onClick");
    public Signal onPress      = new Signal("onPress");
    public Signal onRelease    = new Signal("onRelease");
    public Signal onMouseEnter = new Signal("onEnter");
    public Signal onMouseLeave = new Signal("onLeave");
    
    public SignalButton(String name) {
        super(name);
        this.addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        onClick.signal(e.getX(), e.getY(), e.getButton());
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        onMouseEnter.signal(e.getX(), e.getY());
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        onMouseLeave.signal(e.getX(), e.getY());
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        onPress.signal(e.getX(), e.getY(), e.getButton());
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        onRelease.signal(e.getX(), e.getY(), e.getButton());
    }
}
