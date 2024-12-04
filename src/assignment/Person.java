package assignment;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Person implements Serializable {
    private String name;
    private int age;
    private String address;
    private String aadhaar;


    public Person(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAadhaar() {
        return aadhaar;
    }


    public void setAadhaar(String aadhaar) {
        if(isValidAadhaarNumber(aadhaar))
            this.aadhaar = aadhaar;
        else
            throw new IllegalArgumentException("Aadhaar number is invalid");
    }


    private static boolean
    isValidAadhaarNumber(String str)
    {
        // Regex to check valid Aadhaar number.
        String regex
                = "^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$";

        Pattern p = Pattern.compile(regex);


        if (str == null) {
            return false;
        }

        Matcher m = p.matcher(str);

        return m.matches();
    }


    @Override
    public String toString() {
        return "Name: " + name
                + ", Age: " + age
                + ", Address: " + address;
    }
}

