# Development

This section describes development tools that any developer could find useful whenever contributing or testing code.

## Recommended IDEs

+ Visual Studio Code
+ Atom

<!-- TO-DO -->

### Gradle

The `gradlew` bash/bat scripts help performing from linting to build and release tasks, among others. Here's a short list for some of them.

In Linux, the command is used as follows:

```bash
# In the root directory
./gradlew <task>
```

If executed with no params, `./gradlew` will perform a project build.

#### List tasks

+ **Param:** `tasks`
+ **Description:** returns a descriptive list of runnable tasks

#### Install a debug version

+ **Param:** `installDebug`
+ **Description:** installs an application via `adb` to a connected deviced. It can be found in the apps box as `App Debug`.

### Apk Release

> TO-DO
