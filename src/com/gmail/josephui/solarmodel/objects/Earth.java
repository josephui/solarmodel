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

import com.gmail.josephui.solarmodel.util.Utility;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Joseph Hui <josephui@gmail.com>
 */
public class Earth extends Planet{
    private static final long DIAMETER = 12756; //in km
    private static final long SEMIMAJOR_AXIS = 149598262; //in km
    //private static final double ECCENTRICITY = 0.01671123; 
    private static final double ECCENTRICITY = 0.90; 
    private static final long RELATIVE_DAY = 1436; //In EARTH minutes
    private static final long RELATIVE_YEAR = 8766; //In EARTH hours
    private static final double MEAN_VELOCITY = 29.79; //In km/sec
    private static final BufferedImage IMAGE;
    
    static{
        IMAGE = Utility.loadImage("/earth.png");
    }
    
    public Earth(){
        super(DIAMETER, SEMIMAJOR_AXIS, ECCENTRICITY, RELATIVE_DAY, RELATIVE_YEAR, MEAN_VELOCITY);
    }

    @Override
    public String getName(){
        return "Earth";
    }

    @Override
    public BufferedImage getImage() {
        return IMAGE;
    }
}
