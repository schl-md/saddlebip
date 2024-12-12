package com.trufflecat.saddlebip.shared;

import com.trufflecat.saddlebip.shared.PlayerInfo;

public class GameState
{
    private int nPlayers;
    private PlayerInfo[] players;
    private int ply;

    public GameState (int numPlayers, PlayerInfo[] pInfos)
    {
        nPlayers = numPlayers;
        players = pInfos;
        ply = 0;
    }

    public void incTurn ()
    {
        ply++;
    }
    public int currentIdx ()
    {
        return ply % nPlayers;
    }

    public PlayerInfo getPlayerInfo (int i)
    {
        return players[i];
    }

    public PlayerInfo currentPlayer ()
    {
        return getPlayerInfo(currentIdx());
    }
}