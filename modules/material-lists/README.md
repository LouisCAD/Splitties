# Material Lists

*List item Views implementing [Material Design guidelines](
https://material.io/guidelines) (perfect for usage in a `RecyclerView`).*

Supported platforms: **Android**.

## Setup

If you want to use this dependency without using one of the [fun packs](../../README.md#download),
you can use `Splitties.materialLists`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-material-lists`.

## Content

This [split](../../README.md#what-is-a-split "What is a split in Splitties?")
provides Android `View`s that are the implementation for most of
[the lists components](
https://material.io/guidelines/components/lists.html) and
[the lists controls](
https://material.io/guidelines/components/lists-controls.html) from Material
Design guidelines.

Here's the list of the list item View implementations currently included
in this module:
* `IconOneLineListItem`
* `IconTwoLinesListItem`
* `IconTwoLinesSwitchListItem`
* `IconTwoLinesCheckBoxListItem`
* `SwitchTwoLinesIconListItem`

Feel free to open an issue if you need the implementation of a list item that
is not in this library yet, or feel one can be improved. If so, pull
requests are welcome as long as the code style is kept intact (you can
debate about it in an issue if you feel there's room for improvement).

See an example in the [sample module](../../samples/android-app).
