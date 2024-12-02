package com.trufflecat.saddlebip;

import com.trufflecat.saddlebip.client.Client;
import com.trufflecat.saddlebip.shared.Arguments;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Arguments arguments = new Arguments(args);
        arguments.parseArguments();
        Client client = new Client();
        client.clientMain();
    }
}
