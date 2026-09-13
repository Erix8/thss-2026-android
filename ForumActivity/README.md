# 📋 Homework 3 — Two Screens, One Intent

My first "real" Android app: a list you can tap, and a detail page you can come back from. It's a
**LinearLayout** list with two course entries, a **click listener** on each, an **Intent** carrying the
data across, and a **Return** button that just calls `finish()`. Small, but it taught me how Activities
actually talk to each other. 📣

## 🗺️ What it does

1. Launch → **`MainActivity`** shows two tappable course rows: a title and a one-line, ellipsized teaser. 📚
2. Tap a row → **`DetailActivity`** opens and renders the full title + the complete multi-paragraph text.
3. Tap **Return** → `DetailActivity` closes and you're back on the list. ↩️

## 🎭 The two screens

| Piece | What it is |
|-------|-----------|
| [`MainActivity.java`](app/src/main/java/com/example/forumactivity/MainActivity.java) | The list — binds `dynamic1`/`dynamic2`, grabs their text on tap, and fires off the Intent 📤 |
| [`DetailActivity.java`](app/src/main/java/com/example/forumactivity/DetailActivity.java) | The reader — pulls the extras out, fills `tv_detail_title`/`tv_detail_content`, wires the back button 📥 |
| [`activity_main.xml`](app/src/main/res/layout/activity_main.xml) | Vertical `LinearLayout`; each row is `clickable`/`focusable` with a ripple background 🌀 |
| [`activity_detail.xml`](app/src/main/res/layout/activity_detail.xml) | Bold 24sp title, content in a white "card", and the blue Return button 🔵 |

## 🧩 How it's wired

**The Intent contract** — both Activities declare the same two keys, namespaced so they can't collide:

```java
public static final String EXTRA_TITLE   = "com.example.forumactivity.EXTRA_TITLE";
public static final String EXTRA_CONTENT = "com.example.forumactivity.EXTRA_CONTENT";
```

`MainActivity` fills them in `navigateToDetailActivity(...)`, `DetailActivity` reads them back with
`getStringExtra(...)`. One key, one meaning, no guessing. 🔑

**The one-line teaser** — the list rows aren't shortened in Java; the layout does it:

```xml
android:singleLine="true"
android:maxLines="1"
android:ellipsize="end"     <!-- long text becomes "Machine learning is the science…" -->
```

**Null-safe receive** — a missing extra falls back to `""` instead of a `NullPointerException`:

```java
String title = intent.getStringExtra(EXTRA_TITLE) != null
        ? intent.getStringExtra(EXTRA_TITLE) : "";
```

**Edge-to-edge, done properly** — `MainActivity` calls `EdgeToEdge.enable(this)` and then pads the
`@id/main` root using the system-bar insets, so nothing hides under the status or navigation bar. 📐

## 🗂️ Where things live

| File | What it's for |
|------|--------------|
| [`app/build.gradle.kts`](app/build.gradle.kts) | module config: namespace, SDK levels, Java 11 ☕ |
| [`gradle/libs.versions.toml`](gradle/libs.versions.toml) | the version catalog — AGP, AppCompat, Material 🏷️ |
| [`AndroidManifest.xml`](app/src/main/AndroidManifest.xml) | registers both Activities; `MainActivity` is the LAUNCHER 🚪 |
| [`values/strings.xml`](app/src/main/res/values/strings.xml) | every user-visible string, no hardcoding 🔤 |
| [`values/themes.xml`](app/src/main/res/values/themes.xml) | Material 3 DayNight theme, no action bar 🎨 |
| [`values/colors.xml`](app/src/main/res/values/colors.xml), [`values-night/`](app/src/main/res/values-night) | light/dark color resources 🌗 |
| [`res/xml/`](app/src/main/res/xml) | backup / data-extraction rules ☁️ |
| [`res/mipmap-*/`](app/src/main/res/mipmap-hdpi), [`res/drawable/`](app/src/main/res/drawable) | launcher icons 🖼️ |
| [`src/test/`](app/src/test/java/com/example/forumactivity/ExampleUnitTest.java), [`src/androidTest/`](app/src/androidTest/java/com/example/forumactivity/ExampleInstrumentedTest.java) | the generated unit + instrumented test stubs 🧪 |

> ℹ️ `app/build/` is generated output and already git-ignored via [`app/.gitignore`](app/.gitignore) — safe
> to delete, it just costs you a rebuild. 🗑️

## 🔤 All the words live in `strings.xml`

| Resource | Value |
|----------|-------|
| `app_name` | ForumActivity |
| `activity_list_title` | Web Learning |
| `heading1` / `heading2` | Machine Learning / American Society and Culture |
| `content1` / `content2` | the two full course blurbs (multi-paragraph, split with `\n`) 📝 |
| `detail_title_hint` / `detail_content_hint` | preview text for `tools:text` in the editor |
| `btn_back_text` | Return |

Swapping the demo content is a one-file edit — no Java touched. ✏️

## 🛩️ Lift-off

1. Install **Android Studio**, and make sure the **API 36** SDK is installed. 🤖
2. **File → Open** and pick this `ForumActivity` folder (the one with `settings.gradle.kts`).
3. Let **Gradle sync** run — first time needs a network connection. 🔄
4. Start an emulator (or plug in a device) and hit **Run** ▶️.

> ⚙️ The wrapper pins **Gradle 8.13** and the catalog pins **AGP 8.13.2**, so no manual Gradle setup —
> just use the bundled `./gradlew` if you prefer the terminal. 🧰

## 🧰 Toolchain

| Setting | Value |
|---------|-------|
| Language | Java (`VERSION_11`) ☕ |
| `minSdk` / `targetSdk` / `compileSdk` | 24 / 36 / 36 |
| Build script | Kotlin DSL (`.kts`) + version catalog |
| UI | AndroidX AppCompat + Material 3 |

## 💭 Notes & takeaways

*(Written while building it.)*

- Declaring the extra keys as `public static final` on both sides is what stops the classic
  "sent `title`, read `Title`" bug. 🔒
- Clipping the preview in XML (not Java) means the *same* TextView still holds the full text for the
  Intent — no "shortened version" leaking into the detail page. 🤯
- `finish()` is the honest back button: it pops *this* Activity, it doesn't start a new `MainActivity`
  and grow the back stack. 📚
- `tools:text` is a lovely trick — text you can see in the layout preview that never ships. 🛠️
