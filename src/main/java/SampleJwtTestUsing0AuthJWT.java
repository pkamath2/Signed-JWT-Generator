import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;

/**
 * Author: purnimakamath
 */
public class SampleJwtTestUsing0AuthJWT {

    private static String PATH_TO_PRIVATE_PEM = "<absolute path to your private key>";
    public static void main(String[] args) {
        new SampleJwtTestUsing0AuthJWT().generateJWTSignedToken();
    }

    private String generateJWTSignedToken() {
        String token = null;
        try(InputStream is = new FileInputStream(new File(PATH_TO_PRIVATE_PEM));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));) {

            StringBuilder builder = new StringBuilder();
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                builder.append(line);
            }
            byte[] privateKeyFileContentX509 = DatatypeConverter.parseBase64Binary(builder.toString());

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec x509keyspec = new PKCS8EncodedKeySpec(privateKeyFileContentX509);
            RSAPrivateKey privateKeyX509 = (RSAPrivateKey) keyFactory.generatePrivate(x509keyspec);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, 1);
            Date oneHourPlus = cal.getTime();

            Algorithm algorithm = Algorithm.RSA256(null, privateKeyX509);
            token = JWT.create()
                    .withAudience("Partners")
                    .withExpiresAt(oneHourPlus)
                    .withIssuer("DBS")
                    .withIssuedAt(new Date())
                    .withSubject("PK")
                    .withJWTId("StanderdJWTToken1")
                    .withClaim("PARTY_TYPE", "3")
                    .withClaim("CLIENT_ID", "FX-Trends")
                    .withClaim("CLIENT_TYPE", "Partner")
                    .withClaim("ACCESS", "Community")
                    .withClaim("jti", "StanderdJWTToken1")
                    .sign(algorithm);
            System.out.println("Signed JWT Token => "+token);
        }  catch(Exception ioe){
            ioe.printStackTrace();
            token = "Error";
        }
        return token;
    }
}
