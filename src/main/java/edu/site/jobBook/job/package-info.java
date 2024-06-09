/**
 * <p>
 * Package containing classes related to job management.
 * It includes entities representing job details, job applications, job statuses,
 * controllers for handling job-related requests, services for business logic,
 * and repositories for database interactions.
 *</p>
 *
 * <ul> Folder Structure
 * <li>FileUpload Folder: Folder for handling file upload functionality <b>(Non-Persistence Feature + combination of SQL(H2) and NoSQL(Redis))</b>.
 * <li>Dashboard Folder: Folder containing dashboard-related functionality. 
 * <li>JobApplication Folder: Folder for managing job application-related features.
 * <li>JobView Folder: Folder for Job View Count functionality.
 * 
 * <li>Job.java: Entity class representing job details.
 * <li>JobController.java: Controller class for handling job-related requests.
 * <li>JobControllerAPI.java: Controller class for handling job-related requests using REST API.
 * <li>JobDescription.java: Entity class representing job description details(e.g. overview, responsibilities, qualifications etc..).
 * <li>JobDetails.java: Entity class representing job details(e.g. Start Date, End Date, Job Status etc..).
 * <li>JobRepository.java: Repository interface for CRUD operations on jobs.
 * <li>JobService.java: Service class for business logic related to jobs.
 * <li>JobStatus.java: Enum class representing job statuses.
 * 
 * </ul>
 * <br><br>
 * The application uses <b>Thymeleaf templates</b> and associated fragments for rendering views within the application.
 * The application follows the <b>MVC (Model-View-Controller)</b> approach to separate concerns and manage the flow of data.
 * The views are rendered on the server side and sent to the client's browser. Also have used <b>Bootstrap</b> for styling and responsiveness of the application..
 * We use CSRF tokens in HTML pages to enhance security against cross-site request forgery attacks.
 * <b>CSRF token implementation</b> is demonstrated in jobDetails.html for file upload functionality.
 * 
 * <ul> templates:
 * <ul> templates/fragments:
 * <li> adminNav.html: HTML fragment for the admin navigation bar.
 * <li> footer.html: HTML fragment for the footer.
 * <li> header.html: HTML fragment for the header.
 * <li> head.html: HTML fragment for the head.
 * </ul>
 * <li> adminDashboard.html: HTML file for the admin dashboard. //Needs to be updated
 * <li> adminDashboardJobs.html: HTML file for the admin dashboard jobs.(Basic dashboard for admin to view jobs related data)
 * <li> adminDashboardJobsReport.html: HTML file for the admin dashboard jobs report.(Dashboard for admin to view jobs related data in a report format)
 * <li> error.html: HTML file for error handling.
 * <li> home.html: HTML file for the home page.
 * <li> jobCreate.html: HTML file for creating a job.
 * <li> jobDetails.html: HTML file for job details, including CSRF token implementation for file uploads.
 * <li> jobDetailsRightPanel.html: HTML file to fetch job details in jobList Right panel.
 * <li> jobList.html: HTML file for the job list.
 * <li> layout.html: HTML file for the layout(All).
 * <li> layoutAdmin.html: HTML file for the layout(Admin).
 * <li> myJobsPage.html: HTML file for my jobs.
 * <li> myJobsWithFilter.html: Fragment HTML file for my jobs with filter to inject in myJobsPage.
 * </ul> 
 * 
 * 
 * 
 * @author Ravi Dhondkar
 * @version 1.0
 */
package edu.site.jobBook.job;
