/**
 * This package contains classes related to file upload functionality.
 * It includes classes for handling file metadata, file upload service, file upload controller,
 * and repositories for storing file metadata and job application files.
 * 
 * This package also leverages a <b>combination of SQL (H2) and NoSQL (Redis) databases</b>.
 * <br><br>
 * <b>Note:</b> This feature is designed for <b>non-persistent file storage</b>, 
 * and the uploaded files are stored on the local file system 
 * and the information about the files is stored in the Redis database.
 * 
 * <ul> Folder Structure:
 * <li> Classes and Entities:
 *   <ul>
 *     <li> FileMetadata: Entity class representing metadata of uploaded files.
 *     <li> JobApplicationFile: Entity class representing the association between job applications and files.
 *   </ul>
 * 
 * <li> Controllers:
 *   <ul>
 *     <li> FileUploadController: Controller class for handling file upload requests.
 *   </ul>
 * 
 * <li> Services:
 *   <ul>
 *     <li> FileUploadService: Service class for handling file upload operations.
 *   </ul>
 * 
 * <li> Interfaces (Repository):
 *   <ul>
 *     <li> FileMetadataRepository: Repository interface for CRUD operations on file metadata.
 *     <li> JobApplicationFileRepository: Repository interface for CRUD operations on job application files.
 *   </ul>
 * </ul>
 * 
 * @author Ravi Dhondkar
 * @version 1.0
 */
package edu.site.jobBook.job.FileUpload;
