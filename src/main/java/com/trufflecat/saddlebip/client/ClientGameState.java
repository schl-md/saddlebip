package com.trufflecat.saddlebip.client;

import com.trufflecat.saddlebip.shared.GameState;
import com.trufflecat.saddlebip.shared.BoardState;
import com.trufflecat.saddlebip.shared.PlayerInfo;

public class ClientGameState extends GameState
{
    private BoardState[] boards;

    public ClientGameState (int numPlayers, PlayerInfo[] pInfos,
                            BoardState[] bStates)
    {
        super(numPlayers, pInfos);
        boards = bStates;
    }
    
    public BoardState getBoardState (int i)
    {
        return boards[i];
    }
    public BoardState currentBoard ()
    {
        return getBoardState(currentIdx());
    }
}
