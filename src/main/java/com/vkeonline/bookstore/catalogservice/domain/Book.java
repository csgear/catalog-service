package com.vkeonline.bookstore.catalogservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.Instant;

/**
 * @author csgear
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The book ISBN must be defined.")
    @Pattern(regexp = "^([0-9]{10}|[0-9]{13})$",
            message = "The ISBN format must follow the standards ISBN-10 or ISBN-13.")
    @Column(name = "ISBN")
    private String isbn ;

    @NotBlank(message = "The book title must be defined.")
    @Column(name = "TITLE")
    private String title ;

    @NotBlank(message = "The book author must be defined.")
    @Column(name = "AUTHOR")
    private String author ;

    @NotNull(message = "The book price must be defined.")
    @Positive(message = "The book price must be greater than zero.")
    @Column(name = "PRICE")
    private Double price ;

    @Column(name = "PUBLISHER")
    private String publisher;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    private Instant createdDate ;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private Instant lastModifiedDate ;

    @Version
    private int version;

    public static Book build(String isbn, String title, String author, Double price, String publisher) {
        return new Book(null, isbn, title, author, price, publisher, null, null, 0);
    }
}