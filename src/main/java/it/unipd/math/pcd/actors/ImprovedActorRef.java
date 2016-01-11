package it.unipd.math.pcd.actors;

/**
 * Created by davide on 11/01/16.
 */
public abstract class ImprovedActorRef<T extends Message> implements ActorRef<T> {

    protected AbsActorSystem absSystem;

    ImprovedActorRef( AbsActorSystem newSystem ){

        absSystem = newSystem;
    }

    @Override
    public int compareTo( ActorRef toCompare ) {

        //this control only if "this" ref == anotherEmployer ref. Am I sure this is ok?
        if (this == toCompare){

            return 0;
        }

        return 1;
    }
}
