package com.thoughtcomplex.image;

import java.awt.Point;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/*
 * Recommended resolution = 340x216, thanks to sorceress - 1.57 aspect ratio, close to golden
 * Popular screens: 1366x768, 1024x768, 1280x1024, 1440x900
 * Info from http://unity3d.com/webplayer/hwstats/pages/web-2012Q4-desktop.html
 */

import com.thoughtcomplex.image.GLColor;
import com.thoughtcomplex.starlight.image.VirtualScreen;

public class GLDrawingContext {
    private static int virtualWidth = 800;
    private static int virtualHeight = 600;
    private static int desktopWidth = 800;
    private static int desktopHeight = 600;
    private static int xofs = 0;
    private static int yofs = 0;
    private static int pixelMultiple = 1;
    
    public static VirtualScreen screen = null;
    
    public static void initialize(String title, int width, int height) {
        if (!Display.isCreated()) {
                try {
                        Display.create();
                        Display.setTitle(title);
                        Display.setFullscreen(true);
                } catch (LWJGLException e) {
                        e.printStackTrace();
                }
        }
        
        if (screen==null) {
                screen = new VirtualScreen(width,height);
        }
        
        virtualWidth = width;
        virtualHeight = height;
        desktopWidth = Display.getWidth();
        desktopHeight = Display.getHeight();
        
        pixelMultiple = Math.min(
                        desktopWidth/width,
                        desktopHeight/height
                        );
        int xMargin = desktopWidth - (width*pixelMultiple);
        int yMargin = desktopHeight - (height*pixelMultiple);
        xofs = xMargin/2;
        yofs = yMargin/2;
        
        //disable depth buffer
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        //enable textures
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        //enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        //use nearest-neighbor interpolation
        GL11.glEnable(GL11.GL_NEAREST);
    }
    
    public static void destroy() {
        try {
                Display.setFullscreen(false);
        } catch (LWJGLException e) {
                e.printStackTrace();
        }
        Display.destroy();
    }
    
    public static void display() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 0, 1);
        clear();
        screen.paint(xofs, yofs, virtualWidth*pixelMultiple, virtualHeight*pixelMultiple);
        
        GL11.glPopMatrix();
        Display.update();
    }
    
    public static void clear() {
        //GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
        
        //GL11.glEnable(GL11.GL_SCISSOR_TEST);
        //GL11.glScissor(xofs,yofs,300*pixelMultiple,200*pixelMultiple);
    }
    
    public static int getWidth() { return virtualWidth; }
    public static int getHeight() { return virtualHeight; }
    public static int getDesktopWidth() { return desktopWidth; }
    public static int getDesktopHeight() { return desktopHeight; }
    
    
    //===PAINTING METHODS===//
    
    public static void paintLine(int x1, int y1, int x2, int y2, float width, GLColor c) {
        //startDrawingLines(width);
        c.bind();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glLineWidth(width);
        GL11.glBegin(GL11.GL_LINES);
        {
                GL11.glVertex2f(x1, y1);
                GL11.glVertex2f(x2, y2);
        }
        GL11.glEnd();
    }

    public static void paintPolygon(Point.Float[] points, float width, GLColor c) {
        c.bind();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glLineWidth(width);
        GL11.glBegin(GL11.GL_POLYGON);
        {
                for(Point.Float p : points) {
                        GL11.glVertex2f(p.x, p.y);
                }
        }
        GL11.glEnd();
    }
    
    public static void paintRect(int x1, int y1, int x2, int y2, float width, GLColor c) {
        paintPolygon(new Point.Float[] {
                new Point.Float(x1, y1),
                new Point.Float(x2, y1),
                new Point.Float(x2, y2),
                new Point.Float(x1, y2)
        }, width, c);
    }
    
    public static void fillRect(int x1, int y1, int width, int height, GLColor c) {
        c.bind();
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glVertex2i(x1, y1);
            GL11.glVertex2i(x1, y1+height);
            GL11.glVertex2i(x1+width, y1+height);
            GL11.glVertex2i(x1+width, y1);
        }
        GL11.glEnd();
    }
    
}
