package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

public class Container {
    private String uuid;
    private String dateTime;
    private BigDecimal randomNumber1;
    private BigDecimal randomNumber2;
    private BigDecimal roundedResult;
    private String calculationResult;
    private String md5Hash;
    public Container() {}
    public Container(String uuid, String dateTime, BigDecimal randomNumber1,
                     BigDecimal randomNumber2, BigDecimal roundedResult,
                     String calculationResult, String md5Hash) {
        this.uuid = uuid;
        this.dateTime = dateTime;
        this.randomNumber1 = randomNumber1;
        this.randomNumber2 = randomNumber2;
        this.roundedResult = roundedResult;
        this.calculationResult = calculationResult;
        this.md5Hash = md5Hash;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public BigDecimal getRandomNumber1() {
        return randomNumber1;
    }

    public void setRandomNumber1(BigDecimal randomNumber1) {
        this.randomNumber1 = randomNumber1;
    }

    public BigDecimal getRandomNumber2() {
        return randomNumber2;
    }

    public void setRandomNumber2(BigDecimal randomNumber2) {
        this.randomNumber2 = randomNumber2;
    }

    public BigDecimal getRoundedResult() {
        return roundedResult;
    }

    public void setRoundedResult(BigDecimal roundedResult) {
        this.roundedResult = roundedResult;
    }

    public String getCalculationResult() {
        return calculationResult;
    }

    public void setCalculationResult(String calculationResult) {
        this.calculationResult = calculationResult;
    }

    public String getMd5Hash() {
        return md5Hash;
    }

    public void setMd5Hash(String md5Hash) {
        this.md5Hash = md5Hash;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException var3) {
            return super.toString();
        }
    }
}
