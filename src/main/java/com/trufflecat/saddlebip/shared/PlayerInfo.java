package com.trufflecat.saddlebip.shared;

public class PlayerInfo {
    private String name;
    
    public PlayerInfo (String playerName)
    {
        name = playerName;
    }

    public String getName ()
    {
        return name;
    }
}
