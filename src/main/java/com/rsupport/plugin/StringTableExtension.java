package com.rsupport.plugin;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;

public class StringTableExtension {

    public Property<String> googleDriveCredentialPath;
    public Property<String> spreadSheetFieldId;
    public Property<String> outputExcelFileName;
    public Property<String> outputResourcePath;
    public Property<String> inputSheetName;
    public Property<String> indexRowNumber;

    public StringTableExtension(final Project project) {
        this.googleDriveCredentialPath = project.getObjects().property(String.class);
        this.spreadSheetFieldId = project.getObjects().property(String.class);
        this.outputExcelFileName = project.getObjects().property(String.class);
        this.outputResourcePath = project.getObjects().property(String.class);
        this.inputSheetName = project.getObjects().property(String.class);
        this.indexRowNumber = project.getObjects().property(String.class);
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

    public void setIndexRowNumber(String indexRowNumber) {
        this.indexRowNumber.set(indexRowNumber);
    }
}
