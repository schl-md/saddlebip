package com.trufflecat.saddlebip.shared;

import java.io.BufferedReader;

public class InputValidator {

    public static boolean isInteger (String s)
    {
        try
        {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
    public static String getUserInput (String prompt, BufferedReader stdin)
    {
        String input = null;
        System.out.print(prompt);
        try
        {
            input = stdin.readLine();
        }
        catch (java.io.IOException e)
        {
            System.exit(-1);
        }
        return input;
    }
}
