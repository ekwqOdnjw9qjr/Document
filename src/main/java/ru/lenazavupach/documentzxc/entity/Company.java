package ru.lenazavupach.documentzxc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_for_calls")
    private Long phoneForCalls;

    @Column(name = "inn")
    private Long inn;

    @OneToMany(mappedBy = "company",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JsonIgnoreProperties({"company","type","author","number","date",})
    private List<Document> documents = new ArrayList<>();

}
