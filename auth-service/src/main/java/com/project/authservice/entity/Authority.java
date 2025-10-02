package com.project.authservice.entity;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Entity
@Getter
@Setter
public class Authority extends BaseEntity {
    private String name;
    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;
}