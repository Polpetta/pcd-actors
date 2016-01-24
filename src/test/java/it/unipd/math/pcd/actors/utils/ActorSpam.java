package it.unipd.math.pcd.actors.utils;

import it.unipd.math.pcd.actors.ActorRef;
import it.unipd.math.pcd.actors.ActorSystem;
import it.unipd.math.pcd.actors.utils.actors.spam.SpamDebugActor;

import java.util.ArrayList;

/**
 * Created by davide on 24/01/16.
 */
public class ActorSpam {

    private final int ACTOR_NUM;
    private ArrayList<ActorRef<?>> spamFarm;

    public ActorSpam(final int actorNum, ActorSystem actorSystem){

        ACTOR_NUM = actorNum;
        spamFarm = new ArrayList<>();

        for (int i=0; i<ACTOR_NUM; i++){

            spamFarm.add(actorSystem.actorOf(SpamDebugActor.class));
        }
    }

    public ActorRef<?> getActor(int i){

        if (i < ACTOR_NUM){

            return spamFarm.get(i);
        }

        return null;
    }

    public int getACTOR_NUM(){

        return ACTOR_NUM;
    }
}
