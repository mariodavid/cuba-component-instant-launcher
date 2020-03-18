package de.diedavids.cuba.instantlauncher.web.launcher.screens.launchercommandgroup

import com.haulmont.cuba.gui.screen.OpenMode;
import com.haulmont.cuba.gui.util.OperationResult
import com.haulmont.cuba.web.app.main.MainScreen
import com.haulmont.cuba.web.testsupport.proxy.TestServiceProxy
import de.diedavids.cuba.instantlauncher.entity.LauncherCommandGroup
import de.diedavids.cuba.instantlauncher.web.launcher.InstantLauncherWebTestContainer
import de.diedavids.cuba.instantlauncher.web.screens.launchercommandgroup.LauncherCommandGroupBrowse
import de.diedavids.cuba.instantlauncher.web.screens.launchercommandgroup.LauncherCommandGroupEdit
import de.diedavids.cuba.instantlauncher.web.screens.main.InstantLauncherSideMenuMainScreen
import de.diedavids.cuba.instantlauncher.web.screens.main.InstantLauncherTopMenuMainScreen
import de.diedavids.sneferu.UiTestAPI
import de.diedavids.sneferu.environment.SneferuTestUiEnvironment
import de.diedavids.sneferu.interactions.SetValueInteraction
import de.diedavids.sneferu.screen.StandardEditorTestAPI
import de.diedavids.sneferu.screen.StandardLookupTestAPI
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

import static de.diedavids.sneferu.ComponentDescriptors.button
import static de.diedavids.sneferu.ComponentDescriptors.textField
import static de.diedavids.sneferu.Interactions.click
import static de.diedavids.sneferu.Interactions.closeEditor
import static de.diedavids.sneferu.Interactions.enter

class CreateLauncherCommandGroupSpec extends Specification {

  @Shared @ClassRule
  SneferuTestUiEnvironment environment =
      new SneferuTestUiEnvironment(InstantLauncherWebTestContainer.Common.INSTANCE)
          .withScreenPackages(
                  "de.diedavids.cuba.instantlauncher.web"
          )
          .withUserLogin("admin")
          .withMainScreen(MainScreen)

  UiTestAPI uiTestAPI

  StandardLookupTestAPI<LauncherCommandGroup, LauncherCommandGroupBrowse> browse
  StandardEditorTestAPI<LauncherCommandGroup, LauncherCommandGroupEdit> editor

  def setup() {
    uiTestAPI = environment.getUiTestAPI()


    browse = uiTestAPI.openStandardLookup(LauncherCommandGroup, LauncherCommandGroupBrowse)

    browse.interact(click(button("createBtn")))

    editor = uiTestAPI.getOpenedEditorScreen(LauncherCommandGroupEdit)

  }

  void cleanup() {
    uiTestAPI.closeAllScreens()
  }

  void cleanupSpec() {
    TestServiceProxy.clear()
  }

  def "a Launcher Command Group can be created with a valid Name and Code"() {

    when:
    OperationResult outcome = editor
       .interact(setNameTo("Hoopa Unbound"))
       .andThen(setCodeTo("720"))
       .andThenGet(closeEditor())

    then:
    outcome == OperationResult.success()

    and:
    uiTestAPI.isActive(browse)
  }

  def "a Launcher Command Group without name cannot be saved"() {

    when:
    OperationResult outcome = editor
       .interact(setNameTo(null))
       .andThenGet(closeEditor())

    then:
    outcome == OperationResult.fail()

    and:
    uiTestAPI.isActive(editor)
  }

  protected SetValueInteraction setCodeTo(String code) {
    enter(textField("codeField"), code)
  }

  protected SetValueInteraction setNameTo(String name) {
    enter(textField("nameField"), name)
  }

}