# Permissions Core

*Request runtime permissions without polluting your codebase.*

Supported platforms: **Android**.

The most straightforward way to request runtime permissions on Android is not… straightforward, and
that's why there are so many libraries that attempt to make it easier.

If you don't use any library, you end up breaking control flow with all permissions request results
coming in a method you must override from your `Activity` or `Fragment` as a couple of two arrays.
That means a lot of code to write, and a lot of ways to mess up.

Splitties Permissions leverages suspending functions, and `ActivityResultRegistry`s under the hood, to
make requesting a permission and handling all the result cases **a single function call**.

## Setup

If you want to use this dependency without using one of the [fun packs](../../../README.md#download),
you can use `Splitties.permissions.core`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-permissions-core`.

## Included functions

Top-level functions:

| **Name**            | **Description**                                                              |
|---------------------|------------------------------------------------------------------------------|
| `hasPermission`     | Returns true if the passed runtime permission is granted to the current app. |
| `requestPermission` | Requests the passed permission if needed and returns the result.             |

Extension functions:

| **Name**                                 | **Description**                                                                                                              |
|------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|
| `ComponentActivity.ensurePermission`     | Requests the passed permission, taking the user to app details in settings if needed and returns only once granted.          |
| `ComponentActivity.ensureAllPermissions` | Requests the passed permissions, taking the user to app details in settings if needed and returns only once all are granted. |

## Usage

The `requestPermission`function is straightforward to use, you just have to write a `when` expression for
its result, but it doesn't help you handling the 2 following cases:
- You need to ask again after showing the rationale
- You need to take the user to your app details in the settings because they checked the do not ask again checkbox.

That's where the `ensurePermission` comes handy. It takes several parameters to handle showing the
rationale, and will automatically open the settings pointing to your app details if the user checked
the do not ask again checkbox.

You can see how to easily implement a higher-level overload that suits the UX you want to provide
for permissions requests with [that example](../../../samples/android-app/src/main/kotlin/com/example/splitties/extensions/permissions/SampleEnsurePermission.kt),
and you can see how it is used in [PermissionsExampleActivity](../../../samples/android-app/src/main/kotlin/com/example/splitties/permissions/PermissionsExampleActivity.kt).
