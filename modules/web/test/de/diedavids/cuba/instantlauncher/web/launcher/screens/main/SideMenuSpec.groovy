package de.diedavids.cuba.instantlauncher.web.launcher.screens.main

import com.haulmont.cuba.core.app.DataService
import com.haulmont.cuba.gui.components.mainwindow.SideMenu
import com.haulmont.cuba.gui.screen.OpenMode
import com.haulmont.cuba.web.testsupport.TestUiEnvironment
import com.haulmont.cuba.web.testsupport.proxy.TestServiceProxy
import de.diedavids.cuba.instantlauncher.entity.LauncherCommand
import de.diedavids.cuba.instantlauncher.web.launcher.InstantLauncherWebTestContainer
import de.diedavids.cuba.instantlauncher.web.screens.main.InstantLauncherSideMenuMainScreen
import de.diedavids.sneferu.ControllableDataServiceProxy
import de.diedavids.sneferu.UiTestAPI
import de.diedavids.sneferu.components.descriptor.GenericComponentDescriptor
import de.diedavids.sneferu.components.testapi.GenericComponentTestAPI
import de.diedavids.sneferu.environment.SneferuTestUiEnvironment
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

class SideMenuSpec extends Specification {

  @Shared @ClassRule
  SneferuTestUiEnvironment environment =
      new SneferuTestUiEnvironment(InstantLauncherWebTestContainer.Common.INSTANCE)
          .withScreenPackages(
                  "de.diedavids.cuba.instantlauncher.web"
          )
          .withUserLogin("admin")
          .withMainScreen(InstantLauncherSideMenuMainScreen)

  UiTestAPI uiTestAPI

  private ControllableDataServiceProxy dataServiceProxy

  def setup() {
    dataServiceProxy = new ControllableDataServiceProxy(environment.container)
    uiTestAPI = environment.getUiTestAPI()
  }

  void cleanup() {
    uiTestAPI.closeAllScreens()
    TestServiceProxy.clear()
  }

  def "the Side Menu contains the a configure instant launcher"() {

    given:

    dataServiceProxy.returnEntitiesOnLoadList(
            LauncherCommand,
            [new LauncherCommand(
                    showInMainMenu: true,
                    name: "dynamic launcher"
            )]
    )
    TestServiceProxy.mock(DataService.class, this.dataServiceProxy)


    environment.getScreens()
            .create(InstantLauncherSideMenuMainScreen, OpenMode.ROOT)
            .show();

    def rootScreen = uiTestAPI.getOpenedScreen(InstantLauncherSideMenuMainScreen)
    // def rootScreen = environment.getScreens().getOpenedScreens().rootScreen
    /*
    def mainScreen = new ScreenTestAPI(
            InstantLauncherSideMenuMainScreen,
            environment.getScreens()
                    .getOpenedScreens()
                    .rootScreen
    )
     */

    when:
    def sideMenu = rootScreen
            //.component(sideMenu("sideMenu"))

    then:
    /*
    ((SideMenu) sideMenu.rawComponent())
        .getMenuItems().size() == 12
         */
    true
  }

  SideMenuComponentDescriptor sideMenu(String componentId) {
    new SideMenuComponentDescriptor(componentId)
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