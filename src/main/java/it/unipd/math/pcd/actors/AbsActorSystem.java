/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Riccardo Cardin
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p/>
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */

/**
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
package it.unipd.math.pcd.actors;

import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;

import java.util.HashMap;
import java.util.Map;

/**
 * A map-based implementation of the actor system.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public abstract class AbsActorSystem implements ActorSystem {

    /**
     * Associates every Actor created with an identifier.
     */
    private Map<ActorRef<?>, Actor<?>> actors;

    @Override
    public ActorRef<? extends Message> actorOf(Class<? extends Actor> actor, ActorMode mode) {

        // ActorRef instance
        ActorRef<?> reference;
        try {
            // Create the reference to the actor
            reference = this.createActorReference(mode);
            // Create the new instance of the actor
            Actor actorInstance = ((AbsActor) actor.newInstance()).setSelf(reference);
            // Associate the reference to the actor
            actors.put(reference, actorInstance);

        } catch (InstantiationException | IllegalAccessException e) {
            throw new NoSuchActorException(e);
        }
        return reference;
    }

    @Override
    public ActorRef<? extends Message> actorOf(Class<? extends Actor> actor) {
        return this.actorOf(actor, ActorMode.LOCAL);
    }

    protected abstract ActorRef createActorReference(ActorMode mode);



    public AbsActorSystem() {

        actors = new HashMap<>();
    }

    public Actor<?> search(ActorRef<?> toSearch) throws NoSuchActorException {

        Actor founded = actors.get(toSearch);

        if (founded == null){

            //i have found nothing
            throw new NoSuchActorException();
        }

        return founded;
    }

    @Override
    public void stop(ActorRef<?> toStop){


        stop((AbsActor)actors.get(toStop));
        actors.remove(toStop);
        //Actor actorToStop = actors.get(toStop);

        /*if (actorToStop != null) {

            stop((AbsActor)actors.get(toStop));
            //remove actor from the map
            //Need a Join for waiting the finiscing job actor?
            actors.remove(toStop);
        } else
            throw new NoSuchActorException(); */


    }

    @Override
    public void stop() {

        for (Map.Entry<ActorRef<?>, Actor<?>> toStop : actors.entrySet()) {

            stop(((AbsActor)toStop.getValue()));
            actors.remove(toStop);
        }
    }

    private void stop(AbsActor actorToStop) {

        if (actorToStop == null){

            throw new NoSuchActorException();
        }

        synchronized (this){

            //are this actions executed atomically?
            actorToStop.clearMessages();
            actorToStop.stop();
            actorToStop.putInMailbox(new Packet(new DummyMessage(), null)); //I'm sure null here?!
        }
    }

    //dummy message
    private class DummyMessage implements Message {}
}