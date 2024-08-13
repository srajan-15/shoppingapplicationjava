package com.ecommerce.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
     public static PasswordEncoder passwordEncoder() {
   	  return new BCryptPasswordEncoder();
		
    	}
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(authority -> authority
                        .requestMatchers("/base").permitAll()
                        .requestMatchers("/createUsers").permitAll()
                        .requestMatchers("/api/public/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/admin/**").authenticated());
//                        .anyRequest().permitAll())
//                 http.formLogin(formLogin->formLogin
//                		                   .loginPage("/login")
//                		                   .successForwardUrl("/base")
//                		                   );
                
                http.httpBasic(Customizer.withDefaults()).httpBasic(Customizer.withDefaults());

                http.csrf(csrf -> csrf.disable());
        return http.build();

    }
    
//    @Bean
//    public static SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//    	httpSecurity.csrf().disable().
//   	authorizeHttpRequests((authorize)->authorize
//   			                                                .requestMatchers("/base").permitAll()
//   			                                                .requestMatchers("*/api/public/**").hasRole("ROLE_ADMIN")
//   			                                                  .requestMatchers("*/api/admin/**").authenticated())
//   	 .httpBasic(Customizer.withDefaults());
//   	 return httpSecurity.build();
//    }
//    @Bean
//     public static UserDetailsService userDetailsService() {
//   	  UserDetails admin=User
//   			            .builder()
//   			            .username("admin")
//   			            .authorities("ROLE_ADMIN")
//   			            .password(passwordEncoder().encode("admin"))
//   			            .build();
//   	  
//   	  UserDetails srajan=User
//		            .builder()
//		            .username("srajan")
//		            //.authorities("ROLE_USER")
//		            .password(passwordEncoder().encode("srajan"))
//		            .build();
//   	  return new InMemoryUserDetailsManager(admin,srajan);
//     }
}