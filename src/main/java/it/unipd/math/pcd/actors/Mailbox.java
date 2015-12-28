package it.unipd.math.pcd.actors;

/**
 * Mailbox for actors
 *
 * @author Polonio Davide
 */
public interface Mailbox {

  Packet pop();
  void put(Packet newPacket);
  void clear();
  int size();
}
