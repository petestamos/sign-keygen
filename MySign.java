import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import java.security.*;

public class MySign {

  public static void main(String[] args) throws Exception {

    if (!(args.length == 2)) {
      System.out.println("Invalid Flag");
      return;
    }

    String flag = args[0].toLowerCase();
    String filename = args[1];

    switch (flag) {

      case "s":
          sign(filename);
          break;

      case "v":
          verify(filename);
          break;

      default:
          System.out.println("Invalid Flag");
          return;
    }
  }

///////////////////////////////////////////////////////////////////////////////

  public static void sign(String filename) throws Exception {

    File file = new File(filename);
    Scanner input = new Scanner(file);

    ArrayList<String> list = new ArrayList<>();
    StringBuilder build = new StringBuilder();

    while (input.hasNext()) {
      String x = input.nextLine();
      build.append(x);
      list.add(x);
    }

    MessageDigest message = MessageDigest.getInstance("SHA-256");
    BigInteger hash = new BigInteger(message.digest(build.toString().getBytes())).abs();

    try {

      File priv = new File("privkey.rsa");
      Scanner scanner = new Scanner(priv);

      BigInteger d = new BigInteger(scanner.nextLine());
      BigInteger n = new BigInteger(scanner.nextLine());

      BigInteger decrpytion = hash.modPow(d,n);

      FileWriter signed = new FileWriter(filename + ".signed");
      signed.write(decrpytion + "\n");

      for (String line : list) {
          signed.write(line + "\n");
      }

      signed.close();
    }

    catch (Exception e) {
      System.out.println(e);
    }
  }

///////////////////////////////////////////////////////////////////////////////

  public static void verify(String filename) throws Exception {

    if (!filename.toLowerCase().contains(".signed")) {
      System.out.println("Invalid File");
      return;
    }

    File file = new File(filename);
    Scanner scanner = new Scanner(file);

    ArrayList<String> list = new ArrayList<>();
    StringBuilder builder = new StringBuilder();

    BigInteger decryption = new BigInteger(scanner.nextLine());

    while (scanner.hasNext()) {
      String line = scanner.nextLine();
      builder.append(line);
      list.add(line);
    }

    MessageDigest message = MessageDigest.getInstance("SHA-256");
    BigInteger hash = new BigInteger(message.digest(builder.toString().getBytes())).abs();

    try {

      File pub = new File("pubkey.rsa");
      Scanner scanner2 = new Scanner(pub);

      BigInteger e = new BigInteger(scanner2.nextLine());
      BigInteger n = new BigInteger(scanner2.nextLine());

      BigInteger encryption = decryption.modPow(e, n);

      if (hash.equals(encryption)) {
          System.out.println("Valid");
      }
      else {
          System.out.println("Invalid");
      }
    }

    catch (Exception e) {
      System.out.println(e);
    }
  }
}
