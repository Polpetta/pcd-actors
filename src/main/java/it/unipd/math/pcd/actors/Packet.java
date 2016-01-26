package it.unipd.math.pcd.actors;

/**
 * Packet is a class that contains a Message his Sender
 *
 * @author Polonio Davide
 */
public class Packet<T extends Message> {

  private T msg;
  private ActorRef<? extends Message> sender;
  private boolean isAStoppingPacket;

  public Packet(T msg, ActorRef<? extends Message> sender){

    this.msg = msg;
    this.sender = sender;
    isAStoppingPacket = false;
  }

  Packet(T msg, ActorRef<? extends Message> sender, boolean isAStoppingPacket){ //only for members in the same package

    this(msg, sender);
    this.isAStoppingPacket = isAStoppingPacket;
  }


  //getter methods
  public ActorRef<? extends Message> getActorRef(){

    return sender;
  }

  public T getMessage(){

    return msg;
  }


  //setter methods
  public void setActorRef(ActorRef<? extends Message> newActorRef){

    sender = newActorRef;
  }

  public void setMessage(T newMessage){

    msg = newMessage;
  }

  public boolean isAStoppingPacket(){

    return isAStoppingPacket;
  }
}
