package client.view;

import javafx.scene.control.TextArea;

/**
 * Class that allows ClientOut.java
 * To get information from the Console.
 *
 * @author Anna Murphy
 */
public class ReadConsole
{

    private TextArea consoleIn;

    /**
     * Constructor.
     * Set the console in as
     * a variable accesible be the
     * read method.
     * @param consoleIn text area in UI.
     */
    public ReadConsole (TextArea consoleIn)
    {
        this.consoleIn = consoleIn;
    }
}
