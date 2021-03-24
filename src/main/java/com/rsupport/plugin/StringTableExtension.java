package com.rsupport.plugin;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;

public class StringTableExtension {

    public Property<String> googleDriveCredentialPath;
    public Property<String> spreadSheetFieldId;
    public Property<String> outputExcelFileName;
    public Property<String> outputResourcePath;
    public Property<String> inputSheetName;

    public StringTableExtension(final Project project) {
        this.googleDriveCredentialPath.set(project.getObjects().property(String.class));
        this.spreadSheetFieldId.set(project.getObjects().property(String.class));
        this.outputExcelFileName.set(project.getObjects().property(String.class));
        this.outputResourcePath.set(project.getObjects().property(String.class));
        this.inputSheetName.set(project.getObjects().property(String.class));
    }

    public void setGoogleDriveCredentialPath(String googleDriveCredentialPath) {
        this.googleDriveCredentialPath.set(googleDriveCredentialPath);
    }

    public void setSpreadSheetFieldId(String spreadSheetFieldId) {
        this.spreadSheetFieldId.set(spreadSheetFieldId);
    }

    public void setOutputExcelFileName(String outputExcelFileName) {
        this.outputExcelFileName.set(outputExcelFileName);
    }

    public void setOutputResourcePath(String outputResourcePath) {
        this.outputResourcePath.set(outputResourcePath);
    }

    public void setInputSheetName(String inputSheetName) {
        this.inputSheetName.set(inputSheetName);
    }
}
