package it.unipd.math.pcd.actors;

/**
 * Created by davide on 11/01/16.
 */
public final class FinalActorRef<T extends Message> extends ImprovedActorRef<T> {

    FinalActorRef(AbsActorSystem absSystem){

        super(absSystem);
    }

    //TODO: implement send
    @Override
    public void send(T message, ActorRef to){

        //...
    }
}
