package com.lambda.foodtrucktrackr.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter
{
    private String RESOURCE_ID = "resource_id";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception
    {
        resources.resourceId(RESOURCE_ID)
                .stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
                .antMatchers("/",
                        "/h2-console/**",
                        "/swagger-resources/**",
                        "/swagger-resource/**",
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "/webjars/**",
                        "/createnewuser", "/api/users/user/register", "/login")
                .permitAll()
                .antMatchers("/useremails/**", "/logout").authenticated()
                .antMatchers(HttpMethod.POST, "/api/users/**").hasAnyRole("OPERATOR")
                .antMatchers(HttpMethod.DELETE, "/api/users/**").hasAnyRole("OPERATOR")
                .antMatchers(HttpMethod.PATCH, "/api/users/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole("OPERATOR")
                .antMatchers(HttpMethod.GET, "/api/trucks/**").authenticated()
                .antMatchers(HttpMethod.POST, "/api/trucks/**").hasAnyRole("OPERATOR")
                .antMatchers(HttpMethod.PUT, "/api/trucks/**").hasAnyRole("OPERATOR")
                .antMatchers(HttpMethod.POST, "/api/menus/menuitem").hasAnyRole("OPERATOR")
                .antMatchers(HttpMethod.PUT, "/api/menus/menuitem/**").hasAnyRole("OPERATOR")
                .antMatchers(HttpMethod.PUT, "/api/trucklocation/**").hasAnyRole("OPERATOR")
                .antMatchers("/api/menus/menuitem/**").authenticated()
                .antMatchers("/api/users/**").authenticated()
                .antMatchers("/api/menuratings/**").authenticated()
                .antMatchers("/api/truckratings/**").authenticated()
                .anyRequest().denyAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());

        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.logout().disable();
    }
}