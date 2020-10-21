package com.richmond.cookbook.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    @Column(name="user_id")
    private Integer id;

    @NotEmpty(message="*Please provide a user name")
    @Size(min=5, message="*Your user name must have at least 5 characters")
    @Column(name="user_name", unique=true)
    private String userName;

    @NotEmpty(message="*Please provide an email")
    @Email(message="*Please provide a valid Email")
    @Column(name="email")
    private String email;

    @NotEmpty(message="*Please provide your password")
    @Size(min=5, message="*Your password must have at least 5 characters")
    @Column(name="password")
    private String password;

    @NotEmpty(message="*Please provide your first name")
    @Column(name="first_name")
    private String firstName;

    @NotEmpty(message="*Please provide your last name")
    @Column(name="last_name")
    private String lastName;

    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
        name="user_role",
        joinColumns=@JoinColumn(name="user_id"),
        inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Recipe> recipes = new ArrayList<>();

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
        recipe.setUser(this);
    }

    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);
        recipe.setUser(null);
    }

    public boolean updateRecipe(Recipe recipe) {
        for (int i=0; i<recipes.size(); i++) {
            if (recipes.get(i).getId() == recipe.getId()) {
                recipes.set(i, recipe);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User other = (User) o;

        if (getId() != null ?
            !getId().equals(other.getId()) : other.getId() != null)
            return false;
        if (getUserName() != null ?
            !getUserName().equals(other.getUserName()) : other.getUserName() != null)
            return false;
        if (getFirstName() != null ?
            !getFirstName().equals(other.getFirstName()) : other.getFirstName() != null)
            return false;
        if (getLastName() != null ?
            !getLastName().equals(other.getLastName()) : other.getLastName() != null)
            return false;
        if (getPassword() != null ?
            !getPassword().equals(other.getPassword()) : other.getPassword() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getUserName() != null ? getUserName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        return result;
    }
}
