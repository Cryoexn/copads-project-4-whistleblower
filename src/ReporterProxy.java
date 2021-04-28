//-------------------------------------------------------------
// File  : ReporterProxy.java
// Date  : 04/28/2021
// Author: David Pitoniak (dhp6397@rit.edu)
// Description: Proxy to communicate with Reporter(s).
//-------------------------------------------------------------

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * A proxy for Leaker(s) to communicate with Reporter(s).
 */
public class ReporterProxy implements LeakerListener {

    /** Socket to send DatagramPacket with message from. */
    private final DatagramSocket soc;

    /**
     * Constructor to set socket.
     *
     * @param soc DatagramSocket to send message from.
     */
    public ReporterProxy(DatagramSocket soc) {
        this.soc = soc;
    } // end Constructor.

    /**
     * Sends packet to the Reporter that the socket is bound to.
     *
     * @param packet packet with message to send.
     *
     * @throws IOException if the send fails.
     */
    @Override
    public void reportMsg(DatagramPacket packet) throws IOException {
        soc.send(packet);
    } // end reportMsg.

} // end ReporterProxy.
