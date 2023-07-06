package ru.java_oop.family_tree.human;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Human implements Serializable {
    private long id;
    private String name;
    private Gender gender;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private List<Human> parents, children;
    private Human spouse;

    private Human NullHuman;

    public Human(String name, Gender gender, LocalDate birthDate, LocalDate deathDate, Human mother, Human father) {
        id = -100;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.parents = new ArrayList<>();
        if (mother != null) {
            parents.add(mother);
        }
        if (father != null) {
            parents.add(father);
        }
        this.children = new ArrayList<>();
        ;
    }

    public Human(String name, Gender gender, LocalDate birthDate) {
        this(name, gender, birthDate, null, null, null);
    }

    public Human(String name, Gender gender, LocalDate birthDate, Human mother, Human father) {
        this(name, gender, birthDate, null, mother, father);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Human getMother() {
        for (Human h : parents) {
            if (h.getGender() == Gender.Female) {
                return h;
            }
        }
        return null;
    }

    public Human getFather() {
        for (Human h : parents) {
            if (h.getGender() == Gender.Male) {
                return h;
            }
        }
        return null;
    }

    public void addParent(Human parent) {
        if (!parents.contains(parent)) {
            parents.add(parent);
        }
    }

    public List<Human> getParents() {
        return parents;
    }

    public void setParents(List<Human> parents) {
        if (!new HashSet<>(parents).containsAll(parents)) {
            for (Human parent : parents) {
                this.addParent(parent);
            }
        }
    }

    public Human getSpouse() {
        return spouse;
    }

    public void setSpouse(Human spouse) {
        this.spouse = spouse;
    }

    public List<Human> getChildren() {
        return children;
    }

    public void setChildren(List<Human> children) {
        this.children = children;
    }

    public void addChild(Human child) {
        this.children.add(child);
    }

    public int getAge() {
//        if (deathDate == null) {
//            return getPeriodInYears(birthDate, LocalDate.now());
//        } else {
//            return getPeriodInYears(birthDate, deathDate);
//        }
        return getPeriodInYears(birthDate, Objects.requireNonNullElseGet(deathDate, LocalDate::now));
    }

    private int getPeriodInYears(LocalDate birthDate, LocalDate deathDate) {
        Period difference = Period.between(birthDate, deathDate);
        return difference.getYears();
    }

    public String getFatherInfo() {
        String result = "father: ";
        Human father = this.getFather();
        if (father == null) {
            result += "unknown";
        } else {
            result += father.getName() + " (ID:" + father.getId() + ")";
        }
        return result;
    }

    public String getMotherInfo() {
        String result = "mother: ";
        Human mother = this.getMother();
        if (mother == null) {
            result += "unknown";
        } else {
            result += mother.getName() + " (ID:" + mother.getId() + ")";
        }
        return result;
    }

    public String getChildrenInfo() {
        StringBuilder result = new StringBuilder();
        result.append("children: ");
        if (this.children.isEmpty()) {
            result.append("none");
        } else {
            result.append(this.children.get(0).getName()).append(" (ID:").append(this.children.get(0).getId()).append(")");
            for (int i = 1; i < this.children.size(); i++) {
                result.append(", ").append(this.children.get(i).getName()).append(" (ID:").append(this.children.get(i).getId()).append(")");
            }
        }
        return result.toString();
    }

    public String getSpouseInfo() {
        String result = "spouse: ";
        if (this.spouse == null) {
            result += "none";
        } else {
            result += this.spouse.getName() + " (ID:" + this.spouse.getId() + ")";
        }
        return result;
    }

    private String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(this.getId());
        sb.append(", name: ").append(this.getName());
        sb.append(", gender: ").append(this.getGender());
        sb.append(", age: ").append(this.getAge());
        sb.append(", ").append(this.getFatherInfo());
        sb.append(", ").append(this.getMotherInfo());
        sb.append(", ").append(this.getSpouseInfo());
        sb.append(", ").append(this.getChildrenInfo());
        return sb.toString();
    }

    @Override
    public String toString() {
        return this.getInfo();
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if (!(obj instanceof Human)){
            return false;
        }
        Human h = (Human) obj;
        return h.getId() == this.getId();
    }
}
