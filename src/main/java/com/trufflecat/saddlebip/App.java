package com.trufflecat.saddlebip;

import gnu.gettext.GettextResource;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        java.util.ResourceBundle catalog = java.util.ResourceBundle.getBundle("saddlebip_en-US");
        System.out.println(GettextResource.gettext(catalog, "Hello, world!"));
    }
}
