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
        this.googleDriveCredentialPath = project.getObjects().property(String.class);
        this.spreadSheetFieldId = project.getObjects().property(String.class);
        this.outputExcelFileName = project.getObjects().property(String.class);
        this.outputResourcePath = project.getObjects().property(String.class);
        this.inputSheetName = project.getObjects().property(String.class);
    }
}
