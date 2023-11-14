import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    NAME_REGEX("^[a-zA-Z0-9_]+$"),
    PASSWORD_REGEX("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[*.!@$%^&(){}\\[\\]:;<>,?/~_+\\-=|]).{8,32}$"),
    SEND_MESSAGE_REGEX("^send a message c (?<message>[\\S\\s]+)$"),
    ADD_MEMBER_REGEX("^add member i (?<id>\\S+)$"),
    REGISTER_REGEX("^register i (?<id>\\S+) u (?<username>\\S+) p (?<password>\\S+)$"),
    LOGIN_REGEX("^login i (?<id>\\S+) p (?<password>\\S+)$"),
    CREATE_CHANNEL_REGEX("^create new channel i (?<id>\\S+) n (?<name>\\S+)$"),
    CREATE_GROUP_REGEX("^create new group i (?<id>\\S+) n (?<name>\\S+)$"),
    START_PRIVATE_CHAT_REGEX("^start a new private chat with i (?<id>\\S+)$"),
    JOIN_CHANNEL_REGEX("^join channel i (?<id>\\S+)$"),
    ENTER_CHAT_REGEX("^enter (?<type>private chat||channel||group) i (?<id>\\S+)$"),
    ;

    private final String regex;

    private Commands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, Commands command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
