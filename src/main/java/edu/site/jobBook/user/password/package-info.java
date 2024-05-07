/*********************************************************************************
 * The following document describes how the Spring Boot / MVC                    *
 * framework, Jakarta, and Lombok are leveraged to create                        *
 * persistence for the user class, focusing particularly on password management. *
 * *******************************************************************************
 *                                                                               *
 * lombok:                                                                       *
 * <ul>                                                                          *
 * <li>@Data for generating boilerplate code like getters, setters, hashCode,    *
 * equals, and toString automatically.</li>                                      *
 * <li>@NoArgsConstructor and @AllArgsConstructor to provide constructors        *
 * that enhance flexibility and integration with frameworks that require         *
 * parameter-less and parameterized constructors, respectively.</li>             *
 * <li>@Builder to support the Builder design pattern, allowing for fluent       *
 * configuration of object instances.</li>                                       *
 * </ul>                                                                         *
 *                                                                               *
 * jakarta                                                                       *
 * <ul>                                                                          *
 * <li>@Entity to designate a class as an entity in JPA, which means it can      *
 * be mapped to a table in a database.</li>                                      *
 * <li>@Table for specifying the table in the database to which the entity       *
 * maps, here explicitly named "PASSWORDS".</li>                                 *
 * <li>@Id to denote the primary key of the entity.</li>                         *
 * <li>@GeneratedValue to define the strategy for primary key value generation,  *
 * here using the database identity column capability.</li>                      *
 * <li>@Column used to define specifics for a column in the database, here       *
 * marking a column as not nullable.</li>                                        *
 * <li>@OneToOne to establish a one-to-one relationship between the User entity  *
 * and Password entity.</li>                                                     *
 * <li>@JoinColumn to specify the column that joins entities in a relationship,  *
 * here connecting the Password entity to its corresponding User entity by       *
 * user_id.</li>                                                                 *
 * </ul>                                                                         *
 *                                                                               *
 * Spring Framework:                                                             *
 * <ul>                                                                          *
 * <li>@Repository to indicate that the class is a Data Access Object (DAO),     *
 * facilitating exception translation and declarative data access.</li>          *
 * <li>@Service to mark the class as a business service facade, allowing for     *
 * business logic and transaction encapsulation within service methods.</li>     *
 * <li>@Autowired to enable automatic dependency injection of beans by Spring,   *
 * reducing the need for manual instantiation and configuration.</li>            *
 * </ul>                                                                         *
 *                                                                               *
 * @author Dillon                                                                *
 *********************************************************************************/

package edu.site.jobBook.user.password;
