/*********************************************************************************
 * The follwing document descibes how the Springboot / MVC                       *
 * framework, jakarta, and lombok are leveraged to create                        *
 * persistence for the user class.                                               *
 * *******************************************************************************
 *                                                                               *
 * lombok                                                                        *
 * <ul>                                                                          *
 * <li>@Data for clean and clear POJOs                                           *
 * <li>@NoArgsConstructor, @AllArgsConstructor for flexible instantiation        *
 * <li>@Builder to support the Builder pattern                                   *
 * </ul>                                                                         *
 *                                                                               *
 *                                                                               *
 * jakarta                                                                       *
 * <ul>                                                                          *
 * <li>@Id to specify the primary key of the entity                              *
 * <li>@GeneratedValue to define the strategy for generating primary key values  *
 * <li>@Embeddable to embed the profile into the user entity                     *
 * <li>@Table for specifying the table name for persistence                      *
 * <li>@Entity to mark the class as a persistence entity                         *
 * </ul>                                                                         *
 *                                                                               *
 * Spring Repository                                                             *
 * <ul>                                                                          *
 * <li>@Repository to indicate the class serves as a repository for data access  *
 * </ul>                                                                         *
 *                                                                               *
 * * Spring MVC / Spring Boot:                                                   *
 * <ul>                                                                          *
 * <li>@RestController - Marks the class as a controller where every method      *
 *  returns a domain object instead of a view.                                   *
 * </li>                                                                         * 
 * <li>@RequestMapping - Maps HTTP requests to handler methods of MVC and REST   *
 *  controllers.                                                                 *
 * </li>                                                                         *
 * <li>@Autowired - Marks a constructor, field, or setter method to be           *
 *  autowired by Spring's dependency injection facilities.                       *
 * </li>                                                                         *
 * <li>@SpringBootTest - Used for integration tests that require the full        *
 *  application context.                                                         *
 * </li>                                                                         *
 * <li>@AutoConfigureMockMvc - Automatically configures MockMvc for testing      *
 *  controllers without starting a server.                                       *
 * </li>                                                                         *
 * </ul>                                                                         *
 *                                                                               *
 * @author Dillon                                                                *
 *********************************************************************************/
package edu.site.jobBook.user;
