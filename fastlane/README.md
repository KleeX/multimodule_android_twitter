fastlane documentation
================
# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```
xcode-select --install
```

Install _fastlane_ using
```
[sudo] gem install fastlane -NV
```
or alternatively using `brew cask install fastlane`

# Available Actions
## Android
### android test
```
fastlane android test
```
Runs all the tests
### android beta
```
fastlane android beta
```
Submit a new Beta Build to Crashlytics Beta
### android assemble_apk_for_prod
```
fastlane android assemble_apk_for_prod
```

### android assemble
```
fastlane android assemble
```

### android upload_to_google_play_beta
```
fastlane android upload_to_google_play_beta
```

### android upload_to_google_play_release
```
fastlane android upload_to_google_play_release
```

### android next_version
```
fastlane android next_version
```


----

This README.md is auto-generated and will be re-generated every time [fastlane](https://fastlane.tools) is run.
More information about fastlane can be found on [fastlane.tools](https://fastlane.tools).
The documentation of fastlane can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
