import java.io.*;
import java.math.BigInteger;
import java.net.*;

public class Leaker implements LeakerListener {
    public static void main(String[] args) {
        if(args.length != 6) {
            usage();
        } else {
            try {
                String repHostname = args[0];
                int repPort = Integer.parseInt(args[1]);

                String lkrHostname = args[2];
                int lkrPort = Integer.parseInt(args[3]);

                DatagramSocket dgSoc = new DatagramSocket(new InetSocketAddress(lkrHostname, lkrPort));

                ReporterProxy proxy = new ReporterProxy(dgSoc, new InetSocketAddress(repHostname, repPort));

                File repPub = new File(args[4]);

                BufferedReader br = new BufferedReader(new FileReader(repPub));

                BigInteger exp = new BigInteger(br.readLine());
                BigInteger m = new BigInteger(br.readLine());

                String lkrMsg = args[5];

                BigInteger encodedMsg = encodeMsg(lkrMsg);
                BigInteger encryptedMsg = encryptMsg(encodedMsg, exp, m);

                dgSoc.send(new DatagramPacket(encryptedMsg.toByteArray(), encodedMsg.toByteArray().length, InetAddress.getByName(repHostname), repPort));

                dgSoc.close();

            } catch (SocketException | UnknownHostException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    } // end main.

    public static BigInteger encodeMsg(String msg) {

        OAEP oaep = new OAEP();

        byte[] seed = new byte[] {
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };

        return oaep.encode(msg, seed);

    } // end createPayload.

    public static BigInteger encryptMsg(BigInteger encoded, BigInteger exp, BigInteger m) {
        return encoded.modPow(exp, m);
    } // end encryptRSA.

    /**
     * Print the usage message and exit.
     */
    public static void usage() {
        System.err.println("Usage: java Leaker <rhost> <rport> <lhost> <lport> <publickeyfile> <message>");
        System.exit(1);
    } // end usage.

    @Override
    public void reportMsg(BigInteger msg) throws IOException {

    }
} // end Leaker.
