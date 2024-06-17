package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        // Generate data and output to JSON file
        generateDataAndWriteToJsonFile("data.json");
    }

    private static void generateDataAndWriteToJsonFile(String fileName) {
        Container container = generateContainer();
        writeContainerToJsonFile(container, fileName);
    }

    public static Container generateContainer() {
        // Step 1: Generate a random UUID without dashes and in uppercase
        String uuid = generateUpperCaseUUID();

        // Step 2: Generate the current date and time, formatted as “02 January 2018 21:25”
        String dateTime = generateFormattedDateTime();

        // Step 3: Generate two random decimal numbers between 1 and 1000 with a precision of 4
        BigDecimal randomNumber1 = generateRandomDecimal();
        BigDecimal randomNumber2 = generateRandomDecimal();

        // Step 4: Use the smaller one as the divisor, and divide the other generated decimal number
        BigDecimal minNumber = randomNumber1.min(randomNumber2);
        BigDecimal maxNumber = randomNumber1.max(randomNumber2);
        BigDecimal divisionResult = maxNumber.divide(minNumber, 4, RoundingMode.UP);

        // Step 5: Round up the result of the division to a precision of two decimal places
        BigDecimal roundedResult = divisionResult.setScale(2, RoundingMode.UP);

        // Step 6: Generate a string named “calculationResult” showing the calculation and rounding method
        String calculationResult = String.format("%s divided by %s is %s (rounded up)",
                maxNumber, minNumber, roundedResult);

        // Step 7: Generate an MD5 hash using (UUID + current date and time + rounded result) as the concatenated input
        String inputForHash = uuid + dateTime + roundedResult.toPlainString();
        String md5Hash = generateMD5Hash(inputForHash);

        // Create and return a Container object
        return new Container(uuid, dateTime, randomNumber1, randomNumber2,
                roundedResult, calculationResult, md5Hash);
    }

    private static String generateUpperCaseUUID() {
        return UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }

    private static String generateFormattedDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
        return now.format(formatter);
    }

    private static BigDecimal generateRandomDecimal() {
        return BigDecimal.valueOf(Math.random() * 999 + 1).setScale(4, RoundingMode.HALF_UP);
    }

    private static String generateMD5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 hashing failed", e);
        }
    }

    public static String containerToJsonString(Container container) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(container);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void writeContainerToJsonFile(Container container, String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        File file = new File(fileName);
        List<Container> containers = new ArrayList<>();

        // If the file exists, read the existing content
        if (file.exists()) {
            try {
                containers = objectMapper.readValue(file, new TypeReference<List<Container>>() {});
            } catch (IOException e) {
                System.err.println("Failed to read existing data from " + fileName + ": " + e.getMessage());
            }
        }

        // Add the new Container object to the list
        containers.add(container);

        // Convert the updated list to a JSON string
        String jsonString;
        try {
            jsonString = objectMapper.writeValueAsString(containers);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        // Write the JSON string to the file
        try (FileWriter fileWriter = new FileWriter(file, false)) { // Use 'false' to overwrite existing content
            fileWriter.write(jsonString);
            System.out.println("Data has been written to " + fileName);
        } catch (IOException e) {
            System.err.println("Failed to write data to " + fileName + ": " + e.getMessage());
        }
    }

}
