package com.trufflecat.saddlebip.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class BoardState {
    private byte[][] b;
    public BoardState ()
    {
        b = new byte[10][10];
        for (int r = 0; r < 10; r++)
        {
            for (int c = 0; c < 10; c++)
            {
                b[r][c] = 0;
            }
        }
        randumerizeStartingBoardState();

    }
    public void debugPrintBoard()
    {
        for (int r = 0; r < 10; r++)
        {
            for (int c = 0; c < 10; c++)
            {
                byte v = b[r][c];
                byte t = sType(v);
                byte w = which(v);
                boolean d = dirOf(v);
                boolean h = isHit(v);
                System.out.print(t);
                System.out.print(w);
                System.out.print(' ');
            }
            System.out.print('\n');
        }
    }

    private interface CellFmtEr {
        String fmtCell (int r, int c, byte v);
    };
    private interface RowFmtEr {
        String fmtRow (int r, int nc, CellFmtEr cf);
    };
    private interface BoardFmtEr {
        String fmtBoard (int nr, RowFmtEr rf, int nc, CellFmtEr cf);
    };

    public void colorPrintBoard()
    {
        String clr = "\033[0m";
        String blue = "\033[44;39m";

        CellFmtEr cellFmtEr = (r, c, v) -> {
            byte t = sType(v);
            byte w = which(v);
            boolean d = dirOf(v);
            boolean h = isHit(v);
            String s = t == 0 ? "  " : String.format("%c", '０' + t);
            String C = h
                ? t != 0
                    ? isDead(r, c)
                        ? "\033[41m"
                        : "\033[101m"
                    : "\033[100m"
                : "";
                     
            return String.format(" %s%s%s ",
                C, t != 0 ? String.format("%c", '０' + t) : "  ", blue);
        };

        RowFmtEr rowFmtEr = (r, nc, cf) -> {
            String[] fmtEdCells = new String[nc];
            for (int c = 0; c < nc; c++)
                fmtEdCells[c] = cf.fmtCell(r, c, b[r][c]);
            return String.format("│ %c %s│%s│%s",
                'Ａ'+r, blue, String.join("│", fmtEdCells), clr);
        };

        BoardFmtEr boardFmtEr = (nr, rf, nc, cf) -> {
            String[] fmtEdRows = new String[nr];
            for (int r = 0; r < nr; r++)
                fmtEdRows[r] = rf.fmtRow(r, nc, cf);
            
            String[] hdrParts = new String[nc];
            for (int c = 0; c < nc; c++)
                hdrParts[c] = String.format(" %c ", '０' + c);
            String blnk = "     ";
            String cSep = "────";
            List<String> cSeps = Collections.nCopies(nc, cSep);

            return String.format("%s%s%s%s",
                            String.format("%s┌%s┐\n%s│%s│",
                                blnk, String.join("┬", cSeps), blnk, String.join("│", hdrParts)),
                            String.format("\n┌%s%s┼%s┤%s\n",
                                cSep, blue, String.join("┼", cSeps), clr),
                String.join(String.format("\n├%s%s┼%s┤%s\n",
                                cSep, blue, String.join("┼", cSeps), clr), fmtEdRows),
                            String.format("\n└%s%s┴%s┘%s", cSep, blue, String.join("┴", cSeps), clr)
            );
            
        };
        
        System.out.printf("%s\n", boardFmtEr.fmtBoard(10, rowFmtEr, 10, cellFmtEr));
    }

    public void acceptShotFired (int r, int c)
    {
        b[r][c] |= 0b10000000;
    }

    public byte getVal (int r, int c)
    {
        return b[r][c];
    }

    private int lengthOfType (int type)
    {
        return switch (type) {
            case 0 -> 0;
            case 1 -> 2;
            case 2 -> 3;
            case 3 -> 3;
            case 4 -> 4;
            case 5 -> 5;
            default -> -1;
        };
    }

    public byte sType (byte v)
    {
        return (byte)((byte)(v >> 0) & 0b0111);
    }
    public byte which (byte v)
    {
        return (byte)((byte)(v >> 3) & 0b0111);
    }
    public boolean dirOf (byte v)
    {
        return 0b00 != ((byte)(v >> 6) & 0b01);
    }
    public boolean isHit (byte v)
    {
        return 0b00 != ((byte)(v >> 7) & 0b01);
    }
    public boolean isDead (int r, int c)
    {
        byte v = b[r][c];
        byte w = which(v);
        boolean dir = dirOf(v);
        int rDir = dirOf(v) ?1:0;
        int cDir = (int)(1^rDir);
        int headR = r - w*rDir;
        int headC = c - w*cDir;
        for (int i = 0; i < lengthOfType(sType(v)); i++)
            if (! isHit(b[headR + i*rDir][headC + i*cDir]))
                return false;
        return true;
    }

    private void randumerizeStartingBoardState ()
    {
        ArrayList<Integer> types = new ArrayList<Integer>();
        types.add(1);
        types.add(2);
        types.add(3);
        types.add(4);
        types.add(5);
        Collections.shuffle(types);
        for (int i = 0; i < 5; i++)
        {
            int type = types.get(i);
            ArrayList<int[]> vSqs = validSqs(type);
            
            // System.out.printf("\n\nspLen=%d, nChoices=%d\n", spLen, vSqs.size());
            // for (int h = 0; h < vSqs.size(); h++)
            //     System.out.print(String.format("(%d, %d, %d), ",
            //         vSqs.get(h)[0], vSqs.get(h)[1], vSqs.get(h)[2]));
            
            int j = ThreadLocalRandom.current().nextInt(0, vSqs.size());
            int[] vSq = vSqs.get(j);
            int d = vSq[2];
            int D = (1 ^ d);
            for (int q = 0; q < lengthOfType(type); q++)
            {
                b[vSq[0]][vSq[1]] = (byte)(type | (q << 3));
                vSq[0] += d;
                vSq[1] += D;
            }
        }
    }
    private ArrayList<int[]> validSqs (int type)
    {
        int spLen = lengthOfType(type);
        ArrayList<int[]> v = new ArrayList<int[]>();
        int R, C; int[] p;
        for(R = 0; R < 10; R++)
        {
            // System.out.printf("\n\nR = %d", R);
            for(C = 0; C < 10; C++)
            {
                // System.out.printf("  ////  (%d, %d): ", R, C);
                nextD:for (int d = 1; d > -1; d--)
                {
                    // System.out.printf(" %c:", (d == 1 ? 'h' : 'v'));
                    int D = (1^d);
                    int s = spLen;
                    if (R + s*d > 10 || C + s*D > 10)
                        continue nextD;
                    for (s--; s*d-1*D != s*D-1*d; s--)
                        if (b[R + s*d][C + s*D] != 0)
                            continue nextD;
                    // System.out.printf(" VALID;");
                    v.add(p = new int[3]);
                    p[0] = R; p[1] = C; p[2] = d;
                }
            }
        }
        return v;
    }

}
