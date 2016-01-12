package it.unipd.math.pcd.actors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by davide on 12/01/16.
 */
public class ConcreteActorSystem extends AbsActorSystem {

    ExecutorService threadManager;

    ConcreteActorSystem() {

        int aviableProcessors = Runtime.getRuntime().availableProcessors();

        threadManager = Executors.newFixedThreadPool(aviableProcessors);
    }

    public void add(Runnable task){

        //MEMO: see http://stackoverflow.com/questions/3929342/choose-between-executorservices-submit-and-executorservices-execute
        threadManager.submit(task);
    }


    @Override
    protected ActorRef createActorReference(ActorMode mode){

        if (mode == ActorMode.LOCAL){

            //this cause I have only one ActorSystem
            return new FinalActorRef(this);
        } else
            return null; //no distributed version
    }
}
