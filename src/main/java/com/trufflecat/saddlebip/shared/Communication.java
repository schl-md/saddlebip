package com.trufflecat.saddlebip.shared;

import jnr.posix.POSIX;
import jnr.posix.POSIXFactory;
import jnr.posix.util.DefaultPOSIXHandler;
import jnr.constants.platform.Access;
import jnr.constants.platform.Fcntl;
import jnr.constants.platform.Errno;
import jnr.constants.platform.OpenFlags;
import jnr.ffi.Pointer;
import jnr.posix.util.Platform;

import java.nio.ByteBuffer;

public class Communication {
    private POSIX posix;
    
    public Communication ()
    {
        try {
            posix = POSIXFactory.getPOSIX(new DefaultPOSIXHandler(), true);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void communicate () {
    }
}
