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

## Using the application component

The `instant-launcher` application component let's you define `Launcher Commands` at runtime that can be executed via a search box.

The following types of instant launchers are supported: 

* Screen Launchers
* Script Launchers
* Bean Launchers

A `Launcher Command` can execute any kind of business logic via a groovy script. For certain specific use cases there are pre-defined Launchers like the Screen Launcher.  Here are some example use cases:

* opening the screen to create a new user
* shortcut for running a specific report and starts the download of the file


### Example usage
To see this application component in action, check out this example: [cuba-example-using-instant-launcher](https://github.com/mariodavid/cuba-example-using-instant-launcher).

### Screenshots

#### Launcher Commands definition
![launcher-commands-overview](https://github.com/mariodavid/cuba-component-instant-launcher/blob/master/img/1-launcher-commands-overview.png)

#### Script launcher definition
![script-launcher-definition](https://github.com/mariodavid/cuba-component-instant-launcher/blob/master/img/2-script-launcher-definition.png)

#### Launcher execution
![launcher execution](https://github.com/mariodavid/cuba-component-instant-launcher/blob/master/img/3-launcher-execution.png)
