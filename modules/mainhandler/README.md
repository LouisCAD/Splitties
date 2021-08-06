# Main Handler

*Top-level `mainHandler` property to stop allocating multiple `Handler`s for
main `Looper`.*

Supported platforms: **Android**.

## Setup

If you want to use this dependency without using one of the [fun packs](../../README.md#download),
you can use `Splitties.mainhandler`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-mainhandler`.

## Content

Some Android APIs require you to pass a `Handler` instance.

For the cases where you want to pass one that dispatches onto the main thread,
instead of creating another instance, you can reuse `mainHandler`,
saving allocations, and characters to type. 

Note that `mainHandler` is async, which means there's no vSync delays.
If you need a sync `Handler`, you can simply use `mainHandlerSync`, also provided in this split.
