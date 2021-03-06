package server;

import common.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        server.getUser(username).printMessage("Welcome to " + chatroom + "!" +
                "\nType '?' for help.");
        while (userOnline)
        {
            try
            {
                String clientCommand = clientReader.readLine();
                if (clientCommand.equals("")) {}
                else if (String.valueOf(clientCommand.charAt(0)).equals("@"))
                {
                    String name = clientCommand.substring(1).split(" ")[0];
                    if (server.getRoom(chatroom).hasBot(name))
                    {
                        //Bot Command
                        String botCmd = clientCommand.substring(name.length() + 1);
                        server.getRoom(chatroom).addInstruction(name, botCmd);
                        System.out.println("User " + username +
                                " sent a bot command to bot " + name + ". \n>>" +
                                botCmd);
                    }
                    else
                    {
                        //Send @ Mention
                        String newMessage = clientCommand.substring(name.length() + 1);
                        System.out.println("User " + username +
                                " is sending @-ing user " + name + ". \n>>" +
                                newMessage);
                        if (server.getRoom(chatroom).hasUser(name))
                        {
                            //Print to Recipient
                            server.getUser(name).printMessage(
                                    ServerMessages.formatSendMessageToUser(
                                            newMessage,
                                            username,
                                            name));
                            //Print to Sender
                            server.getUser(username).printMessage(
                                    ServerMessages.formatSendMessageToUser(
                                            newMessage,
                                            username,
                                            name));
                        }
                        else
                        {
                            server.getUser(username).printMessage("ERROR: User " + name + " is unavailable.");
                            System.out.println("Error in sending @ message to user " + name + ". Aborted.");
                        }
                    }
                }
                else if (String.valueOf(clientCommand.charAt(0)).equals("~"))
                {
                    //Anonymous message to room
                    String newMessage = clientCommand.substring(1);
                    server.getRoom(chatroom).post(
                            ServerMessages.formatSendMessageToUser(
                                    newMessage, "Anonymous"));
                    System.out.println("User " + username +
                            " is sending an anonymous message.\n>>" +
                            newMessage );
                }
                else if (String.valueOf(clientCommand.charAt(0)).equals("?"))
                {
                    //Help
                    getHelp();
                }
                else if (clientCommand.startsWith("Bot"))
                {
                    String botName = clientCommand.substring(4);
                    boolean success = server.getRoom(chatroom).addBot(botName);
                    if (success)
                    {
                        System.out.println(username + " started a bot " +
                                botName + " in " + chatroom );
                        sendToChatroom(
                                (ServerMessages.formatSendMessageToUser(
                                        "Bot " + botName +
                                                " successfully started. ", "Server")));
                    }
                    else
                    {
                        System.out.println(username + " failed to start a bot " +
                                botName + " in " + chatroom );
                        sendToChatroom(
                                (ServerMessages.formatSendMessageToUser(
                                        "Bot " + botName +
                                                " failed to start. ", "Server")));
                    }
                }
                else if (clientCommand.equals("exit"))
                {
                    //Disconnect
                    System.out.println(username + " has disconnected.");
                    server.disconnectUser(username, chatroom);
                    break;
                }
                else
                {
                    //General Message
                    System.out.println(username + " sent a message to " +
                            chatroom + ": \n>> " + clientCommand);
                    sendToChatroom(
                            ServerMessages.
                                    formatSendMessageToUser(
                                            clientCommand, username));
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

    /**
     * Sends a message to
     * everyone in a specified
     * chatroom. This message
     * is formatted in ServerMessages.java
     * @param message Message being sent
     */
    public void sendToChatroom (String message)
    {
        server.getRoom(chatroom).post(message);
    }

    /**
     * Sends a help menu to the user
     * requesting it.
     */
    public void getHelp ()
    {
        System.out.println(username + " requested the help menu.");
        server.getUser(username).printMessage(
                "Help:\n" +
                        "'@' + message: sends a direct message at another user.\n" +
                        "'~' + message: sends an anonymous message in the room.\n" +
                        "'?': Brings up this help menu.\n" +
                        "'Bot' <NAME OF BOT> starts a Bot of that name. \n" +
                        "'exit': Disconnects from the server.");
    }

}
