package estudo.java.springboot.screensoundmusic.screensoundmusic.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artistas")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private TipoArtista tipoArtista;
    @OneToMany(mappedBy = "artista", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Musica> musicas;

    public Artista() {
        this.musicas = new ArrayList<>();
    }

    public Artista(String nome, TipoArtista tipoArtista) {
        this.nome = nome;
        this.tipoArtista = tipoArtista;
        this.musicas = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoArtista getTipoArtista() {
        return tipoArtista;
    }

    public void setTipoArtista(TipoArtista tipoArtista) {
        this.tipoArtista = tipoArtista;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void adicionarMusica(Musica musica){
        musica.setArtista(this);
        this.musicas.add(musica);
    }

    @Override
    public String toString() {
        return "Artista - " +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipoArtista=" + tipoArtista.getTipoArtistaNome();
    }
}
