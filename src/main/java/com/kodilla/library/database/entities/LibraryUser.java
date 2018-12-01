package com.kodilla.library.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    @NotNull
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public LibraryUser(@NotNull @Length(min = 3, max = 10) String firstName, @NotNull @Length(min = 3, max = 20) String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @PrePersist
    public void onCreate(){
        this.creationDate = new Date();
    }
}
