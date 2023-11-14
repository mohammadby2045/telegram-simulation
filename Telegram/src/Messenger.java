import java.util.ArrayList;
import java.util.Objects;

public class Messenger {
    private static final ArrayList<Channel> channels = new ArrayList<>();
    private static final ArrayList<Group> groups = new ArrayList<>();
    private static final ArrayList<User> users = new ArrayList<>();
    private static User currentUser;

    public static void addGroup(Group group) {
        groups.add(group);
    }

    public static void addChannel(Channel channel) {
        channels.add(channel);
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static Group getGroupById(String id) {
        if (groups == null)
            return null;
        for (int i = 0; i < groups.size(); i++) {
            if (Objects.equals(groups.get(i).getId(), id))
                return groups.get(i);
        }
        return null;
    }

    public static Channel getChannelById(String id) {
        if (channels == null)
            return null;
        for (int i = 0; i < channels.size(); i++) {
            if (Objects.equals(channels.get(i).getId(), id))
                return channels.get(i);
        }
        return null;
    }

    public static User getMemberById(String id) {
        if (users == null)
            return null;
        for (int i = 0; i < users.size(); i++) {
            if (Objects.equals(users.get(i).getId(), id))
                return users.get(i);
        }
        return null;
    }

    public static ArrayList<Channel> getChannels() {
        return channels;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Messenger.currentUser = currentUser;
    }
}
