package it.unipd.math.pcd.actors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by davide on 12/01/16.
 */
public class ConcreteActorSystem extends AbsActorSystem {

    ExecutorService threadManager;

    public ConcreteActorSystem() {

        int aviableProcessors = Runtime.getRuntime().availableProcessors();

        threadManager = Executors.newFixedThreadPool(aviableProcessors);
    }

    public void add(Callable<Void> task){

        //MEMO: see http://stackoverflow.com/questions/3929342/choose-between-executorservices-submit-and-executorservices-execute
        threadManager.submit(task);
    }


    @Override
    protected ActorRef createActorReference(ActorMode mode){

        if (mode == ActorMode.LOCAL){

            //this cause I have only one ActorSystem
            return new FinalActorRef(this);
        } else
            throw new IllegalArgumentException(); //no distributed version anymore
    }
}
