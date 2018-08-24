package ie.soft8020.librarymanagement.domain;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class User {

    @NotNull()
    @Pattern(regexp = ".*(^[a-zA-Z]{4,30}$)", message = "user name must be alphabetical and in the range of 4 - 30")
    private String username;

    @NotNull
    @Size(min = 5, max = 40, message = "password must be in the range of 5 - 40")
    private String password;


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
}
