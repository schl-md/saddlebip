package com.trufflecat.saddlebip.server;

import com.trufflecat.saddlebip.server.ServerGameState;

import com.trufflecat.saddlebip.shared.CommandLineArguments;
import com.trufflecat.saddlebip.shared.InputValidator;
import com.trufflecat.saddlebip.shared.BoardState;
import com.trufflecat.saddlebip.shared.PlayerInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Server
{
    private ServerGameState state;

    public Server ()
    {
        state = null;
    }

    public void serverMain (CommandLineArguments clargs)
    {
        initGameState();
    }

    private void initGameState ()
    {
    }
}