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

package com.gmail.josephui.solarmodel.objects;

import com.gmail.josephui.solarmodel.display.SolarFrame;
import com.gmail.josephui.solarmodel.util.Point;
import com.gmail.josephui.solarmodel.util.Utility;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author Joseph Hui <josephui@gmail.com>
 */
public final class Sun extends SolarObject{
    private static final long DIAMETER = 1391684; //in km
    private static final long RELATIVE_DAY = 38880; //In EARTH minutes
    private static final BufferedImage IMAGE;
    //Singleton class
    private static final Sun instance;
    
    static{
        instance = new Sun();
        
        IMAGE = Utility.loadImage("/sun.png");
    }
    
    public static Sun getInstance(){
        return instance;
    }
    
    
//------------------------------------------------------------------------------
    
    private Sun(){
        super(DIAMETER, RELATIVE_DAY);
    }

    @Override
    public String getName(){
        return "Sun";
    }

    @Override
    public Point<Long> getLocationAt(long targetTime) {
        //Sun is pretty much stationary
        return new Point(0l, 0l);
    }
    
    

    @Override
    public BufferedImage getImage() {
        return IMAGE;
    }
}
