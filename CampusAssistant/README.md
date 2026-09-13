# 🎓 Homework 10 — Campus Assistant (Fragments on a Bottom Nav)

A two-tab freshman-orientation app. One `Activity`, two `Fragment`s, and a `BottomNavigationView`
swapping between them — my first time giving up `startActivity()` and letting fragments do the work.

## 🗺️ The two tabs

| Tab | Fragment | What's on it |
|-----|----------|--------------|
| 迎新服务 (Orientation Services) 📋 | [`ServiceFragment.java`](app/src/main/java/com/example/myapp/ui/ServiceFragment.java) | Four service rows, each with an icon + title + blurb |
| 我的报到 (My Check-in) ✅ | [`ReportFragment.java`](app/src/main/java/com/example/myapp/ui/ReportFragment.java) | Input box to add to-dos, plus the scrollable task list |

[`MainActivity.java`](app/src/main/java/com/example/myapp/MainActivity.java) is a thin host: it owns the
`FrameLayout` container and reacts to the bottom-nav menu, nothing more. 🎛️

## 🧩 How it works

**Tab switching** — one `loadFragment()` helper, wired to the menu:

```java
nav.setOnItemSelectedListener(item -> {
    if (item.getItemId() == R.id.nav_service) { loadFragment(new ServiceFragment()); return true; }
    if (item.getItemId() == R.id.nav_report)  { loadFragment(new ReportFragment());  return true; }
    return false;
});
```

A `replace(R.id.container, fragment)` transaction drops the new tab in; `ServiceFragment` is loaded first
so the app never opens on a blank screen. 🔁

**Service rows built in code, not XML** — the layout only declares four empty `LinearLayout`s (`item1`…`item4`)
that share the `itemStyle` style. `ServiceFragment.setItem()` then inflates each row in Java: an `ImageView`
tinted `#7B1FA2` plus a bold title and a gray description, with a `dp()` helper converting to real pixels.
Tapping a row fires a `Toast` with its title. 🎯

**Adding a to-do** — `ReportFragment` reads both `EditText`s, shows `请填写标题和内容` if either is blank, and
otherwise hands a new `Task` to the adapter, then clears the inputs. Two defaults come pre-loaded:
`完成注册` and `领取校园卡`. ✍️

**The task list logic** — [`TaskAdapter.java`](app/src/main/java/com/example/myapp/ui/TaskAdapter.java)
carries the interesting part:

- [`Task.java`](app/src/main/java/com/example/myapp/ui/Task.java) stamps `createdAt` on construction and
  `completedAt` when you finish it.
- Tapping an item flips `done`; `sortTasks()` re-sorts — **unfinished first, oldest first**, then **finished,
  oldest-completed first**.
- Finished rows get a strike-through on both lines and `alpha = 0.55`, so done work visually recedes. 🌫️

## 🗂️ Where things live

| File | What it's for |
|------|--------------|
| [`activity_main.xml`](app/src/main/res/layout/activity_main.xml) | Fragment container + bottom nav |
| [`bottom_nav_menu.xml`](app/src/main/res/menu/bottom_nav_menu.xml) | The two menu items (`nav_service`, `nav_report`) |
| [`fragment_service.xml`](app/src/main/res/layout/fragment_service.xml) | Four skeleton rows; content injected at runtime |
| [`fragment_report.xml`](app/src/main/res/layout/fragment_report.xml) | Input section + list section |
| [`item_task.xml`](app/src/main/res/layout/item_task.xml) | One task card |
| [`styles.xml`](app/src/main/res/values/styles.xml) | The shared `itemStyle` for the four rows 🎨 |
| [`colors.xml`](app/src/main/res/values/colors.xml) | `purple` `#7B1FA2`, `purple_dark`, `gray` |
| [`bg_*.xml`](app/src/main/res/drawable) | Rounded backgrounds for the rows, cards, and sections |

## 🚀 Run it

1. Open this `CampusAssistant` folder in **Android Studio** (the one holding `settings.gradle.kts`). 📂
2. Let Gradle sync — the wrapper and catalog are pinned, so nothing to configure. 🔄
3. Run on an emulator or device; the app opens on **迎新服务**. ▶️

## 🧰 Toolchain

| Setting | Value |
|---------|-------|
| Language | Java 11 ☕ |
| `minSdk` / `targetSdk` / `compileSdk` | 24 / 36 / 36 (minor API 1) |
| AGP | 9.1.0 |
| UI | Material 1.13.0, RecyclerView 1.4.0, ConstraintLayout 2.2.1 |
| Root project name | `MyApp` |

## 💭 Notes & takeaways

*(Written while building it.)*

- ⚠️ **Tab switching resets the list.** `loadFragment()` builds a *new* `ReportFragment` every time, and its
  `list` is a plain instance field — so tasks you added vanish when you bounce to another tab and back.
  Caching the fragments (or hoisting the list into the Activity / a ViewModel) would fix it.
- `replace()` is the simplest fragment API to reach for, but it's a blunt instrument: no back stack, no
  state retention. Fine for a two-tab app, a trap for anything bigger.
- Building the service rows in Java instead of XML was a deliberate exercise — and a good reminder that the
  layout file then says almost nothing about what's on screen. 🤔
- Only `app_name` lives in [`strings.xml`](app/src/main/res/values/strings.xml); the Chinese UI text is
  hardcoded in Java/XML, so this app doesn't get the resource-extraction treatment the other one does.
- `MainActivity` imports `EdgeToEdge` but never calls it — a leftover import, not a feature. 🧹
