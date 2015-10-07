/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

/**
 *
 * @author Bas
 */
public class cbItem {

    private int id;
    private String text;

    public cbItem(int id, String text) {
        this.id = id;
        this.text = text;
    }
    
    @Override
    public String toString()
    {
        return text;
    }
    
    public int getID()
    {
        return id;
    }
}
