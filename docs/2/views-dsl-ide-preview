# Views DSL IDE preview

*Preview UIs made with Splitties Views DSL.*

This [split](../README.md#what-is-a-split "What is a split in Splitties?")
provides a class named `UiPreView` that you can use in xml layout files to
preview your `Ui` subclasses made using [Views DSL](../views-dsl/README.md).

To avoid embedding unused code in your production app, add the dependency
only for the `debug` buildType (using `debugImplementation` in your gradle
file) and put the preview xml layout files in the `src/debug/res/layout/`
directory (not in `src/main/res/layout/`).

## Example

Below is a preview xml layout example that the IDE can display.
It assumes there's a class implementing `Ui` named `MainUi` in the `main`
subpackage (relative to the app/library package name).

```xml
<splitties.views.dsl.idepreview.UiPreView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:splitties_class_package_name_relative="main.MainUi"/>
```

Here's a screenshot of how it looks like with [DemoUi from the sample](
../sample/src/main/java/com/louiscad/splittiessample/demo/DemoUi.kt):

![Example screenshot](Splitties%20View%20DSL%20IDE%20preview%20example.png)
## Important info

### Known issues and their workaround

* If preview doesn't work or doesn't reflect latest changes, it's likely
because you didn't build your project. IDE preview currently only works with
compiled classes and xml layouts, so make sure your project is built before
attempting to preview your `Ui`.
* IDE preview is unstable from my experience on Android Studio 3.1 rc01, so
if you encounter a weird problem with preview, just restarting the IDE may
fix your issue, as it did for me.

### Finding a suitable constructor to instantiate your UI

`UiPreView` is compatible with `Ui` implementations with two kind of
constructors:
* Constructors with a single `Context` parameter.
* Constructors whose first parameter is a `Context` and other parameters are
interfaces. Note that the interface methods need to not be called during
preview, or an `UnsupportedOperationException` will be raised because
`UiPreView` can only create stub implementations. You can use
`View.isInEditMode` to skip code for preview if really needed.

### Finding the class

When using the `splitties_class_package_name_relative` attribute, the
`UiPreView` class will take the `packageName` returned from the `Context`
and append a dot plus the value of the attribute to get the class name of
your `Ui` implementation.

However, you may have configured your build so your debug buildType has an
applicationId suffix that is usually `.debug` like show in the example below:
```groovy
buildTypes {
    debug {
        applicationIdSuffix ".debug" // This changes the packageName returned from a Context
        versionNameSuffix "-DEBUG"
    }
    release {
        // Config of your release build
    }
}
```
That's why by default, the
`UiPreView` class will drop any `.debug` suffix found in the package name
before trying to instantiate the class. If you use another suffix, or have
other suffixes for other debuggable buildTypes, or use productFlavors, you're
in luck! The package name suffix to drop is configurable from your resources.

Just copy paste the string resource below in your project in a value resource
file named something like `config.xml` or `do_not_translate.xml`, and edit it
to the suffix you use:

```xml
<string name="splitties_views_dsl_ide_preview_package_name_suffix" translatable="false">.debug</string>
```

This will override the default value from the library.

Alternatively, you can use the `splitties_class_fully_qualified_name`
attribute instead and specify the full class name with its package.

## Download

```groovy
debugImplementation "com.louiscad.splitties:splitties-views-dsl-ide-preview:$splitties_version"
```
