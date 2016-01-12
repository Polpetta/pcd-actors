package it.unipd.math.pcd.actors;

/**
 * Created by davide on 11/01/16.
 */
public final class FinalActorRef<T extends Message> extends ImprovedActorRef<T> {

    public FinalActorRef(AbsActorSystem absSystem){

        super(absSystem);
    }

    @Override
    public void send(T message, ActorRef to){


        Packet<T> packetToSend = new Packet<>(message, this);

        //send to the selected actor
        AbsActor toSend = (AbsActor)absSystem.search(to);
        toSend.putInMailbox(packetToSend);
    }
}
