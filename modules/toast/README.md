# Toast

*Show a toast by just calling `toast(yourText)`, and dodge [API 25
`BadTokenException`](https://github.com/drakeet/ToastCompat#why).*

Supported platforms: **Android**.

To create and show a
[`Toast`](https://developer.android.com/guide/topics/ui/notifiers/toasts.html),
just call `toast(…)` (for breakfast) or `longToast(…)` (if you're very hungry) with
either a string resource id or a `CharSequence`.

## Setup

If you want to use this dependency without using one of the [fun packs](../../README.md#download),
you can use `Splitties.toast`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-toast`.
