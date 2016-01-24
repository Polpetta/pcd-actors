package it.unipd.math.pcd.actors.utils.actors;

import it.unipd.math.pcd.actors.AbsActor;
import it.unipd.math.pcd.actors.Message;
import it.unipd.math.pcd.actors.utils.messages.DummyMessage;

/**
 * Created by davide on 20/01/16.
 */
public class LongJobActor<T extends Message> extends AbsActor<T> {

    private final long TIME_TO_SLEEP = 5000;

    public LongJobActor(){

        System.out.println("LongJobActor Initialized");
    }

    @Override
    public void receive(T message) {

        if (message instanceof DummyMessage){
            try {

                System.out.println("Job Started");
                Thread.sleep(TIME_TO_SLEEP);
                ((DummyMessage) message).getDummyMessage();

                System.out.println("Job Stopped");

            }catch (InterruptedException e){

                e.printStackTrace();
            }
        }
    }
}
