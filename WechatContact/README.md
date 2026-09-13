# 💬 Homework 4 — A WeChat-Style Contact List

A `RecyclerView` with **three different item types**, A–Z section headers, and a hand-drawn alphabet
sidebar for fast scrolling. This is the homework where `getItemViewType()` finally made sense — and where
I wrote my first custom `View`. 🎨

## 🗺️ What's on screen

| Row type | Count | Layout | Holder |
|----------|-------|--------|--------|
| **Entry** (shortcuts) | 5 | [`item_entry.xml`](app/src/main/res/layout/item_entry.xml) — icon + label | `EntryHolder` |
| **Header** (A–Z) | 26 | [`item_header.xml`](app/src/main/res/layout/item_header.xml) — gray strip | `HeaderHolder` |
| **Contact** | 182 | [`item_contact.xml`](app/src/main/res/layout/item_contact.xml) — circular avatar + name | `ContactHolder` |

That's **213 rows** total, in a list that recycles them all. ♻️

The five shortcut entries are `New Friends`, `Chat Only`, `Group Chats`, `Tags`, and `Official Accounts` —
the familiar "top of the address book" cluster. Then each letter from **A to Z** brings **seven** contacts
(`Aaron`, `Abby`, `Adele`… for A; `Xander`, `Xavier`, `Xena`… for X), capped by a `Zuri`. 🧑🤝🧑

Down the right edge sits a 30dp [`SideBarView`](app/src/main/java/com/example/wechatcontact/view/SideBarView.java)
drawing all 26 letters; drag a finger along it and the list jumps to that letter. 📜

## 🧩 How it's wired

**One model, three personalities.** [`ListItem.java`](app/src/main/java/com/example/wechatcontact/model/ListItem.java)
is a single class carrying a `type`, a `text`, and a `letter`, with the type constants doubling as view types:

```java
public static final int TYPE_ENTRY = 0;
public static final int TYPE_HEADER = 1;
public static final int TYPE_CONTACT = 2;
```

The adapter just forwards it (`getItemViewType()` → `list.get(position).getType()`), branches in
`onCreateViewHolder` to pick the layout, then `instanceof`-checks in `onBindViewHolder` to fill the right
`TextView`. It's the simplest multi-type pattern there is. 🧱

**Fake data, real structure.** [`ContactData.java`](app/src/main/java/com/example/wechatcontact/util/ContactData.java)
hand-builds the whole list: 5 entries, then for each letter a header followed by 7 contacts. Every item
carries its `letter` so the sidebar can find it later. 🏗️

**The custom sidebar** — a `View` with no children, doing everything in `onDraw`:

- Font metrics are measured once per pass, then a spacing is chosen: target 80% of the height, clamped so
  letters never overlap (`max(glyphHeight, min(2 × glyphHeight, desiredSpacing))`).
- Every letter is drawn centered horizontally, laid out symmetrically around the vertical midpoint. 📐
- `onTouchEvent` inverts that math: it estimates which index the touch's `y` falls on and fires
  `OnLetterTouchListener`. On `ACTION_UP` it calls `performClick()` so accessibility services stay happy. ♿

**The jump itself** lives in [`MainActivity.java`](app/src/main/java/com/example/wechatcontact/MainActivity.java) —
it walks the list to find the first item whose `letter` matches, then scrolls:

```java
sideBar.setOnLetterTouchListener(letter -> {
    for (int i = 0; i < list.size(); i++) {
        if (letter.equals(list.get(i).getLetter())) {
            final int targetPos = i;
            recyclerView.post(() -> layoutManager.scrollToPositionWithOffset(targetPos, 0));
            break;
        }
    }
});
```

Two details that matter: the `LinearLayoutManager` is kept in a **named variable** (the `RecyclerView`
interface alone can't do offset scrolling), and `post()` defers the scroll until after layout. ⏱️

**Tapping a contact** pops a `Toast` with their name — done inside the adapter's `onBindViewHolder`, since
the contact holder is the only one that needs it. 🔔

## 🗂️ Where things live

| File | What it's for |
|------|--------------|
| [`MainActivity.java`](app/src/main/java/com/example/wechatcontact/MainActivity.java) | Wires the list, the sidebar, and the scroll-to-letter jump 🎛️ |
| [`adapter/ContactAdapter.java`](app/src/main/java/com/example/wechatcontact/adapter/ContactAdapter.java) | Three view types + three holders + the click → Toast 🧠 |
| [`model/ListItem.java`](app/src/main/java/com/example/wechatcontact/model/ListItem.java) | The one model behind all three rows |
| [`model/Contact.java`](app/src/main/java/com/example/wechatcontact/model/Contact.java) | A name + its first letter (currently unreferenced 🤫) |
| [`util/ContactData.java`](app/src/main/java/com/example/wechatcontact/util/ContactData.java) | The hand-written 213-row dataset 📇 |
| [`view/SideBarView.java`](app/src/main/java/com/example/wechatcontact/view/SideBarView.java) | The custom A–Z strip ✍️ |
| [`activity_main.xml`](app/src/main/res/layout/activity_main.xml) | Title bar, list (right-padded 30dp), sidebar 📐 |
| [`values/strings.xml`](app/src/main/res/values/strings.xml) | `app_name` is the only string resource 🔤 |

## 🚀 Run it

**[Android Studio](https://developer.android.com/studio)** — open this `WechatContact` folder (the one with
`settings.gradle.kts`), let Gradle
sync, then Run ▶️.

**Or the command line** (needs a JDK + the Android SDK):

```bash
./gradlew assembleDebug
./gradlew installDebug
```

## 🧰 Toolchain

| Setting | Value |
|---------|-------|
| Language | Java 11 ☕ |
| `minSdk` / `targetSdk` / `compileSdk` | 24 / 36 / 36 (minor API 1) |
| AGP | 9.1.0 |
| UI | AppCompat 1.6.1, Material 1.10.0, ConstraintLayout 2.1.4 |

## 💭 Notes & takeaways

*(Written while building it.)*

- `scrollToPositionWithOffset` needs the **typed** `LinearLayoutManager`; keeping it in a field instead of
  inlining `setLayoutManager(new LinearLayoutManager(this))` is the difference between working and not. 🔑
- Searching the list for the first matching letter means a letter with **no** contacts is silently a no-op —
  harmless here (every letter has 7), but it's the kind of thing that shows up the moment the data is real.
- [`Contact.java`](app/src/main/java/com/example/wechatcontact/model/Contact.java) computes a `firstLetter`
  in its constructor and looks like the intended model — but `ContactData` builds `ListItem`s directly, so
  it's dead code. Worth either using or deleting. 🧹
- The contact layout has an `ImageView` (`imgAvatar`) that nothing ever binds — every row shows the same
  placeholder, and the entry icons are all alike too. 🖼️
- `SideBarView` uses `paint.setTextSize(30)` — **raw pixels**, not sp — so it ignores the user's font-size
  preference. Fine for a fixed 30dp strip, but worth knowing.
- A comment in `ContactData` says each letter has "at least 8 contacts"; it's actually **7**. Comments
  drift; the `grep` doesn't. 😄
