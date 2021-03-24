package com.rsupport.plugin;

import com.rsupport.download.FileDownloader;
import com.rsupport.stringtable.StringTableGenerator;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

public class StringTableTask extends DefaultTask {

    public String googleDriveCredentialPath = "";
    public String spreadSheetFieldId = "";
    public String outputExcelFileName = "";
    public String outputResourcePath = "";
    public String inputSheetName = "";
    public int indexRowNumber = 0;

    @TaskAction
    public void updateStringResource() throws Exception {
        File excelFile = FileDownloader.download(googleDriveCredentialPath, spreadSheetFieldId, outputExcelFileName);
        StringTableGenerator.generate(excelFile, outputResourcePath, inputSheetName, indexRowNumber);
    }
}
