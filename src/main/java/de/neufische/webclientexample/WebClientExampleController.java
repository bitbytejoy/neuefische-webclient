package de.neufische.webclientexample;

import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api")
public class WebClientExampleController {
    @GetMapping("/github-repos")
    public Repo[] getGithubRepos () {
        Repo[] repos = WebClient
            .builder()
            .baseUrl("https://api.github.com")
            .build()
            .method(HttpMethod.GET)
            .uri("/users/neuefische/repos")
            .exchangeToMono(
                clientResponse -> clientResponse.bodyToMono(Repo[].class)
            ).block();

        return repos;
    }

    @PostMapping("/users")
    public User createUser (@RequestBody NewUser newUser) {
        User user = WebClient
            .builder() // Startet den bau vom HTTP request
            .baseUrl("https://reqres.in") // Definiert die domain
            .build() // startet die Einstellungen
            .method(HttpMethod.POST) // Sagt welche HTTP Methode wir ausführen wollen
            .uri("/api/users") // Sagt welchen Endpunkt wir auf die baseUrl "drauf kleben" wollen
            .bodyValue(newUser) // Sagt welchen JSON wir mitschicken wollen
            .exchangeToMono(
                    clientResponse -> clientResponse.bodyToMono(User.class) // Formatiert die Antwort vom Server in unser Objekt
            ).block(); // Führt den Request aus

        return user;
    }
}
