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

/**
 * This class provides a wrapper for Point objects of any Number types.
 * 
 * @author Joseph Hui <josephui@gmail.com>
 * @param <E>
 */
public final class Point<E extends Number>{
    public volatile E x;
    public volatile E y;
    public Point(E _x, E _y){
        setX(_x);
        setY(_y);
    }
    public E getX(){
        return x;
    }
    public E getY(){
        return y;
    }
    public synchronized void setX(E _x){
        x = _x;
    }
    public synchronized void setY(E _y){
        y = _y;
    }
    
    @Override
    public String toString(){
        return "[" + x + " km, " + y + " km]";
    }
}
