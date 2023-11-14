import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu {
    private final MessengerMenu messengerMenu;

    public LoginMenu() {
        messengerMenu = new MessengerMenu();
    }

    public void run(Scanner scanner) {
        String command;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine();
            if ((matcher = Commands.getMatcher(command, Commands.REGISTER_REGEX)) != null) {
                System.out.println(register(matcher));
            } else if ((matcher = Commands.getMatcher(command, Commands.LOGIN_REGEX)) != null) {
                System.out.println(login(matcher));
                if (login(matcher).equals("User successfully logged in!"))
                    messengerMenu.run(scanner);
            } else if (command.equals("exit")) {
                return;
            } else System.out.println("Invalid command!");
        }
    }

    private String login(Matcher matcher) {
        if (Messenger.getMemberById(matcher.group("id")) == null) {
            return "No user with this id exists!";
        } else if (!Messenger.getMemberById(matcher.group("id")).isPasswordCorrect(matcher.group("password"))) {
            return "Incorrect password!";
        } else {
            Messenger.setCurrentUser(Messenger.getMemberById(matcher.group("id")));
            return "User successfully logged in!";
        }
    }

    private String register(Matcher matcher) {
        if (Commands.getMatcher(matcher.group("username"), Commands.NAME_REGEX) == null) {
            return "Username's format is invalid!";
        } else if (Commands.getMatcher(matcher.group("password"), Commands.PASSWORD_REGEX) == null) {
            return "Password is weak!";
        } else if (Messenger.getMemberById(matcher.group("id")) != null) {
            return "A user with this ID already exists!";
        } else {
            Messenger.addUser(new User(matcher.group("id"), matcher.group("username"), matcher.group("password")));
            return "User has been created successfully!";
        }
    }
}
