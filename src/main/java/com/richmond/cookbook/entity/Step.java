package com.richmond.cookbook.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="steps")
public class Step {
    @Id
    @GeneratedValue
    @Column(name="step_id")
    private Integer id;

    @Column(name="step")
    private String step;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Step)) return false;

        Step other = (Step) o;

        if (getId() != null ?
            !getId().equals(other.getId()) : other.getId() != null)
            return false;
        if (getStep() != null ?
            !getStep().equals(other.getStep()) : other.getStep() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getStep() != null ? getStep().hashCode() : 0);
        return result;
    }
}
