package edu.frostburg.cosc470;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by barakshechter on 7/24/16.
 */
public class Generator {
    private String[][] array;
    private int count;

    public Generator() {
        array = new String[10][5];
        count = 0;
    }

    public void gen(String lineNo, String op, String param1, String param2, String param3) {
        array[count++] = new String[]{lineNo,op,param1,param2,param3};
        if (count == array.length) expandArray();
    }

    private void expandArray() {
        String[][] temp = array;
        array = new String[temp.length*2][5];
        for (int i = 0; i < temp.length; ++i) {
            for(int j = 0; j <temp[0].length;++j) {
                array[i][j] = temp[i][j];
            }
        }
    }

    public String getLine(int i) {
        if (i<array.length) {
            return new String(array[i][0] + " " + array[i][1] + " " + array[i][2] +
                    ", " + array[i][3] + ", " + array[i][4]);
        }
        else {
            new Error(21, "Failed accessing generator line");
            return null;
        }
    }

    public void writeOutput()
    {
        gen(""+count, "hlt", "", "", "");
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < count; i++)
        {
            stringBuilder.append(getLine(i) + "\n");
        }
        try {
            PrintWriter writer = new PrintWriter("output");
            for(int i = 0; i < count; i++)
                writer.println(getLine(i));
            writer.close();
        } catch (FileNotFoundException e) {
            new Error(22, "Output was already created");
        }
    }

    public void backpatch(int i, int j)
    {
        array[i][4] += j;
    }

}
