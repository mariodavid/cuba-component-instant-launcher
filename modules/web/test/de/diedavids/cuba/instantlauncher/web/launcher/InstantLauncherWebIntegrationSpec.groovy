package de.diedavids.cuba.instantlauncher.web.launcher

import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.web.app.main.MainScreen
import com.haulmont.cuba.web.testsupport.proxy.TestServiceProxy
import de.diedavids.sneferu.UiTestAPI
import de.diedavids.sneferu.environment.SneferuTestUiEnvironment;
import org.junit.ClassRule;
import spock.lang.Shared;
import spock.lang.Specification;

class InstantLauncherWebIntegrationSpec extends Specification {


  @Shared
  @ClassRule
  SneferuTestUiEnvironment environment =
      new SneferuTestUiEnvironment(InstantLauncherWebTestContainer.Common.INSTANCE)
          .withScreenPackages(
              "com.haulmont.cuba.web.app.main",
              "de.diedavids.cuba.instantlauncher.web"
          )
          .withUserLogin("admin")
          .withMainScreen(MainScreen)

    UiTestAPI uiTestAPI

  Metadata metadata

  def setup() {
    uiTestAPI = environment.getUiTestAPI()

    metadata = AppBeans.get(Metadata)
  }


  void cleanup() {
    uiTestAPI.closeAllScreens()
      TestServiceProxy.clear()
  }


}
