# Reusables
##What's this?
This project is an Android library which provides classes or other code that could be reused in multiple projects.

IMHO, Android framework lacks some facilities, which I try to include here, **split in small different modules**, so you can use only one part of my library without including code you don't care about.

I personally often copy/pasted these classes into new projects, so I'd like to help us save time with this library, and make potential updates to this code easier.
##What's in?
Here's a list of the modules that compose this library:
###Typesafe RecyclerView
This modules consists of two `ViewHolder` subclasses that make it typesafe, and easier to use for the common use case which is to bind a ViewHolder to a POJO. See the sample to understand how it works.
###Selectable ViewGroups
`FrameLayout` is historically the only `ViewGroup` which supports a foreground drawable attribute. The foreground attribute is often used with value `@android:attr/selectableItemBackground` to provide visual feedback when the user touches a selectable element such as a list item in a RecyclerView, which translates as a Ripple effect starting from Android Lollipop. As a consequence, people usually wrap their `RelativeLayout`, `LinearLayout` or other `ViewGroup` with a `FrameLayout` just to provide this foreground attribute. This is unefficient, espacially if this is replicated as it is in a RecyclerView. The foreground attribute is part of the `View` class since Android Marshmallow now, but few people have their minSdk set to 23, so I made this library which provides a foreground attributes that defaults to `@android:attr/selectableItemBackground` for popular `ViewGroups`.

##Improve this library
Included classes are not exhaustive, so if you find some code that follows this library's philosophy and you think it should be here, feel free to open an issue.

And of course, the same goes if you find a bug.