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
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author Joseph Hui <josephui@gmail.com>
 */
public class Venus extends Planet{
    private static final long DIAMETER = 12104; //in km
    private static final long SEMIMAJOR_AXIS = 108209475; //in km
    //private static final double ECCENTRICITY = 0.00677672; 
    private static final double ECCENTRICITY = 0.90; 
    private static final long RELATIVE_DAY = 349920; //In EARTH minutes
    private static final long RELATIVE_YEAR = 5376; //In EARTH hours
    private static final double MEAN_VELOCITY = 35.03; //In km/sec
    private static final BufferedImage IMAGE;
    
    static{
        IMAGE = Utility.loadImage("/venus.png");
    }
    
    public Venus(){
        super(DIAMETER, SEMIMAJOR_AXIS, ECCENTRICITY, RELATIVE_DAY, RELATIVE_YEAR, MEAN_VELOCITY);
    }

    @Override
    public String getName(){
        return "Venus";
    }

    @Override
    public BufferedImage getImage() {
        return IMAGE;
    }
}
