package com.dga.springboot.m13garciadavid;


import com.dga.springboot.m13garciadavid.auth.handler.LoginSuccesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Gestiona la configuració de seguretat de les diferents vistes de l'aplicació
 */
@EnableGlobalMethodSecurity(securedEnabled = true) //Anotació que habilita les configuracions de seguretat al controlador
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccesHandler succesHandler;

    //Injectem la connexió a la BBDD
    @Autowired
    private DataSource dataSource;

    //Registrar password encoder i defecte el bcrypt. Retorna la instància i la desa al contenidor @Bean
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Mètode que configura les rutes http i els dóna seguretat, per permetre veure pàgines segons rols usuaris
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //Amb això asginem rutes, per a USERS, ADMINS i ANÒNIMS. També fem que vagi a login o logout directament
        //Ho comentem perquè totes aquestes restriccions, finalment, es faran usant anotacions al controlador de Client
        http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar", "/locale").permitAll()
                /* .antMatchers("/ver/**").hasAnyRole("USER")
                 .antMatchers("/uploads/**").hasAnyRole("USER")
                 .antMatchers("/form/**").hasAnyRole("ADMIN")
                 .antMatchers("/eliminar/**").hasAnyRole("ADMIN")
                 .antMatchers("/factura/**").hasAnyRole("ADMIN")*/
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(succesHandler)
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error_403");

    }

    /**
     * Registra usuaris. Primer en memòria, després a BBDD
     * @param builder
     * @throws Exception
     */
    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception{


        PasswordEncoder encoder = passwordEncoder();

        ///////////////////////CREACIÓ USUARIS EN BBDD////////////////////////////////////

        //Fa les consultes automàticament passant el paràmetre al ? sobre el camp login
        builder.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("SELECT username, password, enabled FROM user WHERE username = ?")
                .authoritiesByUsernameQuery("SELECT u.username, a.authority FROM authoritie a INNER JOIN user u ON (a.user_id = u.id) WHERE u.username =?");

    }
}
