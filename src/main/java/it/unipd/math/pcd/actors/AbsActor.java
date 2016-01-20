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

import java.util.concurrent.Callable;

/**
 * Defines common properties of all actors.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public abstract class AbsActor<T extends Message> implements Actor<T> {

    private enum internalStatus{

        initialized,
        running,
        stopped
    }

    /**
     * Self-reference of the actor
     */
    protected ActorRef<T> self;

    /**
     * Sender of the current message
     */
    protected ActorRef<T> sender;

    /**
     * mailbox where messages will be send
     */
    protected Mailbox mailbox;

    /**
     * flag that inform me about the internal status of my actor
     */
    private internalStatus status;

    /**
     * Task that pop message from the Mailbox and send to a concrete actor
     */
    private final Callable<Void> ActorEmployer;

    public AbsActor() {

        mailbox = new FIFOMailbox();

        //default null
        self = null;
        sender = null;
        status = internalStatus.initialized;

        ActorEmployer = new Callable() {
            @Override
            public Object call() throws Exception {
                while ( status == internalStatus.running ){

                    synchronized (this) {
                        Packet<T> toProcess = mailbox.pop();
                        if ( status == internalStatus.running) {

                            setSender((ActorRef<T>) toProcess.getActorRef());
                            receive(toProcess.getMessage());
                        }
                    }
                }

                return null; //here is better return a "new Void()"?
            }
        };

    }


    //setter
    protected final void setSender(ActorRef<T> sender){

        this.sender = sender;
    }

    /**
     * Sets the self-reference.
     *
     * @param self The reference to itself
     * @return The actor.
     */
    protected final Actor<T> setSelf(ActorRef<T> self) {
        this.self = self;
        return this;
    }

    public void stop(){

        synchronized (this) {

            mailbox.clear(); //no more messages will be processed
            putInMailbox(new Packet(new DummyMessage(), null)); //put a dummmy message on mailbox
            status = internalStatus.stopped; //set task to be stop and don't accept new messages anymore
        }
    }

    public void putInMailbox(Packet newPacket){

        synchronized (this) {

            if (status == internalStatus.initialized) {

                ((ImprovedActorRef<T>)self).sendTask(ActorEmployer);
                status = internalStatus.running;
            }


            //this need to be synchronize??
            if (status == internalStatus.running) {
                mailbox.put(newPacket);
            }
        }
    }

    private class DummyMessage implements Message{}

}
