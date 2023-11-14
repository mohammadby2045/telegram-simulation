import java.util.ArrayList;
import java.util.Objects;

public class User {
    private final ArrayList<Chat> chats;
    private final String id;
    private final String name;
    private final String password;

    public User(String Id, String Name, String Password) {
        this.id = Id;
        this.name = Name;
        this.password = Password;
        this.chats = new ArrayList<>();
    }

    public void addChat(Chat chat) {
        chats.add(chat);
    }

    public void addGroup(Group group) {
        chats.add(group);
    }

    public void addChannel(Channel channel) {
        chats.add(channel);
    }

    public void addPrivateChat(PrivateChat pv) {
        chats.add(pv);
    }

    public Group getGroupById(String id) {
        for (int i = 0; i < chats.size(); i++) {
            if (chats.get(i).getClass() == Group.class && Objects.equals(chats.get(i).getId(), id)) {
                return (Group) chats.get(i);
            }
        }
        return null;
    }

    public Channel getChannelById(String id) {
        for (int i = 0; i < chats.size(); i++) {
            if (chats.get(i).getClass() == Channel.class && Objects.equals(chats.get(i).getId(), id)) {
                return (Channel) chats.get(i);
            }
        }
        return null;
    }

    public PrivateChat getPrivateChatById(String id) {
        for (int i = 0; i < chats.size(); i++) {
            if (chats.get(i).getClass() == PrivateChat.class && Objects.equals(chats.get(i).getId(), id)) {
                return (PrivateChat) chats.get(i);
            }
        }
        return null;
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private String getPassword() {
        return password;
    }

    public boolean isPasswordCorrect(String password) {
        return this.getPassword().equals(password);
    }

    public int indexOfChat(String type, String id) {
        String typeForEqual;
        if (type.equals("private chat"))
            typeForEqual = "class PrivateChat";
        else if (type.equals("channel"))
            typeForEqual = "class Channel";
        else
            typeForEqual = "class Group";
        for (int i = 0; i < chats.size(); i++) {
            if (chats.get(i).getClass().toString().equals(typeForEqual) && chats.get(i).getId().equals(id))
                return i;
        }
        return -1;
    }
}
