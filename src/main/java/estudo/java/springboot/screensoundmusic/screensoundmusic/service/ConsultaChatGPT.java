package estudo.java.springboot.screensoundmusic.screensoundmusic.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {
    public static String obterInformacoesSobreArtista(String artistaNome) {
        var openAiKey = System.getenv("OPENAI_API_KEY");
        OpenAiService service = new OpenAiService(openAiKey);

        CompletionRequest requisicao = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt("Me informe em 255 caracteres quem é o artista: " + artistaNome)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var resposta = service.createCompletion(requisicao);
        return resposta.getChoices().get(0).getText();
    }
}

