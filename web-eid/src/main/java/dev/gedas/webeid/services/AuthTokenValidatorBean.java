package dev.gedas.webeid.services;

import eu.webeid.security.exceptions.JceException;
import eu.webeid.security.validator.AuthTokenValidator;
import eu.webeid.security.validator.AuthTokenValidatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class AuthTokenValidatorBean {

    private static final String CERTS_RESOURCE_PATH = "/certs";

    @Bean
    public AuthTokenValidator validator() {
        try {
            return new AuthTokenValidatorBuilder()
                    .withSiteOrigin(URI.create("https://localhost:5001"))
//                    .withSiteOrigin(URI.create("https://56ae-84-50-135-113.ngrok.io"))
                    .withTrustedCertificateAuthorities(loadTrustedCACertificatesFromCerFiles())
                    .build();
        } catch (JceException e) {
            throw new RuntimeException("Error building the Web eID auth token validator.", e);
        }
    }

    private X509Certificate[] loadTrustedCACertificatesFromCerFiles() {
        List<X509Certificate> caCertificates = new ArrayList<>();

        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(CERTS_RESOURCE_PATH + "/*.cer");

            for (Resource resource : resources) {
                X509Certificate caCertificate = (X509Certificate) certFactory.generateCertificate(resource.getInputStream());
                caCertificates.add(caCertificate);
            }

        } catch (CertificateException | IOException e) {
            throw new RuntimeException("Error initializing trusted CA certificates.", e);
        }

        return caCertificates.toArray(new X509Certificate[0]);
    }
}
