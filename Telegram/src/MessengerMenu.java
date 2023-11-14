import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessengerMenu {
    private final ChatMenu chatMenu;

    public MessengerMenu() {
        chatMenu = new ChatMenu();
    }

    public void run(Scanner scanner) {
        String command;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine();
            if (command.matches("^show all channels$")) {
                showAllChannels();
            } else if (command.matches("^*show my chats$")) {
                showChats();
            } else if ((matcher = Commands.getMatcher(command,Commands.CREATE_CHANNEL_REGEX)) != null) {
                System.out.println(createChannel(matcher));
            } else if ((matcher = Commands.getMatcher(command,Commands.CREATE_GROUP_REGEX)) != null) {
                System.out.println(createGroup(matcher));
            } else if ((matcher = Commands.getMatcher(command,Commands.START_PRIVATE_CHAT_REGEX)) != null) {
                System.out.println(createPrivateChat(matcher));
            } else if ((matcher = Commands.getMatcher(command,Commands.JOIN_CHANNEL_REGEX)) != null) {
                System.out.println(joinChannel(matcher));
            } else if ((matcher = Commands.getMatcher(command,Commands.ENTER_CHAT_REGEX)) != null) {
                System.out.println(enterChat(matcher));
                if (enterChat(matcher).equals("You have successfully entered the chat!"))
                    chatMenu.run(scanner, Messenger.getCurrentUser().getChats().get(Messenger.getCurrentUser().indexOfChat(matcher.group("type"), matcher.group("id"))));
            } else if (command.equals("logout")) {
                System.out.println("Logged out");
                return;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private void showAllChannels() {
        System.out.println("All channels:");
        for (int i = 0; i < Messenger.getChannels().size(); i++) {
            System.out.println((i + 1) + ". " + Messenger.getChannels().get(i).getName() + ", id: " + Messenger.getChannels().get(i).getId() + ", members: " + (Messenger.getChannels().get(i).getMembers().size()));
        }
    }

    private void showChats() {
        int flag = 1;
        System.out.println("Chats:");
        for (int i = Messenger.getCurrentUser().getChats().size() - 1; i >= 0; i--) {
            System.out.print(flag + ". " + Messenger.getCurrentUser().getChats().get(i).getName() + ", id: " + Messenger.getCurrentUser().getChats().get(i).getId() + ", ");
            if (Messenger.getCurrentUser().getChats().get(i).getClass().toString().equals("class Group"))
                System.out.println("group");
            else if (Messenger.getCurrentUser().getChats().get(i).getClass().toString().equals("class Channel"))
                System.out.println("channel");
            else
                System.out.println("private chat");
            flag++;
        }
    }

    private String enterChat(Matcher matcher) {
        if (Objects.equals(matcher.group("type"), "channel") && Messenger.getCurrentUser().getChannelById(matcher.group("id")) != null) {
            return "You have successfully entered the chat!";
        } else if (Objects.equals(matcher.group("type"), "group") && Messenger.getCurrentUser().getGroupById(matcher.group("id")) != null) {
            return "You have successfully entered the chat!";
        } else if (Objects.equals(matcher.group("type"), "private chat") && Messenger.getCurrentUser().getPrivateChatById(matcher.group("id")) != null) {
            return "You have successfully entered the chat!";
        } else if (Objects.equals(matcher.group("type"), "private chat")) {
            return "You have no " + matcher.group("type") + " with this id!";
        } else if (Objects.equals(matcher.group("type"), "group")) {
            return "You have no " + matcher.group("type") + " with this id!";
        } else {
            return "You have no " + matcher.group("type") + " with this id!";
        }
    }

    private String createChannel(Matcher matcher) {
        if (Commands.getMatcher(matcher.group("name"), Commands.NAME_REGEX) == null) {
            return "Channel name's format is invalid!";
        } else if (Messenger.getChannelById(matcher.group("id")) != null)
            return "A channel with this id already exists!";
        else {
            Messenger.addChannel(new Channel(Messenger.getCurrentUser(), matcher.group("id"), matcher.group("name")));
            Messenger.getCurrentUser().addChannel(Messenger.getChannelById(matcher.group("id")));
            Messenger.getChannelById(matcher.group("id")).addMember(Messenger.getCurrentUser());
            return "Channel " + matcher.group("name") + " has been created successfully!";
        }
    }

    private String createGroup(Matcher matcher) {
        if (Commands.getMatcher(matcher.group("name"), Commands.NAME_REGEX) == null) {
            return "Group name's format is invalid!";
        } else if (Messenger.getGroupById(matcher.group("id")) != null)
            return "A group with this id already exists!";
        else {
            Messenger.addGroup(new Group(Messenger.getCurrentUser(), matcher.group("id"), matcher.group("name")));
            Messenger.getCurrentUser().addGroup(Messenger.getGroupById(matcher.group("id")));
            Messenger.getGroupById(matcher.group("id")).addMember(Messenger.getCurrentUser());
            return "Group " + matcher.group("name") + " has been created successfully!";
        }
    }

    private String createPrivateChat(Matcher matcher) {
        if (Messenger.getCurrentUser().getPrivateChatById(matcher.group("id")) != null) {
            return "You already have a private chat with this user!";
        } else if (Messenger.getMemberById(matcher.group("id")) == null) {
            return "No user with this id exists!";
        } else {
            Messenger.getCurrentUser().addPrivateChat(new PrivateChat(Messenger.getCurrentUser(), matcher.group("id"), Messenger.getMemberById(matcher.group("id")).getName()));
            if (!Messenger.getMemberById(matcher.group("id")).equals(Messenger.getCurrentUser())) {
                Messenger.getMemberById(matcher.group("id")).addPrivateChat(new PrivateChat(Messenger.getMemberById(matcher.group("id")), Messenger.getCurrentUser().getId(), Messenger.getCurrentUser().getName()));
            }
            return "Private chat with " + Messenger.getMemberById(matcher.group("id")).getName() + " has been started successfully!";
        }
    }

    private String joinChannel(Matcher matcher) {
        if (Messenger.getCurrentUser().getChannelById(matcher.group("id")) != null) {
            return "You're already a member of this channel!";
        } else if (Messenger.getChannelById(matcher.group("id")) == null) {
            return "No channel with this id exists!";
        } else {
            Messenger.getCurrentUser().addChannel(Messenger.getChannelById(matcher.group("id")));
            Messenger.getChannelById(matcher.group("id")).addMember(Messenger.getCurrentUser());
            return "You have successfully joined the channel!";
        }
    }

}
