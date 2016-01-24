package it.unipd.math.pcd.actors;

import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by davide on 12/01/16.
 */
public class ConcreteActorSystem extends AbsActorSystem {

    private ExecutorService threadManager;
    private Map<ActorRef<?>, Future<?>> terminatorManager;


    public ConcreteActorSystem() {

        threadManager = Executors.newCachedThreadPool();
        terminatorManager = new ConcurrentHashMap<>();
    }

    public void add(Callable<Void> task, ActorRef<?> associateActorRef){

        Future<?> future = threadManager.submit(task);
        terminatorManager.put(associateActorRef, future);
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
        Future<?> toWait = terminatorManager.remove(toStop);

        if (toRemove == null || toWait == null){

            throw new NoSuchActorException();
        }

        stopAndWait(toRemove, toWait);
    }

    @Override
    public void stop(){

        for (Map.Entry<ActorRef<?>, Actor<?>> toStop : getMap().entrySet()) {

            getMap().remove(toStop);
            Future<?> toWait = terminatorManager.remove(toStop.getKey());

            stopAndWait((AbsActor)toStop.getValue(), toWait);

        }
    }

    private void stopAndWait(AbsActor actorToStop, Future<?> toWait) {

        actorToStop.stop(); //stop will put a message automatically in the MailBox

        try {

            if (toWait != null && toWait.isDone() == false){
                toWait.get(); // Waiting the thread...
            }
        } catch (InterruptedException | ExecutionException e) {

                e.printStackTrace();
        }
    }

    public void finalize() throws Throwable{

        threadManager.shutdown();
        stop();
        super.finalize();
    }
}