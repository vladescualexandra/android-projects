package eu.ase.ro.seminar11.util;

import java.io.Serializable;

public class Coach implements Serializable {

    private String id;
    private String name;
    private String team;
    private String role;

    public Coach() {
    }

    public Coach(String id, String name, String team, String role) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "name='" + name + '\'' +
                ", team='" + team + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
