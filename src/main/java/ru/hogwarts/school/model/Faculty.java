package ru.hogwarts.school.model;

import java.util.Objects;

public class Faculty {
    private Long id;
    private String mame;
    private String color;

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", mame='" + mame + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(id, faculty.id) && Objects.equals(mame, faculty.mame) && Objects.equals(color, faculty.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mame, color);
    }

    public void setMame(String mame) {
        this.mame = mame;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public String getMame() {
        return mame;
    }

    public String getColor() {
        return color;
    }

    public Faculty(Long id, String mame, String color) {
        this.id = id;
        this.mame = mame;
        this.color = color;
    }
}
