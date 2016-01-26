package it.unipd.math.pcd.actors;

/**
 * Mailbox for actors
 *
 * @author Polonio Davide
 */
public interface Mailbox {

  public Packet pop();
  public void put(Packet newPacket);
  public void clear();
  public int size();
}
