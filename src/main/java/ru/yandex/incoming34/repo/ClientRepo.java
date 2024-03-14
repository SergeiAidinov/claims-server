package ru.yandex.incoming34.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.incoming34.structures.entity.Client;

import java.util.Optional;

@Repository
public interface ClientRepo extends CrudRepository<Client, Long> {

    Optional<Client> findByLogin(String login);
}
