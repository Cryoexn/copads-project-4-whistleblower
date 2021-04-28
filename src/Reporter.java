
import java.io.File;
import java.net.*;

public class Reporter {
    public static void main(String[] args) {

        if(args.length != 3) usage();

        try {
            String hostname = args[0];
            int port = Integer.parseInt(args[1]);

            DatagramSocket repSoc = new DatagramSocket(new InetSocketAddress(hostname, port));

            ReporterModel model = new ReporterModel(new File(args[2]));
            LeakerProxy proxy = new LeakerProxy(repSoc);
            proxy.setLeakerListener(model);

        } catch (SocketException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Print the usage message and exit.
     */
    private static void usage() {
        System.err.println("Usage: java Reporter <rhost> <rport> <privatekeyfile>");
        System.exit(1);
    }
}
