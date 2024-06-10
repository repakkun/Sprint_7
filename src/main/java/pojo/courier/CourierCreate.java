package pojo.courier;

import org.apache.commons.lang3.RandomStringUtils;
import java.time.LocalDateTime;

public class CourierCreate {
    private String login;
    private String password;
    private String firstName;

    public CourierCreate() {
    }

    public CourierCreate(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }


    public static CourierCreate random() {
        return new CourierCreate("Ilya" + RandomStringUtils.randomAlphabetic(5, 15), "88888888" + LocalDateTime.now(), "Ilya");
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
