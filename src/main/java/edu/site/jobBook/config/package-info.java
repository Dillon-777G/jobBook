/*********************************************************************************
 * This package handles configuration settings and security configurations       *
 * for the application. It includes custom authentication success handling,      *
 * Redis configuration, and security configurations for Spring Security.         *
 *********************************************************************************
 * Main classes and their responsibilities:                                      *
 *                                                                               *
 * CustomAuthenticationSuccessHandler/CustomLogout:                              *
 * <ul>                                                                          *
 * <li>Handles redirection based on user roles after successful authentication.  *
 * <li>Redirects admin users to the admin page and other users to the home page. *
 * <li> Upon logout, userSession active status is set to false                   *
 * </ul>                                                                         *
 *                                                                               *
 * RedisConfig:                                                                  *
 * <ul>                                                                          *
 * <li>Configures RedisTemplate for Redis operations.</li>                       *
 * <li>Sets key and value serializers for RedisTemplate.</li>                    *
 * </ul>                                                                         *
 *                                                                               *
 * SecurityConfig:                                                               *
 * <ul>                                                                          *
 * <li>Configures security settings using Spring Security.</li>                  *
 * <li>Defines security filter chain, user details service, password encoder,    *
 * and authentication provider.</li>                                             *
 * <li>Disables CSRF for H2 console and frame options for H2 console.</li>       *
 * <li>Implements custom logic for login and logout success handlers</li>        *
 * </ul>                                                                         *
 *                                                                               *
 * SystemConfig:                                                                 *
 * <ul>                                                                          *
 * <li>Provides default user beans for application startup.</li>                 *
 * <li>Defines default users and an admin user with roles and encoded passwords. *
 * <li>Beans are only created in non-test profiles.</li>                         *
 * </ul>                                                                         *
 *                                                                               *
 * Annotations and Frameworks used:                                              *
 * <ul>                                                                          *
 * <li>Spring Security: For handling user authentication and authorization.</li> *
 * <li>Spring Context: For configuration and bean management.</li>               *
 * <li>Spring Data Redis: For Redis integration.</li>                            *
 * <li>Jakarta Servlet: For handling HTTP requests and responses.</li>           *
 * <li>Lombok: For reducing boilerplate code using annotations like @Data,       *
 * @Builder, etc.                                                                *
 * </ul>                                                                         *
 *                                                                               *
 * Authors: Dillon, Alex                                                         *
 ********************************************************************************/


package edu.site.jobBook.config;
