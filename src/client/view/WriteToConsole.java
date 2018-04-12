package client.view;

import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;


/**
 * This is the class that writes to
 * the console in the UI.
 *
 * @author Anna Murphy
 */
public class WriteToConsole
{

    private String content;

    private TextArea consoleText;
    private TextArea output;

    /**
     * Constructor.
     * Takes in the JavaFX Node as a parameter
     * so the write method can actually update
     * the correct thing.
     * @param consoleText Text area being used in
     *                    UserInterface.java
     */
    public WriteToConsole (TextArea consoleText, TextArea output)
    {
        this.consoleText = consoleText;
        this.output = output;
    }

    public void write (String text)
    {
        content = content + "\n" + text;
        javafx.application.Platform.runLater(new Runnable() {
            @Override
            public void run() {
                consoleText.setText(content);
                consoleText.selectEnd();
                consoleText.deselect();
            }
        });
    }

}
