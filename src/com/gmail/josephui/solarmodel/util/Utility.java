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

package com.gmail.josephui.solarmodel.util;

import com.gmail.josephui.solarmodel.objects.Earth;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.imageio.ImageIO;

/**
 * This class provides Utility methods to the Solar Model packages.
 * 
 * @author Joseph Hui <josephui@gmail.com>
 */
public final class Utility{
    
    private static final SimpleDateFormat dateFormat;
    
    static{
        dateFormat = new SimpleDateFormat("MM/dd/yyyy z");
    }
    
    /**
     * This method returns a long whose value is the representation of the date 
     * represented by the arguments month, day and year.
     * 
     * @param month
     * @param day
     * @param year
     * @return
     * @throws IllegalArgumentException 
     */
    public static long getDateInMs(int month, int day, int year) throws IllegalArgumentException{
        try{
            return dateFormat.parse(month + "/" + day + "/" + year + " GMT").getTime();
        }catch(ParseException pe){
            throw new IllegalArgumentException(pe);
        }
    }
    
    public static String getDateInString(long time){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int month = cal.get(Calendar.MONTH) + 1; //Java is stupid
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        return formatDates(month) + "/" + formatDates(day) + "/" + year;
    }
    
    public static BufferedImage loadImage(String fileName){
        try{
            return ImageIO.read(Utility.class.getResourceAsStream(fileName));
        }catch(IOException ioe){
            System.err.println("Unable to load image '" + fileName + "'");
            return null;
        }
    }
    
    private static String formatDates(int value){
        return ((value < 10) ? "0" : "") + value;
    }
    
//------------------------------------------------------------------------------   
    private Utility(){}
}
