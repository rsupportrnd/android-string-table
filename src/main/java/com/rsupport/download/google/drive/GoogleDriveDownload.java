package com.rsupport.download.google.drive;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.rsupport.download.Downloadable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class GoogleDriveDownload implements Downloadable {

    private static final String APPLICATION_NAME = "구글드라이브 file 다운로드";
    private static final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    private static final List<String> scopes = new ArrayList<>();
    private static final String TOKENS_DIRECTORY_PATH = "drive_all_tokens";

    private final String credentialsFilePath;
    private final String fileId;
    private final String fileName;

    public GoogleDriveDownload(String credentialsFilePath, String fileId, String fileName) {
        this.credentialsFilePath = credentialsFilePath;
        this.fileId = fileId;
        this.fileName = fileName;
        scopes.add(DriveScopes.DRIVE);
    }

    @Override
    public java.io.File execute() {
        try {
            final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            final Drive service = new Drive.Builder(httpTransport, jsonFactory, getCredential(httpTransport))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            final File driveFile = service.files().get(fileId).execute();
            final FileExtension fileExtension = FileExtension.from(driveFile);
            final String outputFilePath = makeOutputDirectory() + getExportFile(driveFile, fileExtension);
            final FileOutputStream outputStream = new FileOutputStream(outputFilePath);
            service.files().export(fileId, fileExtension.exportMimeType()).executeMediaAndDownloadTo(outputStream);

            return new java.io.File(outputFilePath);

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getExportFile(File driveFile, FileExtension fileExtension) {
        final String extension = fileExtension.extension();
        String name = fileName;
        if (name.isEmpty()) {
            name = driveFile.getName();
        }

        if (!extension.isEmpty()) {
            return name + "." + extension;
        } else {
            return name;
        }
    }

    private String makeOutputDirectory() {
        final String outputDir = "./output/";
        final java.io.File output = new java.io.File(outputDir);
        if (!output.exists()) {
            output.mkdirs();
        }

        return outputDir;
    }

    private Credential getCredential(NetHttpTransport HTTP_TRANSPORT) throws IOException {
        final FileInputStream in = new FileInputStream(credentialsFilePath);

        final GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));
        final GoogleAuthorizationCodeFlow flow
                = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, jsonFactory, clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        final LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
}
