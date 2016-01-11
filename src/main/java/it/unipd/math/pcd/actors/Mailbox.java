package it.unipd.math.pcd.actors;

/**
 * Mailbox for actors
 *
 * @author Polonio Davide
 */
public interface Mailbox extends Runnable {

  Packet pop();
  void put(Packet newPacket);
  void clear();
  int size();
}
