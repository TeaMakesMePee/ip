package contacts;

public class Contact {
    private final String name;
    private final String phoneNumber;
    private final String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Contact(String name, String phoneNumber) {
        this(name, phoneNumber, null);
    }

    public String getFormattedContact() {
        return this.name + "|" + this.phoneNumber +
                (this.email != null ? "|" + this.email : "");
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\n"
                + "Ph No.: " + this.phoneNumber + "\n"
                + "E-mail: " + (this.email != null ? this.email : "N/A") + "\n";
    }
}
