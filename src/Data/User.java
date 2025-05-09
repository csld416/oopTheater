package Data;

public class User {

    private int id;
    private String name;
    private String phoneNumber;
    
    // === Constructor ===
    public User(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // === Getters ===
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // === Setters ===
    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void clear() {
        this.id = -1;
        this.name = null;
        this.phoneNumber = null;
    }

    // === toString (Optional) ===
    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', phone='" + phoneNumber + "'}";
    }
}
