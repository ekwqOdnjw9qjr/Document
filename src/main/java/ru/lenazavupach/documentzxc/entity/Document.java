package ru.lenazavupach.documentzxc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @NotBlank
    private String title;

    @Column(name = "type")
    @NotBlank
    private String type;

    @Column(name = "date")
    @NotNull
    private LocalDate date;

    @Column(name = "author")
    @NotBlank
    private String author;

    @Column(name = "number")
    @Positive
    private Long number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id",foreignKey = @ForeignKey(name = "documents_company_fk"))
    @JsonIgnore
    private Company company;

}