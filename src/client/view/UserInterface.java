package client.view;

import client.ClientMain;
import common.Constants;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Graphical UserInterface
 * for the Client Side
 * application.
 * @author Anna Murphy
 */
public class UserInterface extends Application
{

    /**
     * Start Method. Necessary for
     * application children classes
     * @param stage
     */
    public void start (Stage stage)
    {
        BorderPane content = new BorderPane();
        content.setPadding(new Insets(UIConstants.INSETS));

        //Set Title

        Label title = new Label(Constants.APP_NAME);
        title.setFont(Font.font("Monospace", 30));
        content.setTop(title);

        //Set Console

        BorderPane console = new BorderPane();
        //console.setPadding(new Insets(UIConstants.INSETS));
        content.setCenter(console);

        TextArea output = new TextArea();
        output.setEditable(false);
        output.setMinSize(200, 300);
        output.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE );
        output.setFont(Font.font("Monospace", 14));
        output.setText("");
        //output.setText(UIConstants.OUTPUT_TEST_STRING);
        output.setWrapText(true);

        TextArea input = new TextArea();
        input.setMinSize(200, 100);
        input.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE );
        input.setFont(Font.font("Monospace", 14));
        input.requestFocus();

        console.setCenter(output);
        console.setBottom(input);

        //Assemble GUI

        stage.setScene(new Scene(content));
        stage.show();

        //Make a ClientMain and Start it.

        ClientMain client = new ClientMain(new WriteToConsole(output, input));

        input.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER)
                {
                    String inputContents = input.getText();
                    client.addUserIn(inputContents);
                    input.setText("");
                }
            }
        });

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                client.disconnect();
                System.exit(0);
            }
        });

        client.startClient();
    }

    public static void main (String[] args)
    {
        //new ClientMainThread().start(); //This is super janky, but it should technically work?
        Application.launch(args);
    }
}
