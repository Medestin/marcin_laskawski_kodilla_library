package com.kodilla.library.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rental_data")
public class RentalDao implements Serializable {

    @Id
    @NotNull
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exemplarId")
    private BookExemplar bookExemplar;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private LibraryUser libraryUser;

    @NotNull
    @CreatedDate
    @Temporal(TemporalType.DATE)
    private Date rentedOn;

    @NotNull
    private Date returnDeadline;

    public RentalDao(LibraryUser user, BookExemplar exemplar){
        this.libraryUser = user;
        this.bookExemplar = exemplar;
    }

    @PrePersist
    public void onCreate(){
        Calendar calendar = Calendar.getInstance();
        this.rentedOn = new Date();
        calendar.setTime(rentedOn);
        calendar.add(Calendar.DATE, 7);
        this.returnDeadline = calendar.getTime();
    }
}
