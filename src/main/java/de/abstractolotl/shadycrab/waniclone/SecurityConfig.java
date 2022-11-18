package de.abstractolotl.shadycrab.waniclone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2Login();

        http
                .oauth2Login()
                .clientRegistrationRepository(clientRegistrationRepository())
                .authorizedClientService(authorizedClientService());

        http
                .httpBasic().disable()
                .formLogin().disable();

        return http.build();
    }

    @Value("#{environment.GOOGLE_OAUTH_CLIENT_ID}")
    private String GOOGLE_OAUTH_CLIENT_ID;

    @Value("#{environment.GOOGLE_OAUTH_CLIENT_SECRET}")
    private String GOOGLE_OAUTH_CLIENT_SECRET;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration client = CommonOAuth2Provider.GOOGLE.getBuilder("wurst")
                .clientId(GOOGLE_OAUTH_CLIENT_ID)
                .clientSecret(GOOGLE_OAUTH_CLIENT_SECRET)
                .build();
        return new InMemoryClientRegistrationRepository(client);
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {

        return new InMemoryOAuth2AuthorizedClientService(
                clientRegistrationRepository());
    }

}
