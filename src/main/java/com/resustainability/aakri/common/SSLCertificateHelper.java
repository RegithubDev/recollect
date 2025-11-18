package com.resustainability.aakri.common;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.io.*;
import java.net.URL;
import java.security.cert.CertificateException;

public class SSLCertificateHelper {
    
    public static void importCertificate(String urlString, String keystorePassword) throws Exception {
        // Extract host from URL
        URL url = new URL(urlString);
        String host = url.getHost();
        int port = url.getPort() != -1 ? url.getPort() : 443;
        
        // Create a custom trust store
        char[] password = keystorePassword.toCharArray();
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, password);
        
        // Get the certificate
        SSLContext context = SSLContext.getInstance("TLS");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);
        X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
        SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
        context.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory factory = context.getSocketFactory();
        
        try (SSLSocket socket = (SSLSocket) factory.createSocket(host, port)) {
            socket.setSoTimeout(10000);
            socket.startHandshake();
            System.out.println("Certificate is already trusted: " + host);
        } catch (SSLException e) {
            // Certificate is not trusted, we'll import it
        }
        
        // Import the certificate
        X509Certificate[] chain = tm.chain;
        if (chain == null) {
            System.out.println("Could not obtain server certificate chain");
            return;
        }
        
        System.out.println("Server sent " + chain.length + " certificate(s):");
        for (int i = 0; i < chain.length; i++) {
            X509Certificate cert = chain[i];
            System.out.println(" " + (i + 1) + " Subject " + cert.getSubjectDN());
            System.out.println("   Issuer  " + cert.getIssuerDN());
        }
        
        // Add certificate to keystore
        String alias = host + "-1";
        keyStore.setCertificateEntry(alias, chain[0]);
        
        // Save the keystore
        File file = new File("custom-keystore.jks");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            keyStore.store(fos, password);
        }
        
        System.setProperty("javax.net.ssl.trustStore", file.getAbsolutePath());
        System.setProperty("javax.net.ssl.trustStorePassword", keystorePassword);
        
        System.out.println("Certificate imported successfully to: " + file.getAbsolutePath());
    }
    
    private static class SavingTrustManager implements X509TrustManager {
        private final X509TrustManager tm;
        private X509Certificate[] chain;
        
        SavingTrustManager(X509TrustManager tm) {
            this.tm = tm;
        }
        
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
        
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            throw new UnsupportedOperationException();
        }
        
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            this.chain = chain;
            tm.checkServerTrusted(chain, authType);
        }
    }
}
