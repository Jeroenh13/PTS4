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

    private final int id;
    private final String text;

    /**
     * Creates a new comboboxItem
     * @param id id for the item
     * @param text Text that is shown
     */
    public cbItem(int id, String text) {
        this.id = id;
        this.text = text;
    }
    
    /**
     * Returns the text
     * @return the shown text
     */
    @Override
    public String toString()
    {
        return text;
    }
    
    /**
     * Returns the set id
     * @return the ID
     */
    public int getID()
    {
        return id;
    }
}
