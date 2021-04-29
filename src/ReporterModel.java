//-------------------------------------------------------------
// File  : ReporterModel.java
// Date  : 04/28/2021
// Author: David Pitoniak (dhp6397@rit.edu)
// Description: Model to accept, decrypt, and decode
//              messages from Leaker(s).
//-------------------------------------------------------------

import java.io.*;
import java.math.BigInteger;
import java.net.DatagramPacket;

/**
 * Model to handle encrypted messages sent to the Reporter.
 */
public class ReporterModel implements LeakerListener {

    /** Exponent component of private key used for decryption. */
    private BigInteger d;

    /** Mod component of private key used for decryption. */
    private BigInteger n;

    /** Helper class to decode the decrypted byte array. */
    private OAEP oaep;

    /**
     * Constructor to initialize the private key components and helper class.
     *
     * @param privateKeyFile file containing private key components.
     */
    public ReporterModel(File privateKeyFile) {
        try {
            // Read in private key components.
            BufferedReader br = new BufferedReader(new FileReader(privateKeyFile));

            try {
                d = new BigInteger(br.readLine());
            } catch(NumberFormatException ex) {
                System.err.println("Couldn't read exponent value in private key file");
                System.exit(1);
            }

            try {
                n = new BigInteger(br.readLine());
            } catch(NumberFormatException ex) {
                System.err.println("Couldn't read modulus value in private key file");
                System.exit(1);
            }

            // Create helper class to decode the message.
            oaep = new OAEP();

        } catch (IOException e) {
            System.err.printf("Error: %s (No such file or directory)\n", privateKeyFile);
            System.exit(1);
        }
    } // end Constructor.

    /**
     * Method called when a message has been sent to the Reporters DatagramSocket.
     *
     * @param packet packet containing message data.
     */
    @Override
    public void reportMsg(DatagramPacket packet) {
        try {
            BigInteger data = new BigInteger(packet.getData(), 0, packet.getLength());
            System.out.println(oaep.decode(data.modPow(d, n)));
        } catch (IllegalArgumentException e) {
            System.err.println("Error: private key does not math public key.");
        }
    } // end reportMsg.
} // end ReporterModel.
