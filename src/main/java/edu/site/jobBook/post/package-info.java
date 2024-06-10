/************************************************************************************************************
 * This package handles post-related functionalities, including post management,                            *
 * comment management, and user interaction within the Spring Boot application.                             *
 ************************************************************************************************************
 * Main classes and their responsibilities:                                                                 *
 *                                                                                                          *
 * Post:                                                                                                    *
 * <ul>                                                                                                     *
 * <li>Represents the post entity with fields such as id, caption, image, likes, shares, and comments.</li> *
 * <li>Associates posts with users and companies.</li>                                                      *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * PostComment:                                                                                             *
 * <ul>                                                                                                     *
 * <li>Represents a comment on a post with fields such as id, message, time, and the user who made it.</li> *
 * <li>Associates comments with posts.</li>                                                                 *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * PostDTO:                                                                                                 *
 * <ul>                                                                                                     *
 * <li>Data transfer object used for transferring post data, including caption and image.</li>              *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * PostCommentDTO:                                                                                          *
 * <ul>                                                                                                     *
 * <li>Data transfer object used for transferring post comment data, including message and postId.</li>     *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * PostService:                                                                                             *
 * <ul>                                                                                                     *
 * <li>Handles post-related business logic, such as creating, fetching, and deleting posts.</li>            *
 * <li>Integrates with PostRepository to perform CRUD operations on posts.</li>                             *
 * <li>Manages comments on posts through methods like addCommentToPost and deleteCommentFromPost.</li>      *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * PostCommentService:                                                                                      *
 * <ul>                                                                                                     *
 * <li>Handles post comment-related business logic, such as adding and deleting comments.</li>              *
 * <li>Integrates with PostCommentRepository to perform CRUD operations on comments.</li>                   *
 * <li>Validates users and posts before performing comment operations.</li>                                 *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * PostRepository:                                                                                          *
 * <ul>                                                                                                     *
 * <li>Extends JpaRepository to provide CRUD operations for Post entities.</li>                             *
 * <li>Includes custom query methods like findByUser and findByCompanyId.</li>                              *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * PostCommentRepository:                                                                                   *
 * <ul>                                                                                                     *
 * <li>Extends JpaRepository to provide CRUD operations for PostComment entities.</li>                      *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * PostController:                                                                                          *
 * <ul>                                                                                                     *
 * <li>Handles HTTP requests related to posts, such as creating, fetching, and deleting posts.</li>         *
 * <li>Uses PostService to perform business logic.</li>                                                     *
 * <li>Includes endpoints for adding and deleting comments on posts.</li>                                   *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * Annotations and Frameworks used:                                                                         *
 * <ul>                                                                                                     *
 * <li>Lombok: For boilerplate code reduction using annotations.</li>                                       *
 * <li>Jakarta Persistence (JPA): For ORM mapping and entity management.</li>                               *
 * <li>Spring MVC: For handling HTTP requests and responses.</li>                                           *
 * <li>Spring Data JPA: For data access and repository management.</li>                                     *
 * <li>Spring Boot: For application configuration and setup.</li>                                           *
 * </ul>                                                                                                    *
 *                                                                                                          *
 * Author: Malav Padhya                                                                                     *
 ***********************************************************************************************************/
package edu.site.jobBook.post;

// Import statements for Javadocs
