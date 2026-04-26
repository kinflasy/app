package br.org.kinflasy.apis.media.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.media.entities.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, UUID> {

}
