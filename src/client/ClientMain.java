package client;

import common.Constants;

import java.io.IOException;
import java.net.Socket;

public class ClientMain
{

    private String username;
    private Socket serverInfo;

    private boolean isActive;

    public ClientMain ()
    {
        this.username = "DEFAULT";
        this.isActive = true;
    }

    public void startClient ()
    {
        try
        {
            this.serverInfo = new Socket(
                    "localhost", Constants.SERVER_PORT);
            new ClientIn(serverInfo, this).start();
            new ClientOut(serverInfo, this).start();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            System.exit(0);
        }
    }

    public static void main (String[] args)
    {
        ClientMain client = new ClientMain();
        client.startClient();
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

}