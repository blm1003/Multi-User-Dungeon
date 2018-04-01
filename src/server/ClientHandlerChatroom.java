package server;

import client.ClientMain;
import common.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Thread that actually deals with
 * client commands inside a chatroom.
 * This can mean @ mentions and starting
 * bots.
 * @author Anna Murphy
 */
public class ClientHandlerChatroom extends Thread
{

    //User Information Variables
    private String username;
    private String chatroom;
    private boolean userOnline;

    //Networking Variables
    private Socket client;
    private ServerMain server;
    private BufferedReader clientReader;

    /**
     * Constructor.
     * Creates the I/O streams
     * and assigns variables.
     * @param client Client Socket
     * @param server Server Main reference
     * @param username Username String
     * @param chatroom Chatroom String
     */
    public ClientHandlerChatroom (Socket client,
                                  ServerMain server,
                                  String username,
                                  String chatroom)
    {
        this.username = username;
        this.chatroom = chatroom;
        this.client = client;
        this.server = server;
        this.userOnline = true;

        //Create Streams
        try
        {
            this.clientReader = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
        }
        catch (IOException ioe)
        {
            System.out.println(Constants.IN_OUT_TO_CLIENT_INIT_ERROR_MESSAGE);
            System.exit(Constants.IN_OUT_TO_CLIENT_INIT_EXIT_CODE);
        }
    }

    /**
     * Loops, takes in client
     * input, and acts on that information.
     * All the actions to be taken are
     * held in their own methods
     */
    public void run ()
    {
        System.out.println("Chatroom Handler Online for " + username);
        server.getUser(username).printMessage("Welcome to " + chatroom + "!");
        while (userOnline)
        {
            try
            {
                String clientCommand = clientReader.readLine();
                if (clientCommand.substring(0, 1).equals("@"))
                {
                    //Send @ Mention
                }
                else if (clientCommand.equals("exit"))
                {
                    //Disconnect
                }
                else if (clientCommand.substring(0, 1).equals("?"))
                {
                    //Help
                }
                else
                {
                    System.out.println(username + " sent a message to " +
                            chatroom + ": \n>> " + clientCommand);
                    sendToChatroom(
                            ServerMessages.
                                    formatSendMessageToUser(
                                            clientCommand));
                }
            }
            catch (IOException ioe)
            {
                System.out.println(
                        Constants.READ_CLIENT_STREAM_ERROR_MESSAGE);
            }
        }
        System.out.println("Chatroom Handler Offline for " + username);
    }

    public void sendToChatroom (String message)
    {
        server.getRoom(chatroom).post(message);
    }

}
