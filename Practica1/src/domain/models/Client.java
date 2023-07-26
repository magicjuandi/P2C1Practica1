package domain.models;

import domain.enums.ClientType;

public class Client {
    private Long id;
    private String name;
    private ClientType level;

    public Client(Long id, String name, ClientType level) {
        this.id = id;
        this.name = name;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientType getLevel() {
        return level;
    }

    public void setLevel(ClientType level) {
        this.level = level;
    }

    public Client() {
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                '}';
    }
}
