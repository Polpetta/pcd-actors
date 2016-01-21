package it.unipd.math.pcd.actors;

import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by davide on 12/01/16.
 */
public class ConcreteActorSystem extends AbsActorSystem {

    private ExecutorService threadManager;


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


    @Override
    public void stop(ActorRef toStop){

        AbsActor toRemove = (AbsActor) getMap().remove(toStop);

        if (toRemove == null){

            throw new NoSuchActorException();
        }

        stop(toRemove);

    }

    @Override
    public void stop(){

        for (Map.Entry<ActorRef<?>, Actor<?>> toStop : getMap().entrySet()) {

            getMap().remove(toStop);
            stop(((AbsActor)toStop.getValue()));

        }
    }

    private void stop(AbsActor actorToStop) {


        //are this actions executed atomically?
        //actorToStop.clearMessages();
        actorToStop.stop(); //stop will put a message automatically in the MailBox

        //I have to wait the thread here? YES
    }

    public void finalize() throws Throwable{

        stop();
        super.finalize();
    }
}