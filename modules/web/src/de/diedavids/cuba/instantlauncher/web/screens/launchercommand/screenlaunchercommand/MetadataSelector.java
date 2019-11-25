package de.diedavids.cuba.instantlauncher.web.screens.launchercommand.screenlaunchercommand;

import java.util.Map;

public interface MetadataSelector {

    String NAME = "ddcil_MetadataSelector";


    Map<String, String> getScreenLookupFieldOptions();

    Map<String, String> getEntitiesLookupFieldOptions();
}