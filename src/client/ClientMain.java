package client;

import client.view.WriteToConsole;
import common.Constants;

import java.io.IOException;
import java.lang.reflect.WildcardType;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.Queue;

public class ClientMain
{

    private String username;
    private Socket serverInfo;

    private boolean isActive;

    private WriteToConsole consoleWriter;
    private Queue<String> consoleReader;

    public ClientMain (WriteToConsole consoleWriter)
    {
        this.username = "DEFAULT";
        this.isActive = true;
        this.consoleWriter = consoleWriter;
        this.consoleReader = new PriorityQueue<>();
    }

    public void startClient ()
    {
        try
        {
            this.serverInfo = new Socket(
                    "129.21.156.224", Constants.SERVER_PORT);
            new ClientIn(serverInfo, this, consoleWriter).start();
            new ClientOut(serverInfo, this).start();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            System.exit(0);
        }
    }

    public void setUsername (String name)
    {
        this.username = name;
    }

    public String getUsername ()
    {
        return this.username;
    }

    public boolean online ()
    {
        return isActive;
    }

    public void disconnect ()
    {
        this.isActive = false;
    }

    /**
     * Adds a instruction from the UI
     * so that the clientOut thread can
     * take it and use it.
     * @param userIn text being added
     */
    public synchronized void addUserIn (String userIn)
    {
        this.consoleReader.add(userIn);
    }

    /**
     * Takes instructions out of the
     * queue, allowing them to be printed
     * to the server.
     * @return User Input to be printed.
     */
    public synchronized String getUserIn ()
    {
        String poll = this.consoleReader.poll();
        return poll;
    }
}