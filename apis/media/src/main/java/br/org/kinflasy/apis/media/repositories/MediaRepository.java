package br.org.kinflasy.apis.media.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import br.org.kinflasy.apis.media.entities.Media;

public interface MediaRepository extends JpaRepository<Media, UUID> {

}
