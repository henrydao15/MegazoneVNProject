package com.megazone.authorization.config;

import com.megazone.authorization.common.AuthenticationTokenFilter;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticatedActionsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class KeycloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

	private static final String[] permitUrl = new String[]{"/login", "/logout", "/v2/**", "/permission", "/reLogin"};

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
		// SimpleAuthorityMapper adds the prefix ROLE_ to keycloak roles.
		keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
		auth.authenticationProvider(keycloakAuthenticationProvider);
	}

	@Bean
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		/**
		 * NOTE: You must provide a session authentication strategy bean which should be of type
		 * RegisterSessionAuthenticationStrategy for public or confidential applications and
		 * NullAuthenticatedSessionStrategy for bearer-only applications.
		 */
		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}

	/**
	 * Override to return our fixed filter.
	 *
	 * @return An instance of our fixed {@link KeycloakAuthenticatedActionsFilter}.
	 */
	@Bean
	@Override
	protected KeycloakAuthenticatedActionsFilter keycloakAuthenticatedActionsRequestFilter() {
		return new FixedKeycloakAuthenticatedActionsFilter();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);

		http
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.sessionAuthenticationStrategy(sessionAuthenticationStrategy()).and()
				.authorizeRequests()
				.antMatchers(permitUrl).permitAll()
				.anyRequest().authenticated().and()
				.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint()).and()
				.csrf().disable();
	}

	@Bean
	public OncePerRequestFilter authenticationTokenFilter() {
		return new AuthenticationTokenFilter();
	}

	@Bean
	public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
	}
}
