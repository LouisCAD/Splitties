# Main Handler

*Top-level `mainHandler` property to stop allocating multiple `Handler`s for
main `Looper`.*

If the sentence above is not clear enough or if you need more explanations,
please open an issue.

Note that `mainHandler` is async, which means there's no vSync delays. If you need a
sync `Handler`, you can simply use `mainHandlerAsync`, also provided in this split.

## Download

```groovy
implementation("com.louiscad.splitties:splitties-mainhandler:$splitties_version")
```
