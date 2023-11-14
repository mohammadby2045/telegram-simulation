import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatMenu {

    public void run(Scanner scanner, Chat chat) {
        String command;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine();
            if ((matcher = Commands.getMatcher(command,Commands.SEND_MESSAGE_REGEX)) != null) {
                System.out.println(sendMessage(matcher, chat));
            } else if ((matcher = Commands.getMatcher(command,Commands.ADD_MEMBER_REGEX)) != null) {
                System.out.println(addMember(matcher, chat));
            } else if (command.equals("show all messages")) {
                showMessages(chat);
            } else if (command.equals("show all members")) {
                showMembers(chat);
            } else if (command.equals("back")) {
                return;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }


    private void showMessages(Chat chat) {
        System.out.println("Messages:");
        for (int i = 0; i < chat.getMessages().size(); i++) {
            System.out.println(chat.getMessages().get(i).getOwner().getName() + "(" + chat.getMessages().get(i).getOwner().getId() + "): " + "\"" + chat.getMessages().get(i).getContent() + "\"");
        }
    }

    private void showMembers(Chat chat) {
        if (chat.getClass() == PrivateChat.class) {
            System.out.println("Invalid command!");
            return;
        }
        System.out.println("Members:");
        for (int i = 0; i < chat.getMembers().size(); i++) {
            if (chat.getMembers().get(i) == chat.getOwner()) {
                System.out.println("name: " + chat.getMembers().get(i).getName() + ", id: " + chat.getMembers().get(i).getId() + " *owner");
                continue;
            }
            System.out.println("name: " + chat.getMembers().get(i).getName() + ", id: " + chat.getMembers().get(i).getId());
        }
    }

    private String addMember(Matcher matcher, Chat chat) {
        if (chat.getClass() == PrivateChat.class)
            return "Invalid command!";
        else if (!Messenger.getCurrentUser().equals(chat.getOwner())) {
            return "You don't have access to add a member!";
        } else if (Messenger.getMemberById(matcher.group("id")) == null) {
            return "No user with this id exists!";
        } else if (chat.getClass() == Channel.class && Messenger.getMemberById(matcher.group("id")).getChannelById(chat.getId()) != null) {
            return "This user is already in the chat!";
        } else if (chat.getClass() == Group.class && Messenger.getMemberById(matcher.group("id")).getGroupById(chat.getId()) != null) {
            return "This user is already in the chat!";
        }
        chat.addMember(Messenger.getMemberById(matcher.group("id")));
        if (chat.getClass() == Group.class) {
            Messenger.getMemberById(matcher.group("id")).addGroup((Group) chat);
            chat.addMessage(new Message(Messenger.getCurrentUser(), Messenger.getMemberById(matcher.group("id")).getName() + " has been added to the group!"));
            for (int i = 0; i < chat.getMembers().size(); i++) {
                chat.getMembers().get(i).addGroup((Group) chat);
                chat.getMembers().get(i).getChats().remove(chat);
            }
        } else
            Messenger.getMemberById(matcher.group("id")).addChannel((Channel) chat);

        return "User has been added successfully!";
    }

    private String sendMessage(Matcher matcher, Chat chat) {
        if (!Messenger.getCurrentUser().equals(chat.getOwner()) && chat.getClass() == Channel.class) {
            return "You don't have access to send a message!";
        }
        chat.addMessage(new Message(Messenger.getCurrentUser(), matcher.group("message")));
        if (chat.getClass() == PrivateChat.class && !Messenger.getMemberById(chat.getId()).getId().equals(Messenger.getCurrentUser().getId()))//chat ba khodesh
            Messenger.getMemberById(chat.getId()).getPrivateChatById(Messenger.getCurrentUser().getId()).addMessage(new Message(Messenger.getCurrentUser(), matcher.group("message")));
        if (chat.getClass() == Group.class) {
            for (int i = 0; i < chat.getMembers().size(); i++) {
                chat.getMembers().get(i).addGroup((Group) chat);
                chat.getMembers().get(i).getChats().remove(chat);
            }
        } else if (chat.getClass() == Channel.class) {
            for (int i = 0; i < chat.getMembers().size(); i++) {
                chat.getMembers().get(i).getChats().remove(chat);
                chat.getMembers().get(i).addChannel((Channel) chat);
            }
        } else {
            Messenger.getCurrentUser().addPrivateChat((PrivateChat) chat);
            Messenger.getCurrentUser().getChats().remove(chat);
            Messenger.getMemberById(chat.getId()).addPrivateChat(Messenger.getMemberById(chat.getId()).getPrivateChatById(Messenger.getCurrentUser().getId()));
            Messenger.getMemberById(chat.getId()).getChats().remove(Messenger.getMemberById(chat.getId()).getPrivateChatById(Messenger.getCurrentUser().getId()));
        }
        return "Message has been sent successfully!";
    }
}
