package estudo.java.springboot.screensoundmusic.screensoundmusic.model;

public enum TipoArtista {

    SOLO("Solo"),
    DUPLA("Dupla"),
    BANDA("Banda");

    private String tipoArtistaNome;

    TipoArtista(String tipoArtistaNome) {
        this.tipoArtistaNome = tipoArtistaNome;
    }

    public String getTipoArtistaNome() {
        return tipoArtistaNome;
    }

    public static TipoArtista porNome(String nome) {
        for (TipoArtista tipo: TipoArtista.values()) {
            if(tipo.tipoArtistaNome.toLowerCase().equalsIgnoreCase(nome.toLowerCase()))
            {
                return tipo;
            }
        }
        throw new RuntimeException("O tipo de artista informado não foi encontrado");
    }
}
