package it.unipd.math.pcd.actors.utils.actors.spam;

import it.unipd.math.pcd.actors.utils.messages.spam.DebugMessage;

/**
 * Created by davide on 24/01/16.
 */
public class SpamDebugActor<T extends DebugMessage> extends DebugActor<T> {

    @Override
    protected void printDebugInformation(DebugMessage message) {

        System.out.println("Message information " + message.getDebuggingInformation());
    }
}
