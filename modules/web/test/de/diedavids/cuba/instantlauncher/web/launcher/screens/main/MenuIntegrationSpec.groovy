package de.diedavids.cuba.instantlauncher.web.launcher.screens.main

import com.haulmont.cuba.gui.app.security.group.browse.GroupBrowser
import com.haulmont.cuba.gui.components.mainwindow.SideMenu
import com.haulmont.cuba.gui.screen.OpenMode
import com.haulmont.cuba.web.testsupport.proxy.TestServiceProxy
import de.diedavids.cuba.instantlauncher.entity.*
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

    def "side menu shows a launcher command that has no group assigned as a top level menu item"() {

        given:
        mainMenuLauncherCommands([
                new LauncherCommand(
                        showInMainMenu: true,
                        name: "dynamic launcher"
                )
        ])

        when:
        SideMenu sideMenu = showSideMenuRootScreen()

        then:

        sideMenu.menuItems[0].caption == "dynamic launcher"
    }

    def "a launcher command can be performed from the menu"() {

        def userBrowseLauncherCommand = new ScreenLauncherCommand(
                showInMainMenu: true,
                name: "dynamic launcher",
                screenEntity: 'sec$Group',
                screenId: 'sec$Group.browse',
                screenLauncherCommandType: ScreenLauncherCommandType.BROWSE,
                type: LauncherCommandType.SCREEN_LAUNCHER
        )
        given:
        mainMenuLauncherCommands([
                userBrowseLauncherCommand
        ])

        and:
        launcherCommandService.loadWithDetails(userBrowseLauncherCommand) >> userBrowseLauncherCommand

        when:
        SideMenu sideMenu = showSideMenuRootScreen()

        def userBrowseScreenOpenerMenuItem = sideMenu.menuItems[0]

        userBrowseScreenOpenerMenuItem.command.accept(userBrowseScreenOpenerMenuItem)

        def activeScreens = environment.getScreens().getOpenedScreens().activeScreens
        then:
        activeScreens.size() == 1
        activeScreens[0] instanceof GroupBrowser

    }

    def "side menu shows a launcher commands under a group main menu entry if they belong to a group"() {

        given:

        def group = new LauncherCommandGroup(
                name: "group 1",
                showInMainMenu: true
        )

        and:
        mainMenuLauncherCommands([
                new LauncherCommand(
                        showInMainMenu: true,
                        name: "dynamic launcher",
                        group: group
                )
        ])

        and:
        launcherCommandGroups([group])

        when:
        SideMenu sideMenu = showSideMenuRootScreen()

        SideMenu.MenuItem groupMenuItem = sideMenu.menuItems[0]

        then:
        groupMenuItem.caption == "group 1"

        and:
        groupMenuItem.children.size() == 1
        groupMenuItem.children[0].caption == "dynamic launcher"
    }

    def "launcher commands that are activated to main menu, but the group is not are hidden in the menu"() {

        given:

        def group = new LauncherCommandGroup(
                name: "group 1",
                showInMainMenu: false
        )

        and:
        mainMenuLauncherCommands([
                new LauncherCommand(
                        showInMainMenu: true,
                        name: "dynamic launcher",
                        group: group
                )
        ])

        and:
        launcherCommandGroups([group])

        when:
        SideMenu sideMenu = showSideMenuRootScreen()

        then:
        sideMenu.menuItems[0].caption == "Administration"
    }

    def "side menu shows no launcher command in case no main menu launcher commands are available"() {

        given:
        mainMenuLauncherCommands([])

        when:
        SideMenu sideMenu = showSideMenuRootScreen()

        then:
        sideMenu.menuItems[0].caption == "Administration"
    }


    void launcherCommandGroups(List<LauncherCommandGroup> launcherCommandGroups) {
        launcherCommandService.findAllMainMenuGroups(_ as String) >> launcherCommandGroups
    }

    private void mainMenuLauncherCommands(List<LauncherCommand> commands) {
        launcherCommandService.findAllMainMenuLauncherCommands(_ as String) >> commands
    }


    private SideMenu showSideMenuRootScreen() {

        environment.getScreens()
                .create(InstantLauncherSideMenuMainScreen, OpenMode.ROOT)
                .show()


        def mainScreen = new ScreenTestAPI(
                InstantLauncherSideMenuMainScreen,
                environment.getScreens()
                        .getOpenedScreens()
                        .rootScreen
        )

        sideMenu(mainScreen)
    }

    private static SideMenu sideMenu(ScreenTestAPI mainScreen) {
        mainScreen
                .rawComponent(new SideMenuComponentDescriptor("sideMenu")) as SideMenu
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