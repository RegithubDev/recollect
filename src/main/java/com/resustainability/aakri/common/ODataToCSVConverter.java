package com.resustainability.aakri.common;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
//Add these imports at the top of your ODataToCSVConverter class
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
public class ODataToCSVConverter {

    private static final String USERNAME = "24000197";
    private static final String PASSWORD = "Resus@25";
    private static final String BASE_URL = "https://cesufiappr01.resustainability.com/sap/opu/odata/sap/ZCDS_ZOHO_CDS/ZCDS_ZOHO(p_date=datetime'2025-11-12T00:00:00')/Set";
    private static final int BATCH_SIZE = 10000;

    // GCS Configuration - CORRECTED BUCKET NAME
    private static final String BUCKET_NAME = "zoho_analytics";
    private static final String CREDENTIALS_FILE = "D:\\nginx-1.24.0\\html\\reirm\\resources\\ai\\reel-group-it-897a06eab4dc.json";

    // Define the columns you want in CSV
    private static final String[] COLUMNS = {
        "p_date", "rldnr", "racct", "aufnr", "rcntr", "prctr", "hsl", "msl",
        "fiscyearper", "blart", "bldat", "xsplitmod", "rbukrs", "usnam",
        "timestamp", "matnr", "sgtxt", "ktext", "butxt", "name1", "aufex",
        "user6", "TEXT", "belnr", "budat", "werks", "docln", "ryear", "awref", "runit"
    };

    public static void main(String[] args) {
        try {
            System.out.println("Starting OData to CSV conversion...");
            System.out.println("Target GCS Bucket: " + BUCKET_NAME);

            String currentDir = System.getProperty("user.dir");
            System.out.println("Current working directory: " + currentDir);

            int totalRecords = getTotalRecordCount();
            System.out.println("Total records found: " + totalRecords);

            if (totalRecords == 0) {
                System.out.println("No records found. Exiting.");
                return;
            }

            File csvFile = processDataInBatches(totalRecords);

            if (csvFile != null && csvFile.exists()) {
                System.out.println("CSV file created: " + csvFile.getAbsolutePath());
                System.out.println("File size: " + csvFile.length() + " bytes");
                verifyAndUploadToGCS(csvFile);
            } else {
                System.out.println("CSV file not found, skipping GCS upload.");
            }

            System.out.println("Process completed successfully!");

        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void verifyAndUploadToGCS(File csvFile) throws Exception {
        System.out.println("Starting upload to Google Cloud Storage...");
        
        Storage storage = getStorageFromFile();
        
        // Verify bucket access
        try {
            Bucket bucket = storage.get(BUCKET_NAME);
            if (bucket != null) {
                System.out.println("✓ Bucket '" + BUCKET_NAME + "' exists and is accessible");
                System.out.println("  Location: " + bucket.getLocation());
                System.out.println("  Storage Class: " + bucket.getStorageClass());
            }
        } catch (StorageException e) {
            System.err.println("✗ Error accessing bucket: " + e.getMessage());
            System.err.println("Please verify:");
            System.err.println("1. Bucket name: " + BUCKET_NAME);
            System.err.println("2. Service account permissions");
            System.err.println("3. Bucket exists in project: reel-group-it");
            throw e;
        }
        
        String destinationBlobName = "sap-data/" + csvFile.getName();
        BlobId blobId = BlobId.of(BUCKET_NAME, destinationBlobName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType("text/csv")
                .build();

        try {
            System.out.println("Uploading file to: gs://" + BUCKET_NAME + "/" + destinationBlobName);
            storage.create(blobInfo, Files.readAllBytes(csvFile.toPath()));
            System.out.println("✓ File uploaded successfully to: gs://" + BUCKET_NAME + "/" + destinationBlobName);
            
            // Verify the file was uploaded
            Blob blob = storage.get(blobId);
            if (blob != null && blob.exists()) {
                System.out.println("✓ Upload verification successful - file exists in bucket");
                System.out.println("  File size in GCS: " + blob.getSize() + " bytes");
                System.out.println("  Created: " + blob.getCreateTime());
            }
            
            boolean deleted = csvFile.delete();
            if (deleted) {
                System.out.println("✓ Local file deleted: " + csvFile.getName());
            } else {
                System.out.println("⚠ Could not delete local file: " + csvFile.getAbsolutePath());
            }
        } catch (StorageException e) {
            System.err.println("✗ Failed to upload file: " + e.getMessage());
            System.err.println("Keeping local file: " + csvFile.getAbsolutePath());
            throw e;
        }
    }

    private static Storage getStorageFromFile() throws Exception {
        File credentialsFile = new File(CREDENTIALS_FILE);
        if (!credentialsFile.exists()) {
            throw new FileNotFoundException("Service account file not found: " + CREDENTIALS_FILE);
        }

        System.out.println("Using service account file: " + CREDENTIALS_FILE);
        
        try (FileInputStream serviceAccountStream = new FileInputStream(credentialsFile)) {
            Storage storage = StorageOptions.newBuilder()
                    .setCredentials(ServiceAccountCredentials.fromStream(serviceAccountStream))
                    .setProjectId("reel-group-it") // Your project ID
                    .build()
                    .getService();
            System.out.println("✓ GCS client initialized successfully");
            return storage;
        }
    }

    private static File processDataInBatches(int totalRecords) throws Exception {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String csvFileName = "odata_export_" + timestamp + ".csv";
        File csvFile = new File(csvFileName);

        try (FileWriter csvWriter = new FileWriter(csvFile)) {
            csvWriter.write('\ufeff'); // BOM for Excel

            writeEnhancedCSVHeader(csvWriter);

            int processedRecords = 0;
            int batchNumber = 1;

            while (processedRecords < totalRecords) {
                System.out.println("Processing batch " + batchNumber + "...");

                String batchUrl = buildBatchUrl(processedRecords);
                String response = executeHttpRequest(batchUrl);

                JSONObject jsonResponse = new JSONObject(response);
                JSONObject d = jsonResponse.getJSONObject("d");
                JSONArray results = d.getJSONArray("results");

                int batchRecordCount = results.length();
                System.out.println("Batch " + batchNumber + " contains " + batchRecordCount + " records");

                for (int i = 0; i < batchRecordCount; i++) {
                    JSONObject record = results.getJSONObject(i);
                    writeEnhancedRecordToCSV(csvWriter, record);
                }

                processedRecords += batchRecordCount;
                batchNumber++;

                System.out.println("Processed " + processedRecords + " of " + totalRecords + " records");

                if (processedRecords < totalRecords) {
                    Thread.sleep(1000); // Rate limiting
                }
            }

            writeExcelStylingInstructions(csvWriter, processedRecords);
            
            System.out.println("✓ CSV file completed: " + csvFile.getName());
            System.out.println("  Total records written: " + processedRecords);
        }

        return csvFile;
    }

    private static int getTotalRecordCount() throws Exception {
        String countUrl = BASE_URL + "?$format=json&$inlinecount=allpages" +
                "&$filter=rbukrs%20ge%20%273106%27%20and%20rbukrs%20le%20%273107%27%20and%20rldnr%20eq%20%270L%27%20and%20werks%20eq%20%273766%27";

        System.out.println("Fetching total record count...");
        String response = executeHttpRequest(countUrl);
        JSONObject jsonResponse = new JSONObject(response);
        JSONObject d = jsonResponse.getJSONObject("d");

        return d.getInt("__count");
    }

    private static void writeEnhancedCSVHeader(FileWriter csvWriter) throws IOException {
        csvWriter.append("# FILE GENERATED: ").append(new Date().toString()).append("\n");
        csvWriter.append("# TOTAL RECORDS: [AUTO_FILLED]").append("\n");
        csvWriter.append("# INSTRUCTIONS: Apply Excel Table formatting").append("\n");
        csvWriter.append("# COLUMNS: ").append(String.valueOf(COLUMNS.length)).append("\n\n");

        for (int i = 0; i < COLUMNS.length; i++) {
            csvWriter.append(escapeCSV(COLUMNS[i]));
            if (i < COLUMNS.length - 1) csvWriter.append(",");
        }
        csvWriter.append("\n");
    }

    private static void writeEnhancedRecordToCSV(FileWriter csvWriter, JSONObject record) throws IOException {
        for (int i = 0; i < COLUMNS.length; i++) {
            String column = COLUMNS[i];
            String value = "";

            if (record.has(column) && record.get(column) != JSONObject.NULL) {
                value = formatEnhancedValue(column, record.get(column));
            }

            csvWriter.append(escapeCSV(value));
            if (i < COLUMNS.length - 1) csvWriter.append(",");
        }
        csvWriter.append("\n");
    }

    private static String formatEnhancedValue(String column, Object value) {
        if (column.matches("p_date|bldat|budat|timestamp")) {
            return formatSAPDate(value.toString());
        }
        if (column.equals("hsl") || column.equals("msl")) {
            return formatNumericValue(value);
        }
        if (column.equals("fiscyearper")) {
            return formatFiscalYear(value.toString());
        }
        return value.toString().trim();
    }

    private static String formatSAPDate(String dateValue) {
        if (!dateValue.startsWith("/Date(")) return dateValue;
        try {
            String timestampStr = dateValue.substring(6, dateValue.indexOf(")")).split("[+-]")[0];
            long timestamp = Long.parseLong(timestampStr);
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
        } catch (Exception e) {
            return "DATE_ERROR";
        }
    }

    private static String formatNumericValue(Object value) {
        try {
            double num = value instanceof Number ? ((Number) value).doubleValue() : Double.parseDouble(value.toString());
            return num == (long) num ? String.format("%d", (long) num) : String.format("%.2f", num);
        } catch (Exception e) {
            return value.toString();
        }
    }

    private static String formatFiscalYear(String fiscalYear) {
        return fiscalYear.length() == 8 ? fiscalYear.substring(4) + "-" + fiscalYear.substring(2, 4) : fiscalYear;
    }

    private static void writeExcelStylingInstructions(FileWriter csvWriter, int totalRecords) throws IOException {
        csvWriter.append("\n# EXCEL TIPS:\n");
        csvWriter.append("# 1. Select data → Home → Format as Table\n");
        csvWriter.append("# 2. Freeze top row\n");
        csvWriter.append("# 3. Format dates & numbers\n");
        csvWriter.append("# Total Records: ").append(String.valueOf(totalRecords)).append("\n");
    }

    private static String buildBatchUrl(int skip) {
        return BASE_URL + "?$format=json" +
                "&$filter=rbukrs%20ge%20%273106%27%20and%20rbukrs%20le%20%273107%27%20and%20rldnr%20eq%20%270L%27%20and%20werks%20eq%20%273766%27" +
                "&$top=" + BATCH_SIZE + "&$skip=" + skip;
    }

    private static String escapeCSV(String value) {
        if (value == null || value.isEmpty()) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    private static String executeHttpRequest(String url) throws Exception {
        CredentialsProvider creds = new BasicCredentialsProvider();
        creds.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USERNAME, PASSWORD));

        SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(TrustAllStrategy.INSTANCE).build();
        SSLConnectionSocketFactory sslFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

        try (CloseableHttpClient client = HttpClients.custom()
                .setDefaultCredentialsProvider(creds)
                .setSSLSocketFactory(sslFactory)
                .build()) {

            HttpGet request = new HttpGet(url);
            request.addHeader("Accept", "application/json");

            HttpResponse response = client.execute(request);
            int status = response.getStatusLine().getStatusCode();
            if (status != 200) {
                throw new RuntimeException("HTTP " + status + ": " + response.getStatusLine().getReasonPhrase());
            }
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        }
    }
}