//-------------------------------------------------------------
// File  : Reporter.java
// Date  : 04/28/2021
// Author: David Pitoniak (dhp6397@rit.edu)
// Description: Receives, decrypts, and decodes encrypted messages
//              from a Leaker host/port.
//-------------------------------------------------------------

import java.io.File;
import java.net.*;

/**
 * Receives an encrypted message from Leaker, and uses
 * privatekeyfile to decrypt message.
 */
public class Reporter {

    /**
     * Create a DatagramSocket on rhost, rport to receive encrypted messages
     *  From a Leaker.
     *
     * @param args arguments to be parsed.
     */
    public static void main(String[] args) {

        // Check that there are the correct number of arguments.
        if(args.length != 3) usage();

        try {
            // Create DatagramSocket at host and port.
            String hostname = args[0];
            int port = Integer.parseInt(args[1]);
            DatagramSocket repSoc = new DatagramSocket(new InetSocketAddress(hostname, port));

            // Create Reporter model to handle messages from Leaker(s).
            ReporterModel model = new ReporterModel(new File(args[2]));

        } catch (SocketException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    } // end main.

    /**
     * Print the usage message and exit.
     */
    private static void usage() {
        System.err.println("Missing arguments\nUsage: java Reporter <rhost> <rport> <privatekeyfile>");
        System.exit(1);
    } // end usage.

} // end Reporter.
