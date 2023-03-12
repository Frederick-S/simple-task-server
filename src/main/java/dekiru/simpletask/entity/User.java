package dekiru.simpletask.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.Instant;

/**
 * The user entity.
 */
@Entity
public class User extends BaseEntity {
    @Column
    private String name;

    @Column
    private String encryptedPassword;

    @Column
    private Instant createdAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
