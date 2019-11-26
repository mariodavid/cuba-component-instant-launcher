package de.diedavids.cuba.instantlauncher.web.launcher

import com.haulmont.addon.search.gui.components.RichSearch
import com.haulmont.cuba.gui.screen.OpenMode
import de.diedavids.cuba.instantlauncher.web.screens.main.InstantLauncherAppMainScreen

class LauncherCommandSearchStrategySpec extends WebIntegrationSpec {


    def setup() {
        //mainWindow()
    }

    def "all persistent MetaClasses are loaded in the collection data container"() {

        when:
        def mainWindow = screens()
                .create(InstantLauncherAppMainScreen.class, OpenMode.ROOT)
                .show()

        and:
        RichSearch searchBox = mainWindow.getWindow().getComponent("search") as RichSearch

        then: //this is false - just a stub implementation
        !searchBox

    }


}
