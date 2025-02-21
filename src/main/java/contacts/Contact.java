package contacts;

/**
 * Represents a contact with a name, phone number, and an optional email address.
 */
public class Contact {
    private final String name;
    private final String phoneNumber;
    private final String email;

    /**
     * Constructs a Contact with a specified name, phone number, and email address.
     *
     * @param name        The name of the contact.
     * @param phoneNumber The phone number of the contact.
     * @param email       The email address of the contact.
     */
    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /**
     * Constructs a Contact with a specified name and phone number.
     * The email field is set to null.
     *
     * @param name        The name of the contact.
     * @param phoneNumber The phone number of the contact.
     */
    public Contact(String name, String phoneNumber) {
        this(name, phoneNumber, null);
    }

    /**
     * Returns a formatted string representation of the contact for storage.
     * Format: "name|phoneNumber|email"
     * If the email is null, it is omitted.
     *
     * @return A formatted string representation of the contact.
     */
    public String getFormattedContact() {
        return this.name + "|" + this.phoneNumber +
                (this.email != null ? "|" + this.email : "");
    }

    /**
     * Returns a human-readable string representation of the contact.
     *
     * @return A string containing the contact's name, phone number, and email.
     *         If the email is null, "N/A" is displayed instead.
     */
    @Override
    public String toString() {
        return "Name: " + this.name + "\n"
                + "Ph No.: " + this.phoneNumber + "\n"
                + "E-mail: " + (this.email != null ? this.email : "N/A") + "\n";
    }
}