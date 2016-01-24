package it.unipd.math.pcd.actors.utils.messages.spam;

/**
 * Created by davide on 24/01/16.
 */
public class ImplDebugMessage implements DebugMessage {

    private int sendFrom;

    public ImplDebugMessage(int sendFrom){

        this.sendFrom = sendFrom;
    }

    @Override
    public String getDebuggingInformation() {
        return "This message was send from: " + sendFrom;
    }
}
