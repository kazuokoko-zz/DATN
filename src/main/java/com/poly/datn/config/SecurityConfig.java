//package com.poly.datn.config;
//
//import com.poly.datn.entity.Account;
//import com.poly.datn.service.AccountService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
//import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.stream.Collectors;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    AccountService accountService;
//
//    @Autowired
//    BCryptPasswordEncoder pe;
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(username -> {
//            try {
//                Account account = accountService.findById(username);
//                String passworrd = pe.encode(account.getPassword());
//                String[] roles = account.getAccountRoles().stream().map(u -> u.getRole().getId())
//                        .collect(Collectors.toList()).toArray(new String[0]);
//                List<GrantedAuthority> grantList = new ArrayList<>();
//                if (roles != null) {
//                    for (String role : roles) {
//                        // ROLE_USER, ROLE_ADMIN,..
//                        GrantedAuthority authority = new SimpleGrantedAuthority(role);
//                        grantList.add(authority);
//                    }
//                }
//                return User.withUsername(username).password(passworrd).authorities(grantList).roles(roles).build();
//            } catch (NoSuchElementException e) {
//                throw new UsernameNotFoundException(username + " not found!");
//            }
//        });
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//
//        http.authorizeRequests().antMatchers("/order/**").authenticated().antMatchers("/admin/**")
//                .hasAnyRole("STAF", "DIRE").antMatchers("/rest/authorities").hasRole("DIRE").anyRequest().permitAll();
//
//        http.formLogin().loginPage("/security/login/form").loginProcessingUrl("/security/login")
////				.defaultSuccessUrl("/security/login/success", false)
//                .defaultSuccessUrl("/").failureUrl("/security/login/error");
//
//        http.rememberMe().tokenRepository(this.persistentTokenRepository()).tokenValiditySeconds(24 * 60 * 60);
//
//        http.exceptionHandling().accessDeniedPage("/security/unauthoried");
//
//        http.authorizeRequests().and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/api/authentication/logout"))
//                // .logoutUrl("/security/logout")
//                .logoutSuccessUrl("/security/logout/success").deleteCookies("JSESSIONID").invalidateHttpSession(true);
//    }
//
//    @Bean
//    public BCryptPasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository() {
//        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
//        db.setDataSource(dataSource);
//        return db;
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
//    }
//}
