package com.kodilla.library.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class BookTitle {

    @Id
    @NotNull
    @GeneratedValue
    private Long id;

    @NotNull
    @Length(min = 3, max = 30)
    private String title;

    @NotNull
    private String author;

    @NotNull
    private int publicationYear;

    public BookTitle(@NotNull @Length(min = 3, max = 30) String title, @NotNull String author, @NotNull int publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }
}
