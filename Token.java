package edu.frostburg.cosc470;

/**
 * Token class
 *
 * @author Barak Shechter
 * @version 2016.6.14
 */
public class Token {
    public int id;
    public String value;
    public String type;
    public String place;

    /**
     * String representation of Token
     *
     * @return String representation of Token
     */
    public String toString() {
        return (value + " " + id);
    }
}
