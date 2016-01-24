package it.unipd.math.pcd.actors.utils.actors.spam;

import it.unipd.math.pcd.actors.AbsActor;
import it.unipd.math.pcd.actors.utils.messages.spam.DebugMessage;

/**
 * Created by davide on 24/01/16.
 */
public abstract class DebugActor<T extends DebugMessage> extends AbsActor<T> {

    protected abstract void printDebugInformation(DebugMessage message);

    @Override
    public void receive(T message){

        printDebugInformation(message);
    }
}
