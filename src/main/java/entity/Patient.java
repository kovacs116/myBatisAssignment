package entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class Patient {

    private String SSN;
    private String firstName;
    private String lastName;
    private char sex;
    private LocalDateTime dateOfBirth;
    private String postalCode;
    private String city;

    public Patient(String SSN, String firstName, String lastName, char sex, LocalDateTime dateOfBirth, String postalCode, String city) throws IOException {
        this.setSSN(SSN);
        this.firstName = firstName;
        this.lastName = lastName;
        this.setSex(sex);
        this.setDateOfBirth(dateOfBirth);
        this.setPostalCode(postalCode);
        this.city = city;
    }
    public Patient(Consumer<Patient> builder) {
        builder.accept(this);
    }

    public Patient() {

    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) throws IOException {
        Integer SSNLength = SSN.length();

        try{

            Integer.parseInt(SSN);

            if (SSNLength.equals(9) ) {
                this.SSN = SSN;
            }
            else {
                System.out.println("Invalid SSN format");
            }
        }catch(Exception e){
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));
            System.out.println("Invalid SSN format");
            postalCode = reader.readLine();
            setSSN( postalCode );
        };
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        String lowerCaseSex = sex.toString().toLowerCase(Locale.ROOT);
        if( Pattern.matches("m|f|o", lowerCaseSex)){
            this.sex = sex;
        }
        else{
            System.out.println("Invalid sex! Please add valid character");
            System.out.println("m - male / f - female / o - other");
            Scanner sc = new Scanner(System.in);
            sex = sc.next().charAt(0);
            setSex(sex);
        }

    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime date) throws IOException {

        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //LocalDateTime dateOfBirth = LocalDateTime.parse(date, formatter);
            //Date dateOfBirth = new DateTime("yyyy/MM/dd").parse(date);
            this.dateOfBirth = date;
        } catch (Exception e) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));
            System.out.println("Invalid Date format");
            //date = reader.readLine();
            setDateOfBirth( date );
        }

    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) throws IOException {

        Integer postalCodeLength = postalCode.length();

        try{

            Integer.parseInt(postalCode);

            if (postalCodeLength.equals(4) ) {
                this.postalCode = postalCode;
            }
            else if( postalCodeLength.equals(0) ){
                this.postalCode = "0000";
            }
            else {
                System.out.println("Invalid Postal Code format");
                System.out.println("Press ENTER to skip");
            }


        }catch(Exception e){
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));
            System.out.println("Invalid Postal Code format");
            System.out.println("Press ENTER to skip");
            postalCode = reader.readLine();
            setPostalCode( postalCode );
        }

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "SSN='" + SSN + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", sex=" + sex +
                ", dateOfBirth=" + dateOfBirth +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
