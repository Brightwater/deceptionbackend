package com.brightwaters.deception.config;

import static com.brightwaters.deception.constants.SecurityConstants.SIGN_UP_URL;

import com.brightwaters.deception.svc.UserDetailsSrv;
import com.brightwaters.deception.util.AuthenticationFilter;
import com.brightwaters.deception.util.AuthorizationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsSrv userDetailsSrv;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public WebSecurityConfig(UserDetailsSrv userDetailsSrv) {
        this.userDetailsSrv = userDetailsSrv;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder(); 
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.csrf().disable().authorizeRequests().anyRequest().permitAll(); // Works for GET, POST, PUT, DELETE
        // http.cors();
        http.cors().and().csrf().disable().authorizeRequests()
        .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
        .antMatchers(HttpMethod.GET, "/api/deception/test").permitAll()
        .antMatchers(HttpMethod.GET, "/api/deception/putTest").permitAll()
        .antMatchers(HttpMethod.GET, "/api/deception/getTest").permitAll()
        .antMatchers(HttpMethod.GET, "/api/deception/getHint").permitAll()
        .antMatchers(HttpMethod.GET, "/api/deception/getWeap").permitAll()
        .antMatchers(HttpMethod.GET, "/api/deception/getAllClue").permitAll()
        .antMatchers(HttpMethod.GET, "/api/deception/getClue").permitAll()
        .antMatchers(HttpMethod.POST, "/api/events/setupGame").permitAll()
        .antMatchers(HttpMethod.POST, "/api/events/string").permitAll()
        .antMatchers(HttpMethod.POST, "/api/gameState/{gameId}/post").permitAll()
        .antMatchers(HttpMethod.GET, "/api/gameState/{gameId}/{playerName}").permitAll()
        .antMatchers(HttpMethod.GET, "/api/events/getallevents").permitAll()
        .antMatchers(HttpMethod.GET, "/api/events/test/{s}").permitAll()
        .antMatchers(HttpMethod.GET, "/api/cleaner").permitAll()
        .antMatchers(HttpMethod.GET, "/api/events/joinGame/{gameId}/{playerName}").permitAll()
        .antMatchers(HttpMethod.GET, "/api/events/startGame/{gameId}/{playerName}").permitAll()
        .antMatchers(HttpMethod.GET, "/api/events/selectCards/{gameId}/{playerName}/{clueCard}/{weaponCard}").permitAll()
        .antMatchers(HttpMethod.GET, "/api/events/selectHintCard/{gameId}/{playerName}/{cardType}/{cardName}/{selected}").permitAll()
        .antMatchers(HttpMethod.GET, "/api/events/submitHintCard/{gameId}/{playerName}").permitAll()
        .antMatchers(HttpMethod.GET, "/api/revealRole/{gameId}/{playerName}").permitAll()
        .antMatchers(HttpMethod.GET, "/api/revealMurderer/{gameId}/{playerName}").permitAll()
        .antMatchers(HttpMethod.GET, "/api/events/endRound/{gameId}/{playerName}/{cardName}").permitAll()
        .antMatchers(HttpMethod.GET, "/api/events/startGame/{gameId}/{playerName}").permitAll()
        .antMatchers(HttpMethod.GET, "/api/events/startGame/{gameId}/{playerName}").permitAll()
        .antMatchers(HttpMethod.GET, "/api/events/startGame/{gameId}/{playerName}").permitAll()
        .antMatchers(HttpMethod.GET, "/api/events/startGame/{gameId}/{playerName}").permitAll()

        .anyRequest().authenticated()
        .and()
        .addFilter(new AuthenticationFilter(authenticationManager()))
        .addFilter(new AuthorizationFilter(authenticationManager()))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsSrv).passwordEncoder(bCryptPasswordEncoder);
    }

    
}