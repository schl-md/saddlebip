package com.trufflecat.saddlebip.client;

import com.trufflecat.saddlebip.client.L10nInterface;

public class Client
{
    public void clientMain ()
    {
        L10nInterface l10n = new L10nInterface();
        l10n.getLocaleCatalog();
        System.out.println(l10n.translate("Hello, world!"));

    }
}