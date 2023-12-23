package estudo.java.springboot.screensoundmusic.screensoundmusic.repository;

import estudo.java.springboot.screensoundmusic.screensoundmusic.model.Artista;
import estudo.java.springboot.screensoundmusic.screensoundmusic.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface iArtistaRepository extends JpaRepository<Artista, Long> {

    Optional<Artista> findByNomeContainingIgnoreCase(String nomeArtista);

    @Query("SELECT m FROM Artista a JOIN a.musicas m")
    List<Musica> listarMusicas();

    @Query("SELECT m FROM Artista a JOIN a.musicas m WHERE a.nome ILIKE %:nomeArtista%")
    List<Musica> buscarMusicasPorArtista(String nomeArtista);
}
