package com.thoughtcomplex.test;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;

import com.thoughtcomplex.signals.CallbackSignature;
import com.thoughtcomplex.signals.Signal;

/**
 * Example class which forwards AWT events into Signals. These Signals are still
 * executing on the AWT Event Dispatch Thread, but much easier to hook.
 * @author Isaac Ellingson
 */
public class SignalButton extends JButton implements MouseListener, MouseMotionListener {
    
    //INTERFACE
    
    /**
     * Invoked when the mouse button has been clicked (pressed and released) on this component.
     * 
     * @signature
     *          onMouseClicked(int x, int y, int button);
     */
    @CallbackSignature({Integer.class, Integer.class, Integer.class})
    public Signal onMouseClicked      = new Signal();
    
    /**
     * Invoked when the mouse button has been pressed on this component.
     * 
     * @signature
     *          onMousePressed(int x, int y, int button);
     */
    @CallbackSignature({Integer.class, Integer.class, Integer.class})
    public Signal onMousePressed      = new Signal();
    
    /**
     * Invoked when the mouse button has been released on this component.
     * @signature
     *          onMouseReleased(int x, int y, int button);
     */
    @CallbackSignature({Integer.class, Integer.class, Integer.class})
    public Signal onMouseReleased    = new Signal();
    
    /**
     * Invoked when the mouse has entered this component.
     * @signature
     *          onMouseEntered(int x, int y);
     */
    @CallbackSignature({Integer.class, Integer.class})
    public Signal onMouseEntered = new Signal();
    
    /**
     * Invoked when the mouse has exited this component.
     * @signature
     *          onMouseLeft(int x, int y);
     */
    @CallbackSignature({Integer.class, Integer.class})
    public Signal onMouseExited = new Signal();
    
    /**
     * Invoked when the mouse has been pressed and then dragged on this component.
     * The coordinates given are always the point at which the user started dragging,
     * not the current mouse coordinates.
     * @signature
     *          onMouseDragged(int x, int y, int button);
     */
    @CallbackSignature({Integer.class, Integer.class, Integer.class})
    public Signal onMouseDragged = new Signal();
    
    /**
     * Invoked when the mouse has been moved while over this component.
     * @signature
     *          onMouseMoved(int x, int y);
     */
    @CallbackSignature({Integer.class, Integer.class})
    public Signal onMouseMoved = new Signal();
    
    //IMPLEMENTATION
    
    public SignalButton(String name) {
        super(name);
        this.addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        onMouseClicked.signal(e.getX(), e.getY(), e.getButton());
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        onMouseEntered.signal(e.getX(), e.getY());
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        onMouseExited.signal(e.getX(), e.getY());
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        onMousePressed.signal(e.getX(), e.getY(), e.getButton());
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        onMouseReleased.signal(e.getX(), e.getY(), e.getButton());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        onMouseDragged.signal(e.getX(), e.getY(), e.getButton());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        onMouseMoved.signal(e.getX(), e.getY());
    }
}
