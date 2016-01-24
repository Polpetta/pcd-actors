package it.unipd.math.pcd.actors;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * A concrete Mailbox's FIFO implementation
 *
 * @author Polonio Davide
 */
public class FIFOMailbox implements Mailbox {


  /**
   *
   * Synchronized Queue
   */
  private LinkedBlockingQueue<Packet> queue;

  public FIFOMailbox(){

    queue = new LinkedBlockingQueue<Packet>();
  }

  /**
   *
   * Return (and remove) the first Packet in the queue.
   * @return Packet or Null if the queue is empty
   */
  @Override
  public Packet pop(){

    try {
      return queue.take();
    }catch(InterruptedException e){

      e.printStackTrace();
    }

    return null;
  }

  /**
   *
   * Put a element at the tail of queue
   * @param newPacket
   */
  @Override
  public void put(Packet newPacket){

    try {

      queue.put(newPacket);
    }catch (InterruptedException e){

      e.printStackTrace();
    }
  }

  /**
   *
   * Clear the queue
   */
  @Override
  public void clear(){

    queue.clear();
  }

  /**
   *
   * @return The size of the queue
   */
  @Override
  public int size(){

    return queue.size();
  }

}
