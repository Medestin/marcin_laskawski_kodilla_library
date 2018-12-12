package com.kodilla.library.database.entities;

import com.kodilla.library.database.entities.exemplarStatus.ExemplarStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "exemplars")
public class BookExemplar {

    @Id
    @NotNull
    @GeneratedValue
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ExemplarStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookTitle_id")
    private BookTitle bookTitle;

    public BookExemplar() {
    }

    public BookExemplar(BookTitle bookTitle){
        this.bookTitle = bookTitle;
        this.status = ExemplarStatus.AVAILABLE;
    }
}
