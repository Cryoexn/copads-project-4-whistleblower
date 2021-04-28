import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

public class ReporterProxy implements LeakerListener {

    private DatagramSocket soc;
    private SocketAddress destination;

    public ReporterProxy(DatagramSocket soc, SocketAddress dest) {
        this.soc = soc;
        this.destination = dest;
    }

    @Override
    public void reportMsg(BigInteger msg) throws IOException {
        soc.send(new DatagramPacket(msg.toByteArray(), msg.toByteArray().length, destination));
    }
}
