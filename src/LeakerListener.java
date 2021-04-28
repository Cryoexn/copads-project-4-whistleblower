//-------------------------------------------------------------
// File  : LeakerListener.java
// Date  : 04/28/2021
// Author: David Pitoniak (dhp6397@rit.edu)
// Description: Interface to allow for DatagramPacker sending.
//-------------------------------------------------------------

import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Interface to allow for DatagramPacker sending.
 */
public interface LeakerListener {

    /**
     * Report that a message has been sent to listeners.
     *
     * @param packet packet containing message.
     *
     * @throws IOException if the message send fails.
     */
    void reportMsg(DatagramPacket packet) throws IOException;

} // end LeakerListener.
