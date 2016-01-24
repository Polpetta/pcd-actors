package it.unipd.math.pcd.actors.utils;

import it.unipd.math.pcd.actors.ActorSystem;

/**
 * Created by davide on 24/01/16.
 */
public class MessageCounter {

    ActorSystem actorSystem;
    Counter effectiveCounter;

    private long messageSent;
    private long messageNotSend;
    private long initialMessage;

    public MessageCounter(long messagePool, ActorSystem actorSystem){

        initialMessage = messagePool;
        messageNotSend = messagePool;
        messageSent = 0;
        this.actorSystem = actorSystem;
    }

    private void printMessageCounterInformation(){

        System.out.println("Statistics:");
        System.out.println("Initial message: " + initialMessage);
        System.out.println("Message sent: " + messageSent);
        System.out.println("Message remaining: " + messageNotSend);
        System.out.println("Total Message remaining after computation: " + (messageNotSend+messageSent));
    }


    public Counter getCounter() {

        synchronized (this){

            if (effectiveCounter == null) {
                effectiveCounter = new Counter() {
                    @Override
                    public synchronized boolean canSendMessage() {
                        if (messageNotSend != 0) {

                            messageSent++;
                            messageNotSend--;
                            return true;
                        }

                        printMessageCounterInformation();
                        //actorSystem.stop();
                        return false;
                    }
                };
            }
            return effectiveCounter;
        }
    }
}
