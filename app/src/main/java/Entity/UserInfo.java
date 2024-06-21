package Entity;

public class UserInfo {
    private int _id;
    private String username;
    private String password;
    private int register_type;

    public UserInfo(int _id, String username, String password, int register_type) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.register_type = register_type;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRegisterType() {
        return register_type;
    }

    public void setRegisterType(int register_type) {
        this.register_type = register_type;
    }
}
