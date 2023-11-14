public class PrivateChat extends Chat {
    public PrivateChat(User admin, String id, String name) {
        super(admin, id, name);
    }

    public String getName() {
        return super.getName();
    }

    public String getId() {
        return super.getId();
    }
}
