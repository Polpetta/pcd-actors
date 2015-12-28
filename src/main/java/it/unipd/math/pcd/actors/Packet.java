package it.unipd.math.pcd.actors;

/**
 * Packet is a class that contains a Message his Sender
 *
 * @author Polonio Davide
 */
public class Packet {

  Message msg;
  ActorRef<? extends Message> sender;

  public Packet(Message msg, ActorRef<? extends Message> sender){

    this.msg = msg;
    this.sender = sender;
  }


  //getter methods
  public ActorRef<? extends Message> getActorRef(){

    return sender;
  }

  public Message getMessage(){

    return msg;
  }


  //setter methods
  public void setActorRef(ActorRef<? extends Message> newActorRef){

    sender = newActorRef;
  }

  public void setMessage(Message newMessage){

    msg = newMessage;
  }
}
