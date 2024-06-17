package org.example;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        generateData();
    }

    public static void generateData() {
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

        System.out.println("UUID: " + uuid);
        System.out.println("DateTime: " + dateTime);
        System.out.println("Random Number 1: " + randomNumber1);
        System.out.println("Random Number 2: " + randomNumber2);
        System.out.println("Rounded Result: " + roundedResult);
        System.out.println("Calculation Result: " + calculationResult);
        System.out.println("MD5 Hash: " + md5Hash);

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
}
