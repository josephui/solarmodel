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

package com.gmail.josephui.solarmodel;

import com.gmail.josephui.solarmodel.display.SolarFrame;
import com.gmail.josephui.solarmodel.objects.*;
import com.gmail.josephui.solarmodel.util.Point;
import com.gmail.josephui.solarmodel.util.Utility;
import java.awt.Color;
import java.awt.Graphics;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Joseph Hui <josephui@gmail.com>
 */
public class SolarModel{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        ArrayList<SolarObject> solarObjects = new ArrayList<>();
        solarObjects.add(Sun.getInstance());
        solarObjects.add(new Mercury());
        solarObjects.add(new Venus());
        solarObjects.add(new Earth());
        solarObjects.add(new Mars());
        
        SolarFrame.getInstance().setVisible(true);
        
        long updatePerSecond = 60;
        long currentTime = System.currentTimeMillis();
        long timeElapsedPerUpdate = 60 /*minutes*/ * 60 /*seconds*/ * 1000 /*milliseconds*/; //1 hour, In ms
        
        for(long i = 0; !Thread.interrupted(); i++){
            try{
                Thread.sleep(1000 / updatePerSecond); //To reflect update per second
            }catch(InterruptedException ie){
                break;
            }
            SolarFrame.getInstance().updateGraphics(currentTime + timeElapsedPerUpdate * i);
        }
    }
    
}
