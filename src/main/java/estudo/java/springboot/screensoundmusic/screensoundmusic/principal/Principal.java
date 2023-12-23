package estudo.java.springboot.screensoundmusic.screensoundmusic.principal;

import estudo.java.springboot.screensoundmusic.screensoundmusic.model.Artista;
import estudo.java.springboot.screensoundmusic.screensoundmusic.model.Musica;
import estudo.java.springboot.screensoundmusic.screensoundmusic.model.TipoArtista;
import estudo.java.springboot.screensoundmusic.screensoundmusic.repository.iArtistaRepository;
import estudo.java.springboot.screensoundmusic.screensoundmusic.service.ConsultaChatGPT;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner scanner;
    private iArtistaRepository artistaRepository;

    public Principal(iArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
        scanner = new Scanner(System.in);
    }

    public void exibirMenu() {

        var opcao = -1;

        var menu = """
                *** Screen Sound Músicas ***
                
                1 - Cadastrar artistas
                2 - Cadastrar músicas
                3 - Listar músicas
                4 - Buscar músicas por artistas
                5 - Pesquisar dados sobre um artista
                
                0 - Sair
                """;

        while(opcao != 0)
        {
            System.out.println(menu);
            opcao =  this.scanner.nextInt();
            /**
             * Scanner não lê o caractere newLine gerado ao apertar o enter, então esse nextLine é usado para
             * ler esse new line e evitar erros em futuros nextLines utilizados.
             */
            scanner.nextLine();

            switch (opcao)
            {
                case 1: {
                    this.cadastrarArtista();
                    break;
                }
                case 2: {
                    this.cadastrarMusica();
                    break;
                }
                case 3: {
                    this.listarMusicas();
                    break;
                }
                case 4: {
                    this.buscarMusicaPorArtista();
                    break;
                }
                case 5: {
                    this.pesquisarDadosSobreArtista();
                    break;
                }
                case 0: {
                    System.out.println("Bye bye");
                    break;
                }
                default: {
                    System.out.println("Opção inesistente");
                }
            }

        }

    }

    private void pesquisarDadosSobreArtista() {
        System.out.println("Informe o artista que deseja pesquisar: ");
        var nomeArtista = scanner.nextLine();
        var dadosArtista = ConsultaChatGPT.obterInformacoesSobreArtista(nomeArtista);
        System.out.println(dadosArtista);
    }

    private void buscarMusicaPorArtista() {
        System.out.println("Os artistas cadastrados são: ");
        this.listarArtistasCadastrados().forEach(artista -> System.out.println(artista.getNome()));
        System.out.println("Digite o nome do artista ao qual você deseja ver as músicas: ");
        var nomeArtista = scanner.nextLine();
        List<Musica> musicasPorArtista = this.artistaRepository.buscarMusicasPorArtista(nomeArtista);
        System.out.printf("As músicas cadastradas do artista %s são: \n",nomeArtista);
        musicasPorArtista.forEach(musica -> System.out.printf("Artista: %s - Música: %s - Álbum: %s \n", musica.getArtista().getNome(), musica.getNome(), musica.getAlbum()));
    }

    private void listarMusicas() {
        List<Musica> musicasCadastradas = this.listarMusicasCadastradas();
        System.out.println("As músicas cadastradas até o momento: ");
        musicasCadastradas.forEach(musica -> {
            System.out.printf("Artista: %s - Música: %s - Álbum: %s \n", musica.getArtista().getNome(), musica.getNome(), musica.getAlbum());
        });
    }

    private void cadastrarMusica() {
        List<Artista> artistasCadastrados = this.listarArtistasCadastrados();
        System.out.println("Artistas cadastrados: ");
        artistasCadastrados.forEach(artista -> System.out.println(artista.getNome()));
        System.out.println("Informe o artista para o qual deseja cadastrar uma música: ");
        var nomeArtista = scanner.nextLine();
        Optional<Artista> artistaEscolhido = this.artistaRepository.findByNomeContainingIgnoreCase(nomeArtista);
        if(artistaEscolhido.isPresent())
        {
            Artista artista = artistaEscolhido.get();
            Musica musica = this.criarObjetoMusica();
            artista.adicionarMusica(musica);
            this.artistaRepository.save(artista);
        }
    }

    private void cadastrarArtista() {

        var opcao = "s";

        while(opcao.equalsIgnoreCase("s"))
        {

            Artista artista = this.criarObjetoArtista();
            artista = this.artistaRepository.save(artista);
            System.out.println(artista);

            System.out.println("Deseja cadastrar outro artista? (S/N)");
            opcao = scanner.nextLine();

            while(!opcao.equalsIgnoreCase("s") && !opcao.equalsIgnoreCase("n"))
            {
                System.out.println("Opção não disponível, digite S ou N.");
                opcao = scanner.nextLine();
            }

        }
    }

    private Artista criarObjetoArtista() {
        System.out.println("Informe o nome do artista: ");
        var nomeArtista = scanner.nextLine();

        System.out.println("Informe o tipo desse artista: (solo, dupla, banda)");
        var tipoArtista = scanner.nextLine();

        Artista artista = new Artista(nomeArtista,TipoArtista.porNome(tipoArtista));

        return artista;
    }

    private Musica criarObjetoMusica() {
        System.out.println("Informe o nome da música: ");
        var nomeMusica = scanner.nextLine();

        System.out.println("Informe o album: ");
        var albumMusica = scanner.nextLine();

        return new Musica(nomeMusica,albumMusica);
    }

    private List<Artista> listarArtistasCadastrados() {
        return this.artistaRepository.findAll();
    }

    private List<Musica> listarMusicasCadastradas() {
        return this.artistaRepository.listarMusicas();
    }

}
