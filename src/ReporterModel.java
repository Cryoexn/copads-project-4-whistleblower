
import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;

public class ReporterModel implements LeakerListener {

    private BigInteger exp;
    private BigInteger m;
    private OAEP oaep;
    private byte[] seed;

    public ReporterModel(File privateKeyFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(privateKeyFile));

            exp = new BigInteger(br.readLine());
            m = new BigInteger(br.readLine());

            oaep = new OAEP();
            seed = new byte[] {
                    0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0
            };

        } catch (IOException e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
            System.exit(1);
        }
    }

    @Override
    public void reportMsg(BigInteger msg) throws IOException {
        System.out.println("Encrypted: " + msg);
        BigInteger decrypted = msg.modPow(exp, m);
        System.out.println("Decrypted: " + decrypted);
        System.out.println("Message  : " + oaep.decode(decrypted));
    }
}
