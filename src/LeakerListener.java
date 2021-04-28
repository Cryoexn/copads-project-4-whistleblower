import java.io.IOException;
import java.math.BigInteger;

public interface LeakerListener {
    public void reportMsg(BigInteger msg) throws IOException;
}
