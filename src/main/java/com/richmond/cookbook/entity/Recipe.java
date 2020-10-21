package com.richmond.cookbook.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="recipes")
public class Recipe {
    @Id
    @GeneratedValue
    @Column(name="recipe_id")
    private Integer id;

    @NotEmpty(message="*Please provide the recipe name")
    @Size(min=3, message="*The recipe name must have at least 3 characters")
    @Column(name="name")
    private String name;

    @NotEmpty(message="*Please select a category")
    @Column(name="category")
    private String category;

    @Max(5)
    @Min(1)
    @Column(name="difficulty")
    private Integer difficulty;

    @Column(name="image")
    private String image;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy="recipe", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Step> steps = new ArrayList<>();

    public void addStep(Step step) {
        steps.add(step);
        step.setRecipe(this);
    }

    public void removeStep(Step step) {
        steps.remove(step);
        step.setRecipe(null);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;

        Recipe other = (Recipe) o;

        if (getId() != null ?
            !getId().equals(other.getId()) : other.getId() != null)
            return false;
        if (getName() != null ?
            !getName().equals(other.getName()) : other.getName() != null)
            return false;
        if (getCategory() != null ?
            !getCategory().equals(other.getDifficulty()) : other.getDifficulty() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDifficulty() != null ? getDifficulty().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "id: " + getId() + ", name: " + getName() + ", image: " + getImage()
            + ", difficulty: " + getDifficulty() + ", category: " + getCategory();
    }
}
