# SpriteBrew for Android

This is a standalone, fully native Kotlin Android application project. It does
not embed the web app in a WebView.

Build a debug APK from this directory with:

```sh
./gradlew assembleDebug
```

The APK is written to `app/build/outputs/apk/debug/app-debug.apk`.

The current project is an early UI foundation. Account sign-in, generation,
gallery sync, sprite editing, and export are not implemented in this Android
app yet. The existing web app remains unchanged.
