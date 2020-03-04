[![Build Status](https://travis-ci.com/mariodavid/cuba-component-instant-launcher.svg?branch=master)](https://travis-ci.com/mariodavid/cuba-component-instant-launcher)
[ ![Download](https://api.bintray.com/packages/mariodavid/cuba-components/cuba-component-instant-launcher/images/download.svg) ](https://bintray.com/mariodavid/cuba-components/cuba-component-instant-launcher/_latestVersion)
[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

# CUBA Platform Component - Instant Launcher

This application component let's you define launcher commands at runtime that can be directly executed via a search box. 

Instant launcher uses [rich-search](https://github.com/cuba-platform/rich-search-addon) under the hood. It adds a specific search result type to the search results of `rich-search` called `Launcher Commands`. Those Launcher Commands are configurations or scripts, that can be defined at runtime of the application. 


![instant-launcher-overview](https://github.com/mariodavid/cuba-component-instant-launcher/blob/master/img/overview.gif)

## Installation

1. `instant-launcher` is available in the [CUBA marketplace](https://www.cuba-platform.com/marketplace/instant-launcher)
2. Select a version of the add-on which is compatible with the platform version used in your project:

| Platform Version | Add-on Version |
| ---------------- | -------------- |
| 7.0.x            | 0.2.x - 0.3.x  |
| 6.10.x           | 0.1.x          |


The latest version is: [ ![Download](https://api.bintray.com/packages/mariodavid/cuba-components/cuba-component-instant-launcher/images/download.svg) ](https://bintray.com/mariodavid/cuba-components/cuba-component-instant-launcher/_latestVersion)

Add custom application component to your project:

* Artifact group: `de.diedavids.cuba.instantlauncher`
* Artifact name: `instantlauncher-global`
* Version: *add-on version*

```groovy
dependencies {
  appComponent("de.diedavids.cuba.instantlauncher:instantlauncher-global:*addon-version*")
}
```


### CHANGELOG

Information on changes that happen through the different versions of the application component can be found in the [CHANGELOG](https://github.com/mariodavid/cuba-component-instant-launcher/blob/master/CHANGELOG.md).
The Changelog also contains information about breaking changes and tips on how to resolve them.

## Supported DBMS


The following databases are supported by this application component:

* HSQLDB
* PostgreSQL
* MySQL

All other DMBS systems are also possible to work with by the fact that CUBA studio generates the corresponding 
init / update scripts within the application.

## Using the application component

The `instant-launcher` application component let's you define `Launcher Commands` at runtime that can be 
executed as shortcuts via the global search box or a global keyboard shortcut.

Example Launcher Commands:

* opening the screen to create a new user
* shortcut for running a specific report and starts the download of the file
* executing arbitrary business logic like 


### Launcher Command Types

The following types of instant launchers are supported: 

* Screen Launchers
* Script Launchers
* Bean Launchers

### Keyboard Shortcuts for Launcher Commands

It is possible to configure a Keyboard Shortcut to a particular Launcher Command. This Shortcut is globally
available in the application. 

Example:

'CTRL-ALT-U' - Launcher Command: Create a new User


### Main Window Replacement

In order to leverage the capabilities of the instant-launcher application component,
it is required to activate it in the main window.


#### Use default Main Windows of the addon

The addon provides preconfigured main windows screens, that can be configured
to activate the addon behavior:


Set the mainScreenId for the application via `web-app.properties`: 

`cuba.web.mainScreenId`

* `cuba.web.mainScreenId=instantLauncherTopMenuMainScreen` - for Top Menu Layout
* `cuba.web.mainScreenId=instantLauncherSideMenuMainScreen` - for Side Menu Layout
* `cuba.web.mainScreenId=instantLauncherResponsiveSideMenuMainScreen` - for responsive Side Menu Layout


#### Custom Main Window

In case your application already contains a custom Main Window, the following code has to be placed
in order to activate the desired behavior of the addon.


1. before the main screen is shown, the shortcut initializer has to be triggered:

```
import de.diedavids.cuba.instantlauncher.web.launcher.LauncherCommandShortcutInitializer;

@UiController("customApplicationMainScreen")
@UiDescriptor("custom-application-main-screen.xml")
public class InstantLauncherTopMenuMainScreen extends MainScreen implements Window.HasFoldersPane {

    // ...
    
    @Inject
    protected LauncherCommandShortcutInitializer launcherCommandShortcutInitializer;


    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {

        launcherCommandShortcutInitializer.initInstantLauncherShortcuts(
                (RootWindow) this.getWindow()
        );
    }

    // ...  
    
}
```

2. the rich-search bar has to be placed in the main screen and the `ddcil_launcherCommandSearchStrategy` has to be activated for
the search bar:

```
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<window xmlns="http://schemas.haulmont.com/cuba/screen/window.xsd"
        xmlns:search="http://schemas.haulmont.com/cuba/search.xsd">
        caption="mainMsg://application.caption">
    <layout expand="foldersSplit">
        <hbox id="titleBar"
              stylename="c-app-menubar"
              expand="mainMenu"
              width="100%"
              spacing="true"
              margin="false;false;false;true">
            <image id="logoImage"
                   align="MIDDLE_LEFT"
                   scaleMode="SCALE_DOWN"
                   stylename="c-app-icon"/>
            <menu id="mainMenu"
                  align="MIDDLE_LEFT"/>
                  
            <search:richSearch id="search"
                               align="MIDDLE_LEFT"
                               inputPrompt="msg://search"
                               suggestionsLimit="200">
                <search:strategyBean name="search_MainMenuSearchStrategy"/>
                <search:strategyBean name="ddcil_launcherCommandSearchStrategy" />
            </search:richSearch>
            
            <!-- ... -->
        
        </hbox>
        
        <!-- ... -->
        
    </layout>
</window>
```

### Launcher Command Parameters

It is also possible to parametrise Launcher Commands. Defining a parameter for a Launcher Command
results in a Dialog window, that asks the user to enter values for the parameters. 

A Launch Command can have multiple parameters, where a parameter can have the following types:

* String
* Number (Integer, Double, BigDecimal)
* Boolean
* Date (Date, DateTime, LocalDate, LocalDateTime)
* Enum
* Entity

#### Launcher with Parameters
![launcher execution with parameters](https://github.com/mariodavid/cuba-component-instant-launcher/blob/master/img/4-instant-launcher-parameters.gif)


### Example usage
To see this application component in action, check out this example: [cuba-example-using-instant-launcher](https://github.com/mariodavid/cuba-example-using-instant-launcher).

### Screenshots

#### Launcher Commands definition
![launcher-commands-overview](https://github.com/mariodavid/cuba-component-instant-launcher/blob/master/img/1-launcher-commands-overview.png)

#### Script launcher definition
![script-launcher-definition](https://github.com/mariodavid/cuba-component-instant-launcher/blob/master/img/2-script-launcher-definition.png)

#### Launcher execution
![launcher execution](https://github.com/mariodavid/cuba-component-instant-launcher/blob/master/img/3-launcher-execution.png)

