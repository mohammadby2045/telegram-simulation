import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class Chat {
    private final ArrayList<User> members;
    private final ArrayList<Message> messages;
    private final User owner;
    private final String id;
    private final String name;

    public Chat(User admin, String id, String name) {
        this.id = id;
        this.name = name;
        this.owner = admin;
        this.members = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public void addMember(User user) {
        members.add(user);
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public User getOwner() {
        return owner;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
