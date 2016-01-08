package it.unipd.math.pcd.actors;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * A concrete Mailbox's FIFO implementation
 *
 * @author Polonio Davide
 */
public class FIFOMailbox implements Mailbox {

  /*
  poll and put are synchronized methods, but there aren't between then
   */

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

    /*

    Here is better poll o take? take() wait until there is a element in the
    queue. poll() return null if no elements are in the queue
     */
    try {//I'm sure i need a try 'n catch here? Can I throw the exception?
      return queue.take();
    }catch(InterruptedException e){

      e.printStackTrace();
    }
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
