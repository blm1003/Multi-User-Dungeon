package client.view;

import client.ClientMain;

public class ClientMainThread extends Thread
{
    public void run ()
    {
        new ClientMain().startClient();
    }
}
