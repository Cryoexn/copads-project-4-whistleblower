
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class LeakerProxy {

    private final DatagramSocket soc;
    private LeakerListener leakerListener;

    public LeakerProxy(DatagramSocket soc) {
        this.soc = soc;
    }

    public void setLeakerListener(LeakerListener leakerListener) {
        this.leakerListener = leakerListener;

        new ReaderThread().start();
    }

    private class ReaderThread extends Thread {
        public void run() {
            try {
                byte[] payload = new byte[256];
                BigInteger msg;

                for(;;) {
                    DatagramPacket packet = new DatagramPacket(payload, payload.length);
                    soc.receive(packet);
                    leakerListener.reportMsg(new BigInteger(packet.getData()));
                }

            } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
            }
        }
    }
}
