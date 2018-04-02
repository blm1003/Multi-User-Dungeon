package common;

public class Constants
{
    public static final String SEPARATOR =
            "**";

    public static final int SERVER_PORT =
            10001;

    public static final int SERVER_STARTUP_EXIT_CODE =
            1000;
    public static final String SERVER_STARTUP_ERROR_MESSAGE =
            "Server failed to open port. Terminating.";
    public static final String SERVER_ACCEPT_ERROR_MESSAGE =
            "Error accepting new user. Continuing execution.";

    public static final String IN_OUT_TO_CLIENT_INIT_ERROR_MESSAGE =
            "Input and output streams failed to initialize. Terminating.";
    public static final int IN_OUT_TO_CLIENT_INIT_EXIT_CODE =
            1001;

    public static final String READ_CLIENT_STREAM_ERROR_MESSAGE =
            "Error in reading from Client input stream. " +
                    "Continuing Execution.";
    public static final String READ_SERVER_STREAM_ERROR_MESSAGE =
            "Error reading from the server input stream. " +
                    "Continuing Execution.";
    public static final String READ_CLIENT_INPUT_ERROR_MESSAGE =
            "Error reading from the client input source. " +
                    "Continuing Execution";
    public static final String INCORRECT_CLIENT_USAGE_ERROR_MESSAGE =
            "Too many arguments, Usage: N/A. Just run main.";
    public static final int INCORRECT_CLIENT_USAGE_EXIT_CODE =
            1002;

    public static final String USER_IN_ERROR_MESSAGE =
            "Error getting user input. Continuing Execution. ";

    public static String APP_NAME =
            "Multi User Dungeon: Client App";

}
