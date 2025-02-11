package contacts;

public class Contact {
    private String name;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Contact(String name, String phoneNumber) {
        this(name, phoneNumber, null);
    }

    public String getName() { return this.name; }
    public String getPhoneNumber() { return this.phoneNumber; }
    public String getEmail() { return this.email; }

    public void setEmail(String email) { this.email = email; }

    public String getFormattedContact() {
        return this.name + "|" + this.phoneNumber +
                (this.email != null ? "|" + this.email : "");
    }

    @Override
    public String toString() {
        return "Contact {name='" + this.name + "', phone number ='" + this.phoneNumber + "', email='"
                + (this.email != null ? this.email : "N/A") + "'}";
    }
}
