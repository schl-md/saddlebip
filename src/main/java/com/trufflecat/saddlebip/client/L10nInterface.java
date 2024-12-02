package com.trufflecat.saddlebip.client;

import gnu.gettext.GettextResource;

class L10nInterface
{
    private String locale;
    private String catalogName;
    private java.util.ResourceBundle catalog;

    public L10nInterface ()
    {
        locale = null;
        catalogName = null;
        catalog = null;
    }

    public void getLocaleCatalog ()
    {
        String language = System.getProperty("user.language");
        String country = System.getProperty("user.country");
        if (country == null)
        {
            country = "";
        }
        else
        {
            country = "-" + country;
        }
        getLocaleCatalog(language + country);
    }

    public void getLocaleCatalog (String locl)
    {
        locale = locl;
        catalogName = "saddlebip_" + locale;
        catalog = java.util.ResourceBundle.getBundle(catalogName);
    }

    public String translate (String str)
    {
        return GettextResource.gettext(catalog, str);
    }

    public String translateN (String str, String plural, int n)
    {
        return GettextResource.ngettext(catalog, str, plural, n);
    }
}