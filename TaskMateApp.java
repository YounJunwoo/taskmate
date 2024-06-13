import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class User {
    private String userId;
    private String userName;
    private String userPassword;
    private List<Schedule> schedules;

    public User(String userId, String userName, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.schedules = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public boolean verifyPassword(String password) {
        return this.userPassword.equals(password);
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }
}

class Schedule {
    private String scheduleId;
    private String date;
    private String time;
    private String details;
    private Notification notification;

    public Schedule(String scheduleId, String date, String time, String details) {
        this.scheduleId = scheduleId;
        this.date = date;
        this.time = time;
        this.details = details;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Notification getNotification() {
        return notification;
    }

    @Override
    public String toString() {
        return "Schedule ID: " + scheduleId + ", Date: " + date + ", Time: " + time + ", Details: " + details;
    }
}

class Notification {
    private String notificationTime;

    public Notification(String notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getNotificationTime() {
        return notificationTime;
    }
}

public class TaskMateApp {
    private Map<String, User> users;
    private User currentUser;

    public TaskMateApp() {
        users = new HashMap<>();
    }

    public static void main(String[] args) {
        TaskMateApp app = new TaskMateApp();
        app.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Add Schedule");
            System.out.println("4. Set Notification");
            System.out.println("5. View Schedules");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (option) {
                case 1:
                    signUp(scanner);
                    break;
                case 2:
                    login(scanner);
                    break;
                case 3:
                    addSchedule(scanner);
                    break;
                case 4:
                    setNotification(scanner);
                    break;
                case 5:
                    viewSchedules();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void signUp(Scanner scanner) {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter User Name: ");
        String userName = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = new User(userId, userName, password);
        users.put(userId, user);
        System.out.println("User registered successfully!");
    }

    private void login(Scanner scanner) {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = users.get(userId);
        if (user != null && user.verifyPassword(password)) {
            currentUser = user;
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid User ID or Password.");
        }
    }

    private void addSchedule(Scanner scanner) {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        System.out.print("Enter Schedule ID: ");
        String scheduleId = scanner.nextLine();
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter Time (HH:MM): ");
        String time = scanner.nextLine();
        System.out.print("Enter Details: ");
        String details = scanner.nextLine();

        Schedule schedule = new Schedule(scheduleId, date, time, details);
        currentUser.addSchedule(schedule);
        System.out.println("Schedule added successfully!");
    }

    private void setNotification(Scanner scanner) {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        System.out.print("Enter Schedule ID: ");
        String scheduleId = scanner.nextLine();
        Schedule schedule = null;

        for (Schedule s : currentUser.getSchedules()) {
            if (s.getScheduleId().equals(scheduleId)) {
                schedule = s;
                break;
            }
        }

        if (schedule == null) {
            System.out.println("Schedule not found.");
            return;
        }

        System.out.print("Enter Notification Time (HH:MM): ");
        String notificationTime = scanner.nextLine();
        Notification notification = new Notification(notificationTime);
        schedule.setNotification(notification);
        System.out.println("Notification set successfully!");
    }

    private void viewSchedules() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        for (Schedule schedule : currentUser.getSchedules()) {
            System.out.println(schedule);
            if (schedule.getNotification() != null) {
                System.out.println("  Notification Time: " + schedule.getNotification().getNotificationTime());
            }
        }
    }
}
