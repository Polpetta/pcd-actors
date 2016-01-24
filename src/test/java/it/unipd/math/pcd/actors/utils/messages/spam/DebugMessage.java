package it.unipd.math.pcd.actors.utils.messages.spam;

import it.unipd.math.pcd.actors.Message;

/**
 * Created by davide on 24/01/16.
 */
public interface DebugMessage extends Message {

    public String getDebuggingInformation();
}
