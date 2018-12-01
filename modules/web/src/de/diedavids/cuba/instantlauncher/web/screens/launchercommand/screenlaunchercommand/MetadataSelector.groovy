package de.diedavids.cuba.instantlauncher.web.screens.launchercommand.screenlaunchercommand

import com.haulmont.chile.core.model.MetaClass
import com.haulmont.chile.core.model.MetaProperty
import com.haulmont.cuba.core.app.dynamicattributes.DynamicAttributes
import com.haulmont.cuba.core.entity.CategoryAttribute
import com.haulmont.cuba.core.entity.Entity
import com.haulmont.cuba.core.global.AppBeans
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

@Component('ddcdi_MetadataSelector')
@CompileStatic
class MetadataSelector {


    @Inject
    Metadata metadata

    @Inject
    Messages messages

    @Inject
    Security security

    @Inject
    WindowConfig windowConfig

    Map<String, Object> getEntitiesLookupFieldOptions() {
        Map<String, Object> options = new TreeMap<>()

        for (MetaClass metaClass : metadataTools.allPersistentMetaClasses) {
            if (readPermitted(metaClass)) {
                Class javaClass = metaClass.javaClass
                if (Entity.isAssignableFrom(javaClass)) {
                    options.put(messageTools.getEntityCaption(metaClass) + ' (' + metaClass.name + ')', metaClass.name)
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



    Map<String, Object> getSceenLookupFieldOptions() {
        Collection<WindowInfo> windows = sortWindowInfos(windowConfig.getWindows());
        Map<String, Object> screens = [:]

        windows.each { WindowInfo windowInfo ->
            String id = windowInfo.getId();
            String menuId = "menu-config." + id;
            String localeMsg = messages.getMessage(AppConfig.getMessagesPack(), menuId);
            String title = menuId.equals(localeMsg) ? id : localeMsg + " (" + id + ")";
            screens[title] = id
        }

        screens
    }

    protected Collection<WindowInfo> sortWindowInfos(Collection<WindowInfo> infos) {
        List<WindowInfo> infosContainer = new ArrayList<>(infos);

        def sortedInfosContainer = infosContainer.sort {o1, o2 ->
            if (o1.getId().contains('$') != o2.getId().contains('$')) {
                if (o1.getId().contains('$')) {
                    return -1;
                } else {
                    return 1;
                }
            }
            else {
                o1.getId() <=> o2.getId();
            }
        }


        return sortedInfosContainer;
    }

}