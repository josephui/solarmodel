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

import com.gmail.josephui.solarmodel.util.Point;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Joseph Hui <josephui@gmail.com>
 */
public abstract class SolarObject {
    protected final long diameter; //in km
    protected final long relativeDay; //In EARTH minutes
    protected SolarObject(long _diameter, long _relativeDay){
        diameter = _diameter;
        relativeDay = _relativeDay;
    }
    public abstract String getName();
    public abstract Point<Long> getLocationAt(long targetTime);
    public abstract BufferedImage getImage();
    public long getDiameter(){
        return diameter;
    }
    
    public long getRelativeDay(){
        return relativeDay;
    }
    
    public double getRotationAt(long targetTime){
        long relativeDayInMs = relativeDay * 60 /*seconds*/ * 1000 /*millisecond*/;
        return -(targetTime % relativeDayInMs) * 2 * Math.PI / relativeDayInMs;
    }
}
