package de.diedavids.cuba.instantlauncher.web.launcher.executor


import com.haulmont.cuba.core.global.Scripting
import de.diedavids.cuba.instantlauncher.entity.ScreenLauncherCommand
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component

import javax.inject.Inject

@Slf4j
@Component("ddcil_ScreenParameterScriptEvaluation")
class ScreenParameterScriptEvaluation {

    @Inject
    Scripting scripting

    Map<String, Object> evaluateScreenParameters(ScreenLauncherCommand screenLauncherCommand, Map<String, Object> inputParams) {
        return inputParams + evaluateScriptParams(screenLauncherCommand, inputParams)
    }

    private Map<String, Object> evaluateScriptParams(ScreenLauncherCommand screenLauncherCommand, Map<String, Object> inputParams) {

        if (!screenLauncherCommand.screenParametersScript) {
            return [:]
        }

        try {
            def screenParameters = scripting.evaluateGroovy(screenLauncherCommand.screenParametersScript, inputParams)

            return screenParameters instanceof Map ? screenParameters : [:]
        }
        catch (Exception e) {
            log.error('Error while executing screen parameters script', e)
            return [:]
        }
    }
}
