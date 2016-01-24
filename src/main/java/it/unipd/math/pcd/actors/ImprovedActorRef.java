package it.unipd.math.pcd.actors;

import java.util.concurrent.Callable;

/**
 * Created by davide on 11/01/16.
 */
public abstract class ImprovedActorRef<T extends Message> implements ActorRef<T> {

    protected AbsActorSystem absSystem;

    public ImprovedActorRef( AbsActorSystem newSystem ){

        absSystem = newSystem;
    }

    public void sendTask(Callable<Void> task){

        ((ConcreteActorSystem)absSystem).add(task, this);
    }

    @Override
    public int compareTo( ActorRef toCompare ) {

        if (this == toCompare){

            return 0;
        }

        return 1;
    }
}
