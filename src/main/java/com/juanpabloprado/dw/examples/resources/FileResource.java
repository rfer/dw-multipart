package com.juanpabloprado.dw.examples.resources;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

/**
 * Created by Juan on 25/02/2015.
 */
@Path("/v1/files")
@Produces(MediaType.APPLICATION_JSON)
public class FileResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileResource.class);

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response post(
            @PathParam("id") String id,
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) throws MessagingException, IOException {
        // TODO: uploadFileLocation should come from config.yml
        String uploadedFileLocation = "C:/Users/Juan/Pictures/uploads/" + fileDetail.getFileName();
        LOGGER.info(uploadedFileLocation);
        // save it
        writeToFile(uploadedInputStream, uploadedFileLocation);
        String output = "File uploaded to : " + uploadedFileLocation;
        return Response.ok(output).build();
    }

    // save uploaded file to new location
    private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) throws IOException {
        int read;
        byte[] bytes = new byte[1024];
        OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
        while ((read = uploadedInputStream.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        out.flush();
        out.close();
    }
}