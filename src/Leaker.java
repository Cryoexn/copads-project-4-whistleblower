//-------------------------------------------------------------
// File  : Leaker.java
// Date  : 04/28/2021
// Author: David Pitoniak (dhp6397@rit.edu)
// Description: Sends public key encrypted messages to a Reporter host/port.
//-------------------------------------------------------------

import java.io.*;
import java.math.BigInteger;
import java.net.*;

/**
 * Sends an encrypted message to rhost, rport using
 * publickeyfile to encrypt message.
 */
public class Leaker {

    /**
     * Make sure there are the correct number of command line arguments.
     * Create a connection to the Reporter with DatagramSocket.
     * Send a DatagramPacket containing the encrypted message.
     *
     * @param args arguments to be parsed.
     */
    public static void main(String[] args) {

        // Check that there are the correct number of arguments.
        if(args.length != 6) usage();

        try {

            // Get Reporter host and port.
            String repHostname = args[0];
            int repPort = Integer.parseInt(args[1]);

            // Get Leaker host and port.
            String lkrHostname = args[2];
            int lkrPort = Integer.parseInt(args[3]);

            // Create a proxy to communicate with the ReporterModel.
            ReporterProxy proxy = new ReporterProxy(new DatagramSocket());

            // Get the public key components from file.
            File repPub = new File(args[4]);
            BufferedReader br = new BufferedReader(new FileReader(repPub));

            BigInteger e = null;
            BigInteger n = null;

            try {
                e = new BigInteger(br.readLine());
            } catch(NumberFormatException ex) {
                System.err.println("Couldn't read exponent value in public key file");
                System.exit(1);
            }

            try {
                n = new BigInteger(br.readLine());
            } catch(NumberFormatException ex) {
                System.err.println("Couldn't read modulus value in public key file");
                System.exit(1);
            }

            // Encode the message with OAEP.
            OAEP oaep = new OAEP();
            BigInteger encodedMsg = encodeMsg(args[5], oaep);

            // Encrypt the message with public key components.
            BigInteger encryptedMsg = encryptMsg(encodedMsg, e, n);

            // Create a byte buffer to store the encrypted message.
            byte[] buff = encryptedMsg.toByteArray();

            // Tell the ReporterProxy to send a message to the Reporter.
            proxy.reportMsg(new DatagramPacket(buff, buff.length, InetAddress.getByName(repHostname), repPort));

        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

    } // end main.

    /**
     * Encodes the message with Optimal Asymmetric Encryption Padding.
     *
     * @param msg message to encode.
     * @param oaep helper class to encode message.
     *
     * @return encoded message.
     */
    public static BigInteger encodeMsg(String msg, OAEP oaep) {
        byte[] seed = new byte[] {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        return oaep.encode(msg, seed);
    } // end createPayload.

    /**
     * Encrypt the encoded message using RSA public file e and n.
     *
     * @param encoded encoded message to be encrypted.
     * @param e exponent component of public key.
     * @param n mod component of public key.
     *
     * @return encrypted message using e, and n.
     */
    public static BigInteger encryptMsg(BigInteger encoded, BigInteger e, BigInteger n) {
        return encoded.modPow(e, n);
    } // end encryptRSA.

    /**
     * Print the usage message and exit.
     */
    public static void usage() {
        System.err.println("Missing arguments\nUsage: java Leaker <rhost> <rport> <lhost> <lport> <publickeyfile> <message>");
        System.exit(1);
    } // end usage.
} // end Leaker.
