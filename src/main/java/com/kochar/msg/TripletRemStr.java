/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kochar.msg;

/**
 *
 * @author kochar-it-prabhjot
 */
public class TripletRemStr { 
    
    public TripletRemStr(String t)
    {
        tripletMemento = t;
        tripletMemento = tripletMemento.intern();
    }
    
    public String toString()
    {
        return tripletMemento;
    }
    
    String tripletMemento;
}
