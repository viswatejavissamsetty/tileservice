package com.viswateja.tileservice;

public class PhoneNumberService {
    public String getPhoneNumber() {
        FileOperationsService fs = new FileOperationsService();
        String phone = fs.readFromFile("phonenumber.txt");
        return phone;
    }
}
