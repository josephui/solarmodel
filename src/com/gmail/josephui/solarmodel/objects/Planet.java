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
import com.gmail.josephui.solarmodel.util.Utility;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author Joseph Hui <josephui@gmail.com>
 */
public abstract class Planet extends SolarObject{
    //http://curious.astro.cornell.edu/question.php?number=203
    private static final long TIME_WHEN_ALL_PLANETS_ALIGN; //the year 2854
    
    //A record of all instances of Planet
    private static final LinkedList<Planet> allPlanets;
    
    static{
        TIME_WHEN_ALL_PLANETS_ALIGN = Utility.getDateInMs(01, 01, 2854);
        allPlanets = new LinkedList<>();
    }
    
    public static Planet[] getAllPlanetInstances(){
        return allPlanets.toArray(new Planet[allPlanets.size()]);
    }
    
//------------------------------------------------------------------------------
    
    protected final long semimajorAxis; //in km
    protected final double eccentricity; 
    protected final long relativeDay; //In EARTH minutes
    protected final long relativeYear; //In EARTH hours
    protected final double meanVelocity; //In km/sec
    
    protected final long semiminorAxis;
    protected final long perimeter; //in km
    
    protected Planet(long _diameter, long _semimajorAxis, double _eccentricity, long _relativeDay, long _relativeYear, double _meanVelocity){
        super(_diameter, _relativeDay);
        
        semimajorAxis = _semimajorAxis;
        eccentricity = _eccentricity;
        relativeDay = _relativeDay;
        relativeYear = _relativeYear;
        meanVelocity = _meanVelocity;
        
        //b = a * sqrt(1 - e^2)
        semiminorAxis = Math.round(semimajorAxis * Math.sqrt(1 - Math.pow(eccentricity, 2)));
        
        //placeholders for easy reading
        long a = semimajorAxis;
        long b = semiminorAxis;
        //ramanujan's approximation ellipse: pi ( 3 (a + b) - sqrt( (3a + b) (a + 3b) ) )
        perimeter = Math.round(Math.PI * (3 * (a + b)  - Math.sqrt((3 * a + b) * (a + 3 * b))));
        
        //register this Planet
        allPlanets.add(this);
    }
    
    /**
     * 
     * @param targetTime
     * @return 
     */
    @Override
    public Point<Long> getLocationAt(long targetTime){
        //Calculate current time with time when all planets align
        long timeDifference = targetTime - TIME_WHEN_ALL_PLANETS_ALIGN; // this is in milliseconds
        
        //Convert relativeyear into unit of milliseconds
        long relativeYearInMs = relativeYear * 60 /*minutes*/ * 60 /*seconds*/ * 1000 /*milliseconds*/;
        
        //Elliptical orbits are periodic, so we only care for remainder
        long timeRemain = timeDifference % relativeYearInMs;
        
        //t is between 0 and 2pi, so
        //t = 2pi * timeRemain / relativeYear
        //x = major cos(t)
        //y = minor sin(t)
        double t = -Math.PI * 2 * timeRemain / relativeYearInMs; //Negative cause counter clock
        long x = Math.round(1.0 * semimajorAxis * Math.cos(t));
        long y = Math.round(1.0 * semiminorAxis * Math.sin(t));
        
        return new Point<>(x, y);
    }
    
    public long getMaximumDistanceFromSun(){
        return semimajorAxis;
    }
    
    public long getMinimumDistanceFromSun(){
        return semiminorAxis;
    }
}
