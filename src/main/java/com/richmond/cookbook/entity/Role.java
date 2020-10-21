package com.richmond.cookbook.entity;

import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue
    @Column(name="role_id")
    private Integer id;

    @Column(name="role")
    private String role;

    @ManyToMany(mappedBy="roles")
    private Set<User> users = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role other = (Role) o;

        if (getId() != null ?
            !getId().equals(other.getId()) : other.getId() != null)
            return false;
        if (getRole() != null ?
            !getRole().equals(other.getRole()) : other.getRole() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getRole() != null ? getRole().hashCode() : 0);
        return result;
    }
}
