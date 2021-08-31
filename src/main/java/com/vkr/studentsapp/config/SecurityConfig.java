package com.vkr.studentsapp.config;

import com.vkr.studentsapp.security.jwt.JwtConfigurer;
import com.vkr.studentsapp.security.jwt.JwtTokenFilter;
import com.vkr.studentsapp.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;
    private final CorsFilter corsFilter;

    private static final String SUPER_ADMIN_ENDPOINT = "/api/superadmin/**";
    private static final String LOGIN_ENDPOINT = "/api/login";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider ,CorsFilter corsFilter){
        this.jwtTokenProvider = jwtTokenProvider;
        this.corsFilter = corsFilter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.csrf().disable()
                //.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT, "/api/registration/user","/api/registration/admin")
                        //,"/assets/**", "/**")
                .permitAll()
                .antMatchers(SUPER_ADMIN_ENDPOINT).hasRole("SUPER_ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));


    }
}
