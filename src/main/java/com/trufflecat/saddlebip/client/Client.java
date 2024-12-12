package com.trufflecat.saddlebip.client;

import com.trufflecat.saddlebip.client.L10nInterface;
import com.trufflecat.saddlebip.client.ClientGameState;

import com.trufflecat.saddlebip.shared.CommandLineArguments;
import com.trufflecat.saddlebip.shared.InputValidator;
import com.trufflecat.saddlebip.shared.Communication;
import com.trufflecat.saddlebip.shared.BoardState;
import com.trufflecat.saddlebip.shared.PlayerInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client
{
    private L10nInterface l10n;
    private BufferedReader stdin;
    private ClientGameState state;

    public Client ()
    {
        l10n = new L10nInterface();
        stdin = new BufferedReader(new InputStreamReader(System.in));
        state = null;

        (new Communication()).communicate();
    }

    public void clientMain (CommandLineArguments clargs)
    {
        l10n.getLocaleCatalog();
        initGameState();
        playTurn();
        playTurn();
    }

    private void playTurn ()
    {
        System.out.printf("Player %s's turn:\n", state.currentPlayer().getName());
        state.currentBoard().colorPrintBoard();
        String sq = getUserInput("where to attack? ");
        char a = (char)(sq.chars().toArray()[0]);
        char b = (char)(sq.chars().toArray()[1]);
        int row = (int)a - (int)'A';
        int col = (int)b - (int)'0';
        BoardState bState = state.getBoardState(1 ^ state.currentIdx());
        bState.acceptShotFired(row, col);
        if (bState.sType(bState.getVal(row, col)) == 0)
            System.out.println("Miss!\n");
        else
            System.out.println("Hit!\n");
        //state.currentBoard().debugPrintBoard();
        state.incTurn();
    }

    private void initGameState ()
    {
        // int nPlayers = readNumber("How many players? ");
        int nPlayers = 2;
        PlayerInfo[] pInfos = new PlayerInfo[nPlayers];
        BoardState[] boards = new BoardState[nPlayers];
        for (int i = 0; i < nPlayers; i++)
        {
            String username = getUserInput(String.format("Player %d's name: ", i+1));
            pInfos[i] = new PlayerInfo(username);
            boards[i] = new BoardState();
        }
        state = new ClientGameState(nPlayers, pInfos, boards);
    }


    private int readNumber (String prompt)
    {
        String s;
        String err = l10n.translate("Must be an integer!  Try again.");
        while (! InputValidator.isInteger(s = getUserInput(prompt)))
        {
            System.out.print("\n" + err + "\n");
        }
        System.out.print('\n');
        return Integer.parseInt(s);
    }

    private String getUserInput (String prompt)
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