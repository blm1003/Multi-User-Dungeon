package bots;

import server.ServerMain;

public class ManageServer extends Bot
{

    public ManageServer (ServerMain server, String chatroom)
    {
        super(server, chatroom);
    }

    @Override
    public void execute()
    {
        if (this.server.getRoom(this.chatroom).isEmpty())
        {
            //TODO: Close the room in the server
            this.endBot();
        }
        String instruction = getAction();
        if (instruction != null)
        {
            switch (instruction)
            {
                case "kick":
                    break;
                case "lock":
                    break;
            }
        }
    }
}
