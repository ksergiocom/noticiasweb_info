package info.noticiasweb.auth.services;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JWTService {

    private static final String SECRET = "clave-super-secreta-de-minimo-32-caracteres";
    private static final String ISSUER = "https://ksergio.com";

    public String generateToken(String username) {
        try {
            JWSSigner signer = new MACSigner(SECRET);

            Date now = new Date();
            Date expiration = new Date(now.getTime() + 60 * 60 * 1000); // 1 hora

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .issuer(ISSUER)
                    .issueTime(now)
                    .expirationTime(expiration)
                    .claim("roles", List.of("ADMIN"))
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS256),
                    claimsSet
            );

            signedJWT.sign(signer);

            return signedJWT.serialize();

        } catch (KeyLengthException e) {
            throw new RuntimeException("La clave no tiene la longitud correcta", e);
        } catch (JOSEException e) {
            throw new RuntimeException("Ha ocurrido un error al firmar el token", e);
        }
    }

    public JWTClaimsSet validateToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            JWSVerifier verifier = new MACVerifier(SECRET);

            if (!signedJWT.verify(verifier)) {
                throw new RuntimeException("Firma JWT inválida");
            }

            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            if (!ISSUER.equals(claims.getIssuer())) {
                throw new RuntimeException("Issuer inválido");
            }

            Date expiration = claims.getExpirationTime();

            if (expiration == null || expiration.before(new Date())) {
                throw new RuntimeException("Token expirado");
            }

            return claims;

        } catch (ParseException | JOSEException e) {
            throw new RuntimeException("Token JWT inválido", e);
        }
    }
}