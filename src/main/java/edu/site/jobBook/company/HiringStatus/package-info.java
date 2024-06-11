/**
 * This package includes classes and enums related to the hiring status of companies, leveraging a NoSQL database using Redis run from a Docker container.
 * <li>CompanyHiringStatus.java: Object Representing the hiring status of a company.
 * <li>HiringStatus.java: Enum representing the different states of hiring status.
 * <li>CompanyStatusInitializer.java: Helper class that injects sample data for the CompanyHiringStatus objects
 * <li>HiringStatusRepository.java: Handling the CRUD operations for a CompanyHiringStatus Objects
 * <li>HiringStatusController.java: provides endpoints for CompanyHiringStatus object logic.
 * <li>HiringStatusService.java: the buisiness logic for a HiringStatusLogic.
 * Additional components within this package include a repository, service, controller, and an initializer for setting up initial data.
 * @author Alexander Lazarov
 */
package edu.site.jobBook.company.HiringStatus;