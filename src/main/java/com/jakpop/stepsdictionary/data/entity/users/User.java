package com.jakpop.stepsdictionary.data.entity.users;

import com.jakpop.stepsdictionary.data.entity.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "users")
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String username;
    private String passwordSalt;
    private String passwordHash;
    private Role role;

    public User(String username, String password, Role role) {
        this.username = username;
        this.passwordSalt = RandomStringUtils.random(32);
        this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt);
        this.role = role;
    }

    public boolean checkPassword(String password) {
        return DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash);
    }

    public boolean checkUsername(String username) {
        return username.equals(this.username);
    }

    public void setId() {
        this.id = UUID.randomUUID().toString();
    }
}
