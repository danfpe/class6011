
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Room {
    private static final HashMap<String, Room> roomMap = new HashMap<>();
    private final String roomName;
    private final ArrayList<ClientHandlerRunnable> clientsArray = new ArrayList<>();
    private final ArrayList<String> messageArray = new ArrayList<>();

    public static Room getRoom(String name) {
        synchronized(roomMap) {
            Room room = (Room) roomMap.get(name);
            if (room == null) {
                room = new Room(name);
                roomMap.put(name, room);
            }

            return room;
        }
    }

    public String getName() {
        return this.roomName;
    }

    public synchronized void addClient(ClientHandlerRunnable clientHandlerRunnable) throws IOException {
        Iterator runnableIterator = this.clientsArray.iterator();

        while(runnableIterator.hasNext()) {
            ClientHandlerRunnable nextHandlerRunnable = (ClientHandlerRunnable)runnableIterator.next();
            String joinMessage = MyJsonCreator.createJoinMessage(nextHandlerRunnable.getUserName(), this.roomName);
            clientHandlerRunnable.sendWebSocketMessage(joinMessage);
        }

        runnableIterator = this.messageArray.iterator();

        while(runnableIterator.hasNext()) {
            String nextMsg = (String)runnableIterator.next();
            clientHandlerRunnable.sendWebSocketMessage(nextMsg);
        }

        if (MyWebServer.verbose_) {
            PrintStream out = System.out;
            String userName = clientHandlerRunnable.getUserName();
            out.println(userName + " has joined room " + this.roomName);
        }

        this.clientsArray.add(clientHandlerRunnable);
        String var5 = MyJsonCreator.createJoinMessage(clientHandlerRunnable.getUserName(), this.roomName);
        this.sendMessage(var5, false);
    }

    public synchronized void removeClient(ClientHandlerRunnable clientHandlerRunnable) {
        this.clientsArray.remove(clientHandlerRunnable);
    }

    public synchronized void sendMessage(String msg, boolean isJoin) throws IOException {
        if (MyWebServer.verbose_) {
            PrintStream out = System.out;
            int size = this.clientsArray.size();
            out.println("Sending message to " + size + " clients: " + msg);
        }

        Iterator iterator = this.clientsArray.iterator();

        while(iterator.hasNext()) {
            ClientHandlerRunnable var4 = (ClientHandlerRunnable)iterator.next();
            var4.sendWebSocketMessage(msg);
        }

        if (isJoin) {
            this.messageArray.add(msg);
        }

    }

    private Room(String roomName) {
        if (MyWebServer.verbose_) {
            System.out.println("Creating new room: " + roomName);
        }

        this.roomName = roomName;
    }
}
