/*
 * The MIT License
 *
 * Copyright 2015 Joseph Hui <josephui@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.gmail.josephui.solarmodel.display;

import com.gmail.josephui.solarmodel.Constants;
import com.gmail.josephui.solarmodel.objects.Planet;
import com.gmail.josephui.solarmodel.objects.Sun;
import com.gmail.josephui.solarmodel.util.Point;
import com.gmail.josephui.solarmodel.util.Utility;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Joseph Hui <josephui@gmail.com>
 */
public class SolarFrame extends JFrame implements Constants{
    //Singleton class
    private static final SolarFrame instance;
    private static GraphicsEnvironment environment;
    private static GraphicsDevice device;
    
    static{
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException cnfeieiaeulafe){
            System.err.println("Unable to initialize default UI.. Not even suppose to be possible..");
        }
        instance = new SolarFrame();
    }
    
    public static SolarFrame getInstance(){
        return instance;
    }
    
//------------------------------------------------------------------------------
    
    public JPanel contentPanel;
    
    private JComboBox[] dateComboBoxes;
    private JTextField yearField;
    
    private JLabel currentTimeLabel;
    private JLabel[] planetLabels;
    private JLabel statusLabel;
    
    private Planet[] planets;
    
    private final SolarWindow window;
    
    private double scaleDownDistancesBy;
    private double scaleDownSunSizeBy;
    private double scaleDownOtherSizesBy;
    
    private SolarFrame(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(PROGRAM_NAME);
        setContentPane(contentPanel = new JPanel(){{
            setLayout(new BorderLayout());
            add(new JPanel(){{
                setLayout(new GridLayout(1, 3));
                setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Date setting"));
                dateComboBoxes = new JComboBox[2]; //Month and Day
                for(int i = 0; i < dateComboBoxes.length; i++){
                    add(dateComboBoxes[i] = new JComboBox());
                }
                add(yearField = new JTextField());
            }}, BorderLayout.NORTH);
            add(new JPanel(){{
                planets = Planet.getAllPlanetInstances();
                planetLabels = new JLabel[planets.length];
                setLayout(new GridLayout(planets.length + 1, 2)); //The time label too!
                add(new JLabel("Current Date"){{
                    setBorder(BorderFactory.createEtchedBorder());
                    setHorizontalAlignment(RIGHT);
                }});
                add(currentTimeLabel = new JLabel(){{
                    setBorder(BorderFactory.createEtchedBorder());
                }});
                for(int i = 0; i < planets.length; i++){
                    add(new JLabel(planets[i].getName()){{
                        setBorder(BorderFactory.createEtchedBorder());
                        setHorizontalAlignment(RIGHT);
                    }});
                    add(planetLabels[i] = new JLabel(){{
                        setBorder(BorderFactory.createEtchedBorder());
                    }});
                }
            }}, BorderLayout.CENTER);
            add(statusLabel = new JLabel("Status"){{
                setBorder(BorderFactory.createEtchedBorder());
            }}, BorderLayout.SOUTH);
        }});
        
        environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = environment.getDefaultScreenDevice();
        
        long maxDistance = 0;
        for (Planet planet : planets) {
            maxDistance = Math.max(planet.getMaximumDistanceFromSun(), maxDistance);
        }
        long screenMin = Math.min(device.getDisplayMode().getWidth(), device.getDisplayMode().getHeight());
        scaleDownDistancesBy = maxDistance / screenMin * 1.5;
        
        /*So that the objects will be 1000 times bigger in diameter than 
        they are suppose to if we keep the relative distances. Otherwise they 
        can't even be seen. The Sun is only scaled up by factor of 10.*/
        final int sunZoomFactor = 10;
        final int planetZoomFactor = 1000;
        scaleDownSunSizeBy = scaleDownDistancesBy / sunZoomFactor;
        scaleDownOtherSizesBy = scaleDownDistancesBy / planetZoomFactor; 
        
        statusLabel.setText(" [Distances: 1x] [Sun size: " + sunZoomFactor + "x] [Planet size: " + planetZoomFactor + "x] ");
        
        window = new SolarWindow();
        
        pack();
        setSize(getWidth(), getHeight());
        
        //Starting location to be bottom right corner.
        int startX = device.getDisplayMode().getWidth() - getWidth();
        int startY = device.getDisplayMode().getHeight() - getHeight() - 50;
        
        setLocation(startX, startY);
    }
    
    /**
     * 
     * @param time the time of this instance for the planetary motion
     */
    public void updateGraphics(long time){
        window.currentTime.set(time);
        window.repaint();
    }
    
    @Override
    public void setVisible(boolean b){
        super.setVisible(b);
        window.setVisible(b);
    }
    
    
    /**
     * private inner class because this is owned by the frame
     */
    private class SolarWindow extends JWindow implements Constants{
        private final AtomicLong currentTime;
        private SolarWindow(){
            currentTime = new AtomicLong();
            final int screenWidth = device.getDisplayMode().getWidth();
            final int screenHeight = device.getDisplayMode().getHeight();
            System.out.println(screenWidth + " " + screenHeight);
            setSize(screenWidth, screenHeight);
            setContentPane(new JLabel(){
                {
                    setOpaque(true);
                }
                
                @Override
                public void paint(Graphics g){
                    Sun sun = Sun.getInstance();
                    drawAdjustedImage((Graphics2D)g, sun.getImage(), sun.getLocationAt(currentTime.get()), sun.getRotationAt(currentTime.get()), sun.getDiameter(), false);
                    int i = 0;
                    for(Planet planet : planets){
                        Point<Long> location = planet.getLocationAt(currentTime.get());
                        drawAdjustedImage((Graphics2D)g, planet.getImage(), location, planet.getRotationAt(currentTime.get()), planet.getDiameter(), true);
                        planetLabels[i++].setText(location.toString());
                    }
                    currentTimeLabel.setText(Utility.getDateInString(currentTime.get()));
                }
                
                private void drawAdjustedImage(Graphics2D g, BufferedImage image, Point<Long> location, double angle, long diameter, boolean planetScale){
                    int xPos = (int)Math.round(location.getX() / scaleDownDistancesBy);
                    int yPos = (int)Math.round(location.getY() / scaleDownDistancesBy);
                    int width = (int)Math.round(diameter / ((planetScale) ? scaleDownOtherSizesBy : scaleDownSunSizeBy));
                    int height = width/*(int)Math.round(diameter / scaleDownSizesBy)*/;
                    xPos = xPos - (width / 2) + (screenWidth / 2);
                    yPos = yPos - (height / 2) + (screenHeight / 2);
                    //g.drawImage(image, xPos, yPos, width, height, null);
                    AffineTransform transform = new AffineTransform();
                    transform.rotate(angle, image.getWidth() / 2.0, image.getHeight() / 2.0);
                    AffineTransformOp scaleOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage tempImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);;
                    tempImage = scaleOp.filter(image, tempImage);
                    g.drawImage(tempImage, xPos, yPos, width, height, null);
                }
            });
            setBackground(new Color(0,0,0,0));
            setAlwaysOnTop(true);
        }
    }
}
