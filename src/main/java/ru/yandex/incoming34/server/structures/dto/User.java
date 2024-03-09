package ru.yandex.incoming34.server.structures.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yandex.incoming34.server.structures.Role;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "table_clients")
public class User {

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

}
