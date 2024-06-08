/**
 * This package contains classes related to file upload functionality.
 * It includes classes for handling file metadata, file upload service, file upload controller,
 * and repositories for storing file metadata and job application files.
 * 
 * <ul> 
 * <li> FileMetadata: Entity class representing metadata of uploaded files.
 * <li> JobApplicationFile: Entity class representing the association between job applications and files.
 * </ul>
 * 
 * <ul> Controllers:
 * <li> FileUploadController: Controller class for handling file upload requests.
 * </ul>
 * 
 * <ul> Services:
 * <li> FileUploadService: Service class for handling file upload operations.
 * </ul>
 * 
 * <ul> Interfaces (Repository):
 * <li> FileMetadataRepository: Repository interface for CRUD operations on file metadata.
 * <li> JobApplicationFileRepository: Repository interface for CRUD operations on job application files.
 * </ul>
 * 
 * This package also leverages a NoSQL database using Redis, which can be run from a Docker container.
 * Note: This feature is designed for non-persistent file storage, meaning that file data is not intended to be permanently stored in a traditional database.
 * @author Ravi Dhondkar
 * @version 1.0
 */
package edu.site.jobBook.job.FileUpload;
