package com.kodilla.library.database.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "users")
public class LibraryUser {

    @Id
    @NotNull
    @GeneratedValue
    private Long id;

    @NotNull
    @Length(min = 3, max = 10)
    private String firstName;

    @NotNull
    @Length(min = 3, max = 20)
    private String lastName;

    @CreationTimestamp
    private Timestamp creationTimestamp;

    public LibraryUser() {
    }

    public LibraryUser(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
