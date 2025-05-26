package javaguide.base;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Base64;
import java.util.UUID;

public class JWTTest {
    private long ttl = 60 * 60 * 1000 * 2;  // 2 hours
    private String secretKey = "admin";

    @Test
    public void jwt() {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                // header
                .setHeaderParam("typ", "JWT").setHeaderParam("alg", "HS256")
                // payload
                .claim("username", "xs").claim("role", "admin")
                //.setSubject("admin-test").setExpiration(new Date(System.currentTimeMillis() + ttl)).setId(UUID.randomUUID().toString())
                // signature
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
        System.out.println(jwtToken);
        // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
        // .eyJ1c2VybmFtZSI6InhzIiwicm9sZSI6ImFkbWluIn0
        // .zCDND2-oYQjXh7PmHe7TgeNOpUg7Z07IlJr6tAB1Rnk
        // 三部分：header.payload.signature

    }

    @Test
    public void JSON2Base64URL() throws NoSuchAlgorithmException, InvalidKeyException {
        String headerJson = "{\"typ\":\"JWT\",\"alg\":\"HS256\"}";
        String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(headerJson.getBytes());
        System.out.println(encoded);
        // Java代码伪示意
        String header = Base64.getUrlEncoder().withoutPadding().encodeToString("{\"typ\":\"JWT\",\"alg\":\"HS256\"}".getBytes());

        String payload = Base64.getUrlEncoder().withoutPadding().encodeToString("{\"username\":\"xs\",\"role\":\"admin\"}".getBytes());

        String signatureInput = header + "." + payload;

        // 计算签名
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secretKey.getBytes(), "HmacSHA256"));
        byte[] signatureBytes = mac.doFinal(signatureInput.getBytes());

        // Base64Url 编码签名
        String signature = Base64.getUrlEncoder().withoutPadding().encodeToString(signatureBytes);

        System.out.println("JWT = " + header + "." + payload + "." + signature);
    }
}
