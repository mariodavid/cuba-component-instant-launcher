package de.diedavids.cuba.instantlauncher.web.launcher.screens.main


import com.haulmont.cuba.gui.components.mainwindow.SideMenu
import com.haulmont.cuba.gui.screen.OpenMode
import com.haulmont.cuba.gui.screen.Screen
import com.haulmont.cuba.web.testsupport.proxy.TestServiceProxy
import de.diedavids.cuba.instantlauncher.entity.LauncherCommand
import de.diedavids.cuba.instantlauncher.service.LauncherCommandService
import de.diedavids.cuba.instantlauncher.web.launcher.InstantLauncherWebTestContainer
import de.diedavids.cuba.instantlauncher.web.screens.main.InstantLauncherSideMenuMainScreen
import de.diedavids.sneferu.UiTestAPI
import de.diedavids.sneferu.components.descriptor.GenericComponentDescriptor
import de.diedavids.sneferu.components.testapi.GenericComponentTestAPI
import de.diedavids.sneferu.environment.SneferuTestUiEnvironment
import de.diedavids.sneferu.screen.ScreenTestAPI
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

class SideMenuSpec extends Specification {

    @Shared
    @ClassRule
    SneferuTestUiEnvironment environment =
            new SneferuTestUiEnvironment(InstantLauncherWebTestContainer.Common.INSTANCE)
                    .withScreenPackages(
                            "de.diedavids.cuba.instantlauncher.web"
                    )
                    .withUserLogin("admin")
                    .withMainScreen(InstantLauncherSideMenuMainScreen)

    UiTestAPI uiTestAPI

    LauncherCommandService launcherCommandService

    def setup() {
        launcherCommandService = Mock(LauncherCommandService)
        launcherCommandService.findAllLauncherCommandsWithShortcuts() >> []
        TestServiceProxy.mock(LauncherCommandService, launcherCommandService)
        uiTestAPI = environment.getUiTestAPI()
    }

    void cleanup() {
        uiTestAPI.closeAllScreens()
    }

    void cleanupSpec() {
        TestServiceProxy.clear()
    }

    def "side menu shows a launcher command that is activated for main menu and has no group assigned as a top level menu item"() {

        given:
        mainMenuLauncherCommands([
                new LauncherCommand(
                        showInMainMenu: true,
                        name: "dynamic launcher"
                )
        ])

        when:
        ScreenTestAPI mainScreen = showSideMenuRootScreen()
        SideMenu sideMenu = sideMenu(mainScreen)

        then:

        sideMenu.menuItems.any { it.caption == "dynamic launcher" }
    }

    def "side menu shows no launcher command in case no main menu launcher commands are available"() {

        given:
        mainMenuLauncherCommands([])

        when:
        ScreenTestAPI mainScreen = showSideMenuRootScreen()

        then:
        SideMenu sideMenu = sideMenu(mainScreen)

        sideMenu.menuItems[0].caption == "Administration"
    }

    private void mainMenuLauncherCommands(List<LauncherCommand> commands) {
        launcherCommandService.findAllMainMenuLauncherCommands(_ as String) >> commands
    }

    private static SideMenu sideMenu(ScreenTestAPI mainScreen) {
        mainScreen
                .rawComponent(new SideMenuComponentDescriptor("sideMenu")) as SideMenu
    }

    private ScreenTestAPI showSideMenuRootScreen() {

        environment.getScreens()
                .create(InstantLauncherSideMenuMainScreen, OpenMode.ROOT)
                .show()


        new ScreenTestAPI(
                InstantLauncherSideMenuMainScreen,
                environment.getScreens()
                        .getOpenedScreens()
                        .rootScreen
        )

    }

}

class SideMenuComponentDescriptor extends GenericComponentDescriptor<SideMenu, GenericComponentTestAPI> {

    SideMenuComponentDescriptor(String componentId) {
        super(SideMenu, componentId)
    }

    @Override
    GenericComponentTestAPI createTestAPI(SideMenu component) {
        new GenericComponentTestAPI(component)
    }
}