import java.util.*;
import java.io.*;
import java.math.*;

public class MyKeyGen {

  public static void main(String[] args) throws IOException {

    BigInteger one = new BigInteger(String.valueOf(1));

    BigInteger p = new BigInteger(512, 1, new Random());
    BigInteger q = new BigInteger(512, 1, new Random());

    BigInteger n = p.multiply(q);

    BigInteger p_sub = p.subtract(one);
    BigInteger q_sub = q.subtract(one);

    BigInteger phi = p_sub.multiply(q_sub);

    BigInteger e =  new BigInteger(512, 1, new Random());

    while((phi.compareTo(e) != 1) || !(phi.gcd(e).equals(one))) {
        e =  new BigInteger(512, 1, new Random());
    }

    BigInteger d = e.modInverse(phi);

///////////////////////////////////////////////////////////////////////////////

    FileWriter pub = new FileWriter("pubkey.rsa");

    pub.write(e.toString() + "\n");
    pub.write(n.toString());

    pub.close();

    FileWriter priv = new FileWriter("privkey.rsa");

    priv.write(d.toString() + "\n");
    priv.write(n.toString());

    priv.close();
  }
}
