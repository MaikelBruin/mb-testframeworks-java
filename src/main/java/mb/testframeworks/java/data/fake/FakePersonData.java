package mb.testframeworks.java.data.fake;

import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mb.testframeworks.java.utils.StringUtils;

import java.util.Date;
import java.util.Locale;


@Slf4j
@Getter
@Setter
public class FakePersonData {

    protected String testEnv;
    protected Faker fakerNl = new Faker(new Locale("nl"));
    protected Faker fakerUs = new Faker(new Locale("us"));
    protected Faker fakerPl = new Faker(new Locale("pl"));
    protected Faker fakerZa = new Faker(new Locale("za"));
    protected String firstName;
    protected String lastName;
    protected Date birthDate;
    protected String nationalPhoneNumber;
    protected String internationalPhoneNumber;
    protected String street;
    protected String houseNumber;
    protected String postalCode;
    protected String city;
    protected String country;
    protected String countryCode;
    protected String email;

    public FakePersonData(String testEnv) {
        setTestEnv(testEnv);
        setBirthDate(fakerNl.date().birthday());
        setAddress("NL");
        setNameDetails("NL");
        setRandomEmail();
        setPhoneNumbers("06" + fakerNl.number().numberBetween(10000000, 59000000));
    }

    public void setRandomEmail() {
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase().replaceAll("\\s+", "") + "." + fakerNl.random().hex(10).toLowerCase();
        String domain = "mb-testframeworks-" + testEnv + "-" + ".nl";
        setEmail(email + "@" + domain);
    }

    public void setPhoneNumbers(String nationalPhoneNumber) {
        if (nationalPhoneNumber == null || !nationalPhoneNumber.matches("^06\\d{8}$")) {
            throw new IllegalArgumentException("National phone number must be a 10-digit number starting with '06'.");
        }
        this.nationalPhoneNumber = nationalPhoneNumber;
        this.internationalPhoneNumber = "+31" + nationalPhoneNumber.substring(1);
    }

    public void setNameDetails(String countryCode){
        switch (countryCode.toUpperCase()){
            case "NL":
                this.firstName = StringUtils.stripAccents(fakerNl.name().firstName());
                this.lastName = StringUtils.stripAccents(fakerNl.name().lastName());
                break;
            case "PL":
                this.firstName = StringUtils.stripAccents(fakerPl.name().firstName());
                this.lastName = StringUtils.stripAccents(fakerPl.name().lastName());
                break;
            case "US":
                this.firstName = StringUtils.stripAccents(fakerUs.name().firstName());
                this.lastName = StringUtils.stripAccents(fakerUs.name().lastName());
                break;
            case "ZA":
                this.firstName = StringUtils.stripAccents(fakerZa.name().firstName());
                this.lastName = StringUtils.stripAccents(fakerZa.name().lastName());
                break;
            default: throw new IllegalArgumentException ("Unexpected value for countryCode: " + countryCode);
        }
    }

    public void setAddress(String countryCode){
        switch (countryCode.toUpperCase()){
            case "NL":
                this.postalCode = fakerNl.address().zipCode();
                this.city = fakerNl.address().city();
                this.street = fakerNl.address().streetName();
                this.houseNumber = fakerNl.address().streetAddressNumber();
                this.country = countryCode.toUpperCase();
                this.countryCode = countryCode.toUpperCase();
                break;
            case "PL":
                this.postalCode = fakerPl.address().zipCode();
                this.city = fakerPl.address().city();
                this.street = fakerPl.address().streetName();
                this.houseNumber = fakerPl.address().streetAddressNumber();
                this.country = countryCode.toUpperCase();
                this.countryCode = countryCode.toUpperCase();
                break;
            case "US":
                this.postalCode = fakerUs.address().zipCode();
                this.city = fakerUs.address().city();
                this.street = fakerUs.address().streetName();
                this.houseNumber = fakerUs.address().streetAddressNumber();
                this.country = countryCode.toUpperCase();
                this.countryCode = countryCode.toUpperCase();
                break;
            case "ZA":
                this.postalCode = fakerZa.address().zipCode();
                this.city = fakerZa.address().city();
                this.street = fakerZa.address().streetName();
                this.houseNumber = fakerZa.address().streetAddressNumber();
                this.country = countryCode.toUpperCase();
                this.countryCode = countryCode.toUpperCase();
                break;
            default: throw new IllegalArgumentException ("Unexpected value for countryCode: " + countryCode);
        }
    }
}
