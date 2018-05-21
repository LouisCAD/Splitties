# Activities

*Start activities with minimal boilerplate.*

## Content

### Starting Activities

The `start` extension function for `Context` takes advantage of reified type
parameters to allow you to write such code: `start<AboutActivity>()`.

There's an optional lambda where the `Intent` is the receiver so you can
edit it (e.g. adding flags) before the activity is started with it.

The `startActivity` extension function for `Context` is designed for implicit
intents. It expects the `Intent` action as first parameter, and takes a
lambda to edit the intent further, like `start`.

## Download

```groovy
implementation "com.louiscad.splitties:splitties-activities:$splitties_version"
```
