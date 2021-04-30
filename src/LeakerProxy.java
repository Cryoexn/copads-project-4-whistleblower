//-------------------------------------------------------------
// File  : LeakerProxy.java
// Date  : 04/28/2021
// Author: David Pitoniak (dhp6397@rit.edu)
// Description: Proxy to communicate with Leaker(s).
//-------------------------------------------------------------

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * A proxy for Leaker(s) to communicate with Reporter(s).
 */
public class LeakerProxy {

    /** Socket to get message. */
    private final DatagramSocket soc;

    /** Listener to send message to. */
    private LeakerListener leakerListener;

    /**
     * Constructor to set the DatagramSocket.
     *
     * @param soc socket to get message.
     */
    public LeakerProxy(DatagramSocket soc) {
        this.soc = soc;
    } // end Constructor.

    /**
     * set the listener to be updated when a message is received.
     *
     * @param leakerListener listener to update.
     */
    public void setLeakerListener(LeakerListener leakerListener) {
        this.leakerListener = leakerListener;

        new ReaderThread().start();
    } // end setLeakerListener.

    /**
     * Helper class to receive packets from Leakers.
     */
    private class ReaderThread extends Thread {

        /**
         * Thread run function that infinitely accepts packets.
         */
        public void run() {
            try {
                byte[] payload = new byte[260];
                BigInteger msg;

                for(;;) {
                    DatagramPacket packet = new DatagramPacket(payload, payload.length);
                    soc.receive(packet);
                    leakerListener.reportMsg(packet);
                }

            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        } // end run.
    } // end ReaderThread.

} // end LeakerProxy.