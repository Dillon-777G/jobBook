/**
 * <b> Package File for Milestone 1 for SE452
 * <ul>
 * <li> Defining the Company object for JobBook application
 * <li> Its intended to represent a company information and such as name, about, available jobs. 
 * <ul>
 * <b> Package File update for Milestone 2 for SE452
 * <ul>
 * <li> Company.java - An object that represents a company such as name, description, jobs that it can provides and posts that are made
 * <li> One To Many Company to Jobs
 * <li> One To Many Company to Posts
 * <li> CompanyRepository.java - Handling the CRUD operations for a company 
 * <li> CompanyService.java - The buisiness logic gor acompany
 * <li> CompanyController.java - provides endpoints for company logic.
 * <li>
 * <li> Package File update for Milestone 3 for SE452
 * <li>
 * <li> CompanyUIController.java - Backend endpoints for users to navigate the company page UI.
 * <li> compananies-page.html - bootstrap and thmye static webpage for user to to navigate through the list of companiess
 * <li> companiy-details.html -bootstrap and thyme static webpage for user to view a specific companies detais and access job offerings and posts.
 ** <br>
 * Annotations and Frameworks used:                                                                         *
 * <ul>                                                                                                     *
 * <li>Lombok: For boilerplate code reduction using annotations.</li>                                       *
 * <li>Jakarta Persistence (JPA): For ORM mapping and entity management.</li>                               *
 * <li>Spring Security: For handling user authentication and authorization.</li>                            *
 * <li>Spring MVC: For handling HTTP requests and responses.</li>                                           *
 * <li>Spring Data JPA: For data access and repository management.</li>                                     *
 * <li>Redis: For managing the hiring status of a company
 * </li>                                    *
 * </ul>
 * <br><br>
 * The application uses <b>Thymeleaf templates</b> and associated fragments for rendering views within the application.
 * The application follows the <b>MVC (Model-View-Controller)</b> approach to separate concerns and manage the flow of data.
 * The views are rendered on the server side and sent to the client's browser. Also have used <b>Bootstrap</b> for styling and responsiveness of the application..
 * We use CSRF tokens in HTML pages to enhance security against cross-site request forgery attacks.
 * <b>CSRF token implementation</b> is demonstrated in jobDetails.html for file upload functionality.
 * 
 * <ul> templates:
 * <li> company-list.html: HTML file for viewing existing companies.
 * <li> company-right-panel.html: HTML file to fetch company details in company-list.html Right panel.           
 * <li>company-details.html: HTML for view a specific details and allows view its posts and viewing and navigating to its jobs.
 * @author Alexander Lazarov
 */
package edu.site.jobBook.company;
