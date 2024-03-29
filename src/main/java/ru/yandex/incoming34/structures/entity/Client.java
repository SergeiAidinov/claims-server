package ru.yandex.incoming34.structures.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "table_clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;
    @Column(name = "client_login")
    private String login;
    @Column(name = "client_password")
    private String password;
    @Column(name = "client_name")
    private String name;
    @Column(name = "client_roles")
    private String roles;

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
