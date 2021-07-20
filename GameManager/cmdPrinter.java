package LevelManager;

public class cmdPrinter implements MessageHandler{

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }
}
