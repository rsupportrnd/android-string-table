package com.rsupport.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskProvider;

public class StringTablePlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        StringTableExtension extension = project.getExtensions().create("stringResourceConfig", StringTableExtension.class, project);
        TaskProvider<StringTableTask> task = project.getTasks().register("updateStringResource", StringTableTask.class);
        project.afterEvaluate(target -> {
            (task.get()).setGroup("android");
            (task.get()).googleDriveCredentialPath = extension.googleDriveCredentialPath.get();
            (task.get()).outputExcelFileName = extension.outputExcelFileName.get();
            (task.get()).inputSheetName = extension.inputSheetName.get();
            (task.get()).outputResourcePath = extension.outputResourcePath.get();
            (task.get()).spreadSheetFieldId = extension.spreadSheetFieldId.get();
        });
    }
}
