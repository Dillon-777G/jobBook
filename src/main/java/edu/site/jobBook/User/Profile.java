package edu.site.jobBook.User;

import lombok.Data;
import java.util.Objects;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

@Data
public class Profile {
    private String firstName;
    private String lastName;
    private String email; // Assuming we want to include email in the profile
    private String username;
    private String passwordHash;

    /**
     * Sets the password after hashing it.
     * @param password The plain text password to hash and store.
     */
    public void setPassword(String password) {
        this.passwordHash = hashPassword(password);
    }

    /**
     * Checks if the provided password corresponds to the hashed password stored.
     * @param password The plain text password to check.
     * @return true if the hashed password matches the stored hash, false otherwise.
     */
    public boolean checkPassword(String password) {
        return Objects.equals(this.passwordHash, hashPassword(password));
    }

    /**
     * Hashes a password using SHA-256.
     * @param password The password to hash.
     * @return The hashed password.
     */
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, encodedhash);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not supported", e);
        }
    }
}
