package com.trufflecat.saddlebip;

import com.trufflecat.saddlebip.client.Client;
import com.trufflecat.saddlebip.shared.CommandLineArguments;

public class App {
    public static void main(String[] args) {
        CommandLineArguments clargs = new CommandLineArguments(args);
        clargs.parseArguments();

        Client client = new Client();
        client.clientMain(clargs);
    }
}
