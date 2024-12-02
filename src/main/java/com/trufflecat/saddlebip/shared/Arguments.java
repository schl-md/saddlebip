package com.trufflecat.saddlebip.shared;

public class Arguments {
    private String[] args;
    
    public Arguments (String[] args)
    {
        this.args = args;
    }

    public void parseArguments ()
    {
        for (int i = 0; i < args.length; i++)
        {
            System.out.println(args[i]);
        }
    }
}
