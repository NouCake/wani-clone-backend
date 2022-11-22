package de.abstractolotl.shadycrab.waniclone.configurations;

import de.abstractolotl.shadycrab.waniclone.authentication.CrabAuthenticationEntryPoint;
import de.abstractolotl.shadycrab.waniclone.authentication.PostOAuthAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class SecurityConfig {


    @Value("#{environment.GOOGLE_OAUTH_CLIENT_ID}") private String GOOGLE_OAUTH_CLIENT_ID;
    @Value("#{environment.GOOGLE_OAUTH_CLIENT_SECRET}") private String GOOGLE_OAUTH_CLIENT_SECRET;
    @Autowired private CrabAuthenticationEntryPoint entryPoint;
    @Autowired private PostOAuthAuthenticationFilter authFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/register/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
            .oauth2Login()
                .clientRegistrationRepository(oauthClients())
                .and()
            .cors()
                .configurationSource(corsConfig())
                .and()
            .csrf() //TODO
                .disable()
            .addFilterAfter(authFilter, CorsFilter.class)
            ;

        return http.build();
    }

    private CorsConfigurationSource corsConfig() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST"));
        config.setAllowedHeaders(List.of("Content-Type"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    private ClientRegistrationRepository oauthClients() {
        ClientRegistration client = CommonOAuth2Provider.GOOGLE.getBuilder("google")
                .clientId(GOOGLE_OAUTH_CLIENT_ID)
                .clientSecret(GOOGLE_OAUTH_CLIENT_SECRET)
                .build();
        return new InMemoryClientRegistrationRepository(client);
    }

}
