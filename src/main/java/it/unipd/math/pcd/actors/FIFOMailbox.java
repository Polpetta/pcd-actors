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
   * Sincronized Queue
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

    //poll is a synchronize method
    return queue.poll();
  }

  /**
   *
   * Put a element at the tail of queue
   * @param newPacket
   */
  @Override
  public void put(Packet newPacket){

    try {

      //put is a synchronize method
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

    //clear is a synchronize method
    queue.clear();
  }

  /**
   *
   * @return The size of the queue
   */
  @Override
  public int size(){

    //size is a synchronize method
    return queue.size();
  }

}
