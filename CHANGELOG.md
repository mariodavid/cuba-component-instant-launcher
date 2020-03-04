# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [0.4.1] - 2020-03-04

### Added
- Ability to add Input Parameters to Launcher Commands
- Launcher Commands are part of the search results (via the name) even if it contains no translations
- support for MySQL

### Changed
- Screens are re-implemented with CUBA 7 APIs
- Interface change in the `LauncherCommandExecutor`: added parameter `Map<String, Object> inputParams` to receive the values provided by the user


### Dependencies
- CUBA 7.1.x
- rich-search 1.5.0
- metadataextensions 0.2.1

## [0.3.0] - 2019-07-02

### Added
- Ability to assign keyboard shortcuts to launcher commands

## [0.2.0] - 2019-06-22

### Added
- new shortcut variables for script for new CUBA 7 APIs (notifications, dialogs etc.)

### Dependencies
- CUBA 7.0.x

## [0.1.0] - 2018-12-11

### Added
- Screen Launchers
- Script Launchers
- Bean Launchers

### Dependencies
- CUBA 6.10.x

