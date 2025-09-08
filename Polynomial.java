import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Polynomial {
    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("input.json"));
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        String json = sb.toString();

        int k = Integer.parseInt(json.replaceAll("(?s).*\"k\"\\s*:\\s*(\\d+).*", "$1"));

        List<BigInteger> roots = new ArrayList<>();
        String[] entries = json.split("\\},");
        for (String entry : entries) {
            if (!entry.contains("\"base\"")) continue;

            String baseStr = entry.replaceAll("(?s).*\"base\"\\s*:\\s*\"?(\\w+)\"?.*", "$1");
            String valueStr = entry.replaceAll("(?s).*\"value\"\\s*:\\s*\"?([0-9a-zA-Z]+)\"?.*", "$1");

            try {
                int base = Integer.parseInt(baseStr);
                BigInteger decimalVal = new BigInteger(valueStr, base);
                roots.add(decimalVal);
            } catch (Exception e) {
                
            }
        }

        roots = roots.subList(0, k);

        BigInteger[] coeffs = new BigInteger[k + 1];
        Arrays.fill(coeffs, BigInteger.ZERO);
        coeffs[0] = BigInteger.ONE;

        for (BigInteger root : roots) {
            BigInteger[] newCoeffs = new BigInteger[coeffs.length];
            Arrays.fill(newCoeffs, BigInteger.ZERO);
            for (int i = 0; i < coeffs.length; i++) {
                if (coeffs[i] == null) continue;
                if (i + 1 < coeffs.length) {
                    newCoeffs[i + 1] = newCoeffs[i + 1].add(coeffs[i]);
                }
                newCoeffs[i] = newCoeffs[i].add(coeffs[i].negate().multiply(root));
            }
            coeffs = newCoeffs;
        }

        System.out.println("Polynomial Coefficients:");
        for (int i = coeffs.length - 1; i >= 0; i--) {
            System.out.print(coeffs[i] + "x^" + i);
            if (i > 0) System.out.print(" + ");
        }
        System.out.println();
    }
}
