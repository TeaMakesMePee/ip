package manager;

import contacts.Contact;
import java.util.List;
import java.util.ArrayList;
import exception.TiffyException;

/**
 * Manages a list of contacts, allowing for addition, deletion, and retrieval.
 */
public class ContactManager {
    private final List<Contact> contacts;

    /**
     * Constructs a ContactManager and initializes the contact list
     * from stored contact data.
     *
     * @param contactData A list of contact strings formatted as "name|phone"
     *                    or "name|phone|email".
     */
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

    /**
     * Adds a new contact to the contact list.
     *
     * @param contact The contact to be added.
     */
    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }

    /**
     * Deletes a contact at the specified index.
     *
     * @param index The index of the contact to be deleted.
     * @throws TiffyException If the index is out of bounds.
     */
    public void deleteContact(int index) throws TiffyException {
        assert index >= 0 && index < this.contacts.size() : "Invalid contact index: " + index;

        try {
            UiManager.getInstance().generateEventFeedback(this.contacts.get(index), UiManager.EventType.CONTACT_DELETED);
            this.contacts.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new TiffyException("Invalid index!",
                    TiffyException.ExceptionType.INVALID_INDEX, e);
        }
    }

    /**
     * Returns an unmodifiable list of contacts.
     *
     * @return A list of contacts.
     */
    public List<Contact> getContacts() {
        return java.util.Collections.unmodifiableList(this.contacts);
    }
}