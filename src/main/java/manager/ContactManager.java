package manager;

import contacts.Contact;
import java.util.List;
import java.util.ArrayList;
import exception.TiffyException;

public class ContactManager {
    private final List<Contact> contacts;

    public ContactManager(List<String> contactData) {
        this.contacts = new ArrayList<>();
        for (String s : contactData) {
            String[] parts = s.split("\\|");
            if (parts.length == 2) {
                this.contacts.add(new Contact(parts[0], parts[1]));
            } else {
                this.contacts.add(new Contact(parts[0], parts[1], parts[2]));
            }
        }
    }

    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }

    public void deleteContact(int index) throws TiffyException {
        assert index >= 0 && index < this.contacts.size() : "Invalid contact index: " + index;

        try {
            this.contacts.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new exception.TiffyException("Invalid index!",
                    exception.TiffyException.ExceptionType.INVALID_INDEX, e);
        }
    }

    public List<Contact> getContacts() {
        return java.util.Collections.unmodifiableList(this.contacts);
    }
}
