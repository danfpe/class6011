//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class MyJsonCreator {
    public MyJsonCreator() {
    }

    public static String createMessage(String type, String room, String msg) {
        return new String("{ \"type\": \"message\", \"user\": \"" + type + "\", \"room\": \"" + room + "\", \"message\": \"" + msg + "\" }");
    }

    public static String createJoinMessage(String user, String type) {
        return new String("{ \"type\": \"join\", \"room\": \"" + type + "\", \"user\": \"" + user + "\" }");
    }

    public static String createLeaveMessage(String user, String type) {
        return new String("{ \"type\": \"leave\", \"room\": \"" + type + "\", \"user\": \"" + user + "\" }");
    }
}
