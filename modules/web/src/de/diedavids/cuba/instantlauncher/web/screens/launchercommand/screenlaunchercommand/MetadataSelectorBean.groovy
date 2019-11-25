package de.diedavids.cuba.instantlauncher.web.screens.launchercommand.screenlaunchercommand

import com.haulmont.chile.core.model.MetaClass
import com.haulmont.cuba.core.entity.Entity
import com.haulmont.cuba.core.global.MessageTools
import com.haulmont.cuba.core.global.Messages
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.core.global.MetadataTools
import com.haulmont.cuba.core.global.Security
import com.haulmont.cuba.gui.AppConfig
import com.haulmont.cuba.gui.config.WindowConfig
import com.haulmont.cuba.gui.config.WindowInfo
import com.haulmont.cuba.security.entity.EntityOp
import groovy.transform.CompileStatic
import org.springframework.stereotype.Component

import javax.inject.Inject

@Component(MetadataSelector.NAME)
@CompileStatic
class MetadataSelectorBean implements MetadataSelector {

  @Inject
  Metadata metadata

  @Inject
  Messages messages

  @Inject
  Security security

  @Inject
  WindowConfig windowConfig

  private static final String ENTITY_NAME_SEPARATOR = '$'
  private static final String OPEN_BRACKET = ' ('
  private static final String CLOSED_BRACKET = ')'

  Map<String, String> getEntitiesLookupFieldOptions() {
    Map<String, String> options = new TreeMap<>()

    for (MetaClass metaClass : metadataTools.allPersistentMetaClasses) {
      if (readPermitted(metaClass)) {
        Class javaClass = metaClass.javaClass
        if (Entity.isAssignableFrom(javaClass)) {
          options.put(messageTools.getEntityCaption(metaClass) + OPEN_BRACKET + metaClass.name + CLOSED_BRACKET, metaClass.name)
        }
      }
    }

    options
  }

  protected boolean readPermitted(MetaClass metaClass) {
    entityOpPermitted(metaClass, EntityOp.READ)
  }

  protected boolean entityOpPermitted(MetaClass metaClass, EntityOp entityOp) {
    security.isEntityOpPermitted(metaClass, entityOp)
  }

  private MessageTools getMessageTools() {
    messages.tools
  }

  private MetadataTools getMetadataTools() {
    metadata.tools
  }

  Map<String, String> getScreenLookupFieldOptions() {
    Collection<WindowInfo> windows = sortWindowInfos(windowConfig.windows)
    Map<String, String> screens = [:]

    windows.each { WindowInfo windowInfo ->
      String id = windowInfo.id
      String menuId = "menu-config.$id"
      String localeMsg = messages.getMessage(AppConfig.messagesPack, menuId)
      String title = menuId == localeMsg ? id : localeMsg + OPEN_BRACKET + id + CLOSED_BRACKET
      screens[title] = id
    }

    screens
  }

  protected Collection<WindowInfo> sortWindowInfos(Collection<WindowInfo> infos) {
    List<WindowInfo> infosContainer = new ArrayList<>(infos)

    infosContainer.sort { o1, o2 ->
      if (o1.id.contains(ENTITY_NAME_SEPARATOR) != o2.id.contains(ENTITY_NAME_SEPARATOR)) {
        return o1.id.contains(ENTITY_NAME_SEPARATOR)? -1 : 1
      }

      o1.id <=> o2.id
    }

    infosContainer
  }
}