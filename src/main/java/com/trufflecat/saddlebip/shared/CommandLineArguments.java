package com.trufflecat.saddlebip.shared;

//import static java.util.Map.entry;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.ArrayList;


public class CommandLineArguments {
    private int argc;
    private String[] argv;
    
    static HashMap<String, Integer> shortOpt2nOfArgs = new HashMap<>(Map.ofEntries(
        Map.entry("h", 0),
        Map.entry("v", 0),
        Map.entry("c", 1),
        Map.entry("s", 1)
    ));

    static HashMap<String, Integer> longOpt2nOfArgs = new HashMap<>(Map.ofEntries(
        Map.entry("help", 0),
        Map.entry("version", 0),
        Map.entry("client", 1),
        Map.entry("server", 1)
    ));

    public CommandLineArguments (String[] args)
    {
        this.argv = args;
        this.argc = args.length;
    }

    public void parseArguments ()
    {
        
        HashMap<String, String[]> opts = new HashMap();
        ArrayList<String> args = new ArrayList();
        int a = 0;
        int i = 0;
        String arg;
        int subargs = 0;
        while (i < argc)
        {
            arg = argv[i];
            if (subargs > 0)
            {
                //
                if (subargs-- == 0)
                {
                    // main arg is argv[a], subargs are argv[a+1], ..., argv[i]
                }
                continue;
            }
            if (argIsEndOpt(arg))
            {
                i++;
                break;
            }
            if (argIsLongOpt(arg))
            {
                arg = argLongOpt(arg);
                if (longOpt2nOfArgs.containsKey(arg))
                {
                    a = i;
                    subargs = longOpt2nOfArgs.get(arg);
                }
                else
                {
                    // invalid arg -- print usage
                }
            }
            else if (argIsShortOpt(arg))
            {
                arg = argShortOpt(arg);
                for (int c = 0; c < arg.length(); c++)
                {
                    if (shortOpt2nOfArgs.containsKey(arg))
                    {

                        a = i;
                        subargs = shortOpt2nOfArgs.get(arg);
                    }
                    else
                    {
                        // invalid arg -- print usage
                    }
                }
            }
            else
            {
                args.add(arg);
            }
            i++;
        }
        args.addAll(Arrays.asList(argv).subList(i, argc));
        for(i = 0; i < args.size(); i++)
        {
            // todo: argparsing heck
        }
    }

    private static boolean argIsStdin (String arg)
    {
        return arg.equals("-");
    }
    private static boolean argIsEndOpt (String arg)
    {
        return arg.equals("--");
    }
    private static boolean argIsShortOpt (String arg)
    {
        return arg.startsWith("-") && !argIsStdin(arg)
               && !argIsLongOpt(arg);
    }
    private static boolean argIsLongOpt (String arg)
    {
        return arg.startsWith("--") && !argIsEndOpt(arg);
    }

    private static String argShortOpt (String arg)
    {
        return arg.substring(1);
    }
    private static String argLongOpt (String arg)
    {
        return arg.substring(2);
    }
}
