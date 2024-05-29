import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    static class Contact {
        private String lastName;
        private String firstName;
        private String position;
        private String address;
        private String phone;
        private String email;

        public Contact() {
        }

        public Contact(String lastName, String firstName, String position, String address, String phone, String email) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.position = position;
            this.address = address;
            this.phone = phone;
            this.email = email;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return String.format("%-15s %-10s %-15s %-20s %-15s %-20s",
                    lastName, firstName, position, address, phone, email);
        }

        public static String getTableHeader() {
            return String.format("%-15s %-10s %-15s %-20s %-15s %-20s",
                    "Прізвище", "Ім'я", "Посада", "Адреса", "Телефон", "Електронна пошта");
        }
    }

    static class ContactDatabase {
        private List<Contact> contacts;

        public ContactDatabase() {
            contacts = new ArrayList<>();
        }

        public void addContact(Contact contact) {
            contacts.add(contact);
            saveToFile();
        }

        public List<Contact> getContacts() {
            return contacts;
        }

        public void editContact(String lastName, Contact newContact) {
            for (int i = 0; i < contacts.size(); i++) {
                if (contacts.get(i).getLastName().equalsIgnoreCase(lastName)) {
                    contacts.set(i, newContact);
                    saveToFile();
                    return;
                }
            }
            System.out.println("Контакт з таким прізвищем не знайдено.");
        }

        public void deleteContact(String lastName) {
            for (int i = 0; i < contacts.size(); i++) {
                if (contacts.get(i).getLastName().equalsIgnoreCase(lastName)) {
                    contacts.remove(i);
                    saveToFile();
                    return;
                }
            }
            System.out.println("Контакт з таким прізвищем не знайдено.");
        }

        public void displayContacts() {
            System.out.println(Contact.getTableHeader());
            System.out.println("--------------- ---------- --------------- -------------------- --------------- --------------------");
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }

        public Contact searchContactByLastName(String lastName) {
            for (Contact contact : contacts) {
                if (contact.getLastName().equalsIgnoreCase(lastName)) {
                    return contact;
                }
            }
            return null;
        }

        public void sortContactsByParameter(String parameter) {
            switch (parameter.toLowerCase()) {
                case "прізвище":
                    Collections.sort(contacts, Comparator.comparing(Contact::getLastName));
                    break;
                case "ім'я":
                    Collections.sort(contacts, Comparator.comparing(Contact::getFirstName));
                    break;
                case "посада":
                    Collections.sort(contacts, Comparator.comparing(Contact::getPosition));
                    break;
                default:
                    System.out.println("Невідомий параметр для сортування.");
            }
        }

        private void saveToFile() {
            try (PrintWriter writer = new PrintWriter(new FileWriter("contacts.txt"))) {
                for (Contact contact : contacts) {
                    writer.println(contact.getLastName() + "," + contact.getFirstName() + "," +
                            contact.getPosition() + "," + contact.getAddress() + "," +
                            contact.getPhone() + "," + contact.getEmail());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void loadFromFile() {
            try (BufferedReader reader = new BufferedReader(new FileReader("contacts.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String lastName = parts[0];
                    String firstName = parts[1];
                    String position = parts[2];
                    String address = parts[3];
                    String phone = parts[4];
                    String email = parts[5];
                    Contact contact = new Contact(lastName, firstName, position, address, phone, email);
                    contacts.add(contact);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ContactDatabase contactDatabase = new ContactDatabase();
        contactDatabase.loadFromFile();

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Додати контакт");
            System.out.println("2. Редагувати контакт");
            System.out.println("3. Видалити контакт");
            System.out.println("4. Показати всі контакти");
            System.out.println("5. Пошук контакту за прізвищем");
            System.out.println("6. Сортування контактів");
            System.out.println("7. Вихід");
            System.out.print("Оберіть опцію: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    addContact(scanner, contactDatabase);
                    break;
                case 2:
                    editContact(scanner, contactDatabase);
                    break;
                case 3:
                    deleteContact(scanner, contactDatabase);
                    break;
                case 4:
                    contactDatabase.displayContacts();
                    break;
                case 5:
                    searchContact(scanner, contactDatabase);
                    break;
                case 6:
                    sortContacts(scanner, contactDatabase);
                    break;
                case 7:
                    System.out.println("Завершення програми.");
                    return;
                default:
                    System.out.println("Невірний вибір.");
            }
        }
    }

    private static void addContact(Scanner scanner, ContactDatabase contactDatabase) {
        System.out.print("Прізвище: ");
        String lastName = scanner.nextLine();

        System.out.print("Ім'я: ");
        String firstName = scanner.nextLine();

        System.out.print("Посада: ");
        String position = scanner.nextLine();

        System.out.print("Адреса: ");
        String address = scanner.nextLine();

        System.out.print("Телефон: ");
        String phone = scanner.nextLine();

        System.out.print("Електронна пошта: ");
        String email = scanner.nextLine();

        Contact contact = new Contact(lastName, firstName, position, address, phone, email);
        contactDatabase.addContact(contact);
        System.out.println("Контакт успішно додано.");
    }

    private static void editContact(Scanner scanner, ContactDatabase contactDatabase) {
        System.out.print("Введіть прізвище контакту, який потрібно редагувати: ");
        String lastName = scanner.nextLine();

        Contact existingContact = contactDatabase.searchContactByLastName(lastName);
        if (existingContact != null) {
            System.out.print("Нове ім'я: ");
            String newFirstName = scanner.nextLine();

            System.out.print("Нова посада: ");
            String newPosition = scanner.nextLine();

            System.out.print("Нова адреса: ");
            String newAddress = scanner.nextLine();

            System.out.print("Новий телефон: ");
            String newPhone = scanner.nextLine();

            System.out.print("Нова електронна пошта: ");
            String newEmail = scanner.nextLine();

            Contact newContact = new Contact(lastName, newFirstName, newPosition, newAddress, newPhone, newEmail);
            contactDatabase.editContact(lastName, newContact);
            System.out.println("Контакт успішно відредаговано.");
        } else {
            System.out.println("Контакт з таким прізвищем не знайдено.");
        }
    }

    private static void deleteContact(Scanner scanner, ContactDatabase contactDatabase) {
        System.out.print("Введіть прізвище контакту, який потрібно видалити: ");
        String lastName = scanner.nextLine();

        contactDatabase.deleteContact(lastName);
        System.out.println("Контакт успішно видалено.");
    }

    private static void searchContact(Scanner scanner, ContactDatabase contactDatabase) {
        System.out.print("Введіть прізвище для пошуку: ");
        String lastName = scanner.nextLine();

        Contact foundContact = contactDatabase.searchContactByLastName(lastName);
        if (foundContact != null) {
            System.out.println("Знайдено контакт: " + foundContact);
        } else {
            System.out.println("Контакт з таким прізвищем не знайдено.");
        }
    }

    private static void sortContacts(Scanner scanner, ContactDatabase contactDatabase) {
        System.out.print("Сортувати за полем (прізвище/ім'я/посада): ");
        String parameter = scanner.nextLine();
        contactDatabase.sortContactsByParameter(parameter);
        contactDatabase.displayContacts();
    }
}
