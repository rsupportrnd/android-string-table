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

    @TaskAction
    public void updateStringResource() throws Exception {
        FileDownloader.main(new String[]{googleDriveCredentialPath, spreadSheetFieldId, outputExcelFileName});
        String outputFilePath = "./output/" + outputExcelFileName;
        StringTableGenerator.main(new String[]{outputFilePath, outputResourcePath, inputSheetName});
    }
}
