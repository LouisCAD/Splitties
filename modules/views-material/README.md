# Views Material

*Material Components extension of [Views](../views).*

Supported platforms: **Android**.

## Setup

If you want to use this dependency without using one of the [fun packs](../../README.md#download),
you can use `Splitties.viewsMaterial`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-views-material`.

## Content

### CollapsingToolbarLayout extensions

`contentScrimColor` allows to set the content scrim color of a
`CollapsingToolbarLayout` using property syntax.

### TextInputLayout extensions

The `text` read/write extension property for `TextInputLayout` is an alias
to the `text` property of the child `EditText`.

The `string` read only extension property for `TextInputLayout` allows to
easily get the `String` (immutable) representation of the text of the
child `EditText`.
