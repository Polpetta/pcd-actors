package it.unipd.math.pcd.actors;

/**
 * ActorEmployer is the employer of an Actor
 * @author Polonio Davide
 */
public class ActorEmployer<T extends Message> extends Thread implements ActorRef<T> {

  private final Actor<T> actor;
  private final Mailbox mail;
  private final boolean haveToStop;

  public ActorEmployer(Actor<T> actor){

    this.actor = actor;
    mail = new FIFOMailbox();
    haveToStop = false;
  }

  public void stopActor(){

    mail.clear(); //clear next message
  }

  //TODO
  @Override
  public void send(T message, ActorRef to){

    /*
    Steps:

    1 - Build packet
    2 - Send it to ActorRef "to"

     */
    //need a method for have mailbox? for example to.getMailbox().put(packet)
  }

  @Override
  public int compareTo(ActorRef anotherEmployer){

    //this control only if "this" ref == anotherEmployer ref. Am I sure this is ok?
    if (this == anotherEmployer){

      return 0;
    }

    return 1;
  }

  @Override
  public void run(){

    while (mail.size() != 0){

      Packet<T> receivedPacket = mail.pop();

      actor.receive(receivedPacket.getMessage());
    }

    this.interrupt();
    //need someone that awake the thread when some message come

  }
}
