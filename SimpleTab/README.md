# 🧭 Homework 5 — SimpleTab (ViewPager2 + a Hand-Rolled Tab Switcher)

A pixel-clone of a mobile "service hub" home screen: a dark-brown canvas, one top bar, and two tabs of
rounded cards full of icons. I built the tab switcher **out of two `TextView`s** instead of a `TabLayout`,
and used `layout_weight` to fake a grid — which turned out to be the whole lesson. 📐

## 🗺️ The screen

**Top bar** — a 56dp `ConstraintLayout` with three zones:

| Zone | Contents |
|------|----------|
| Left | Circular avatar (`44dp`, placeholder art) 👤 |
| Center | The custom tab switcher: a translucent white pill holding `我的` / `发现` |
| Right | Search 🔍 and Scan 📷 icons, tinted white |

**Tab 1 — 我的 (`fragment_tab1.xml`)** — one card, `本地生活`:

- Row 1: **4 icons** — 手机充值, 生活缴费, 附近加油, 挂号体检 🎨
- Row 2: **2 icons + 2 invisible spacers** — 同城服务, 买房租房

**Tab 2 — 发现 (`fragment_tab2.xml`)** — two stacked cards:

- `智慧出行`: 4 icons — 滴滴出行, 火车票, 机票, 酒店 ✈️
- `购物娱乐`: **3 icons + 1 spacer** — 美团外卖, 华为商城, 电影票 🎬

Every cell is the same vertical shape: a `48dp` icon on top, a `12sp` label underneath, centered. 🧱

## 🧩 How it's wired

**Click-only switching.** Swiping is deliberately killed, so the tabs are the only way to move:

```java
viewPager.setUserInputEnabled(false);          // no swiping
viewPager.setCurrentItem(1, false);            // jump with no animation
selectTab(1);                                  // then restyle the labels
```

**The switcher itself** is just two `TextView`s. `selectTab(index)` swaps which `ShapeDrawable` each one
uses and flips the text color — selected gets the solid pill, unselected gets the faint one:

```java
tvTabMine.setBackgroundResource(R.drawable.bg_tab_selected);
tvTabMine.setTextColor(getResources().getColor(R.color.tab_selected_text, null));
```

**Pages** are served by `TabPagerAdapter` (a `FragmentStateAdapter` nested in `MainActivity`) returning
`Tab1Fragment` at position 0 and `Tab2Fragment` at position 1. Both fragments are pure inflaters — all the
visual work lives in XML. 📄

**The grid hack** 🪄 — a `LinearLayout` can't do "4 columns, left-aligned, second row half-empty", so each
cell is `layout_width="0dp"` + `layout_weight="1"` (four equal columns), and empty rows are padded with
invisible `View`s that have the same weight:

```xml
<!-- spacer cell: claims a column so the row above stays aligned -->
<View android:layout_width="0dp" android:layout_height="1dp" android:layout_weight="1" />
```

That's why Tab 1's second row has **two** spacers and Tab 2's shopping row has **one** — the icons above
them land in exactly the same columns. 🎯

## 🎨 Palette

| Color | Value | Role |
|-------|-------|------|
| `bg_dark_brown` | `#4A2C1A` | Whole-app background |
| `card_white` | `#E8F0EBE8` | Card fill (low-alpha white) |
| `tab_container_bg` | `#33FFFFFF` | Pill behind both tabs |
| `tab_selected_bg` | `#CCFFFFFF` | Selected tab pill |
| `tab_selected_text` / `tab_unselected_text` | `#4A2C1A` / `#AAFFFFFF` | Label contrast flip |
| `title_text` / `icon_label_text` | `#5C3D22` / `#3D2010` | Card titles vs. icon captions |
| `accent_red` | `#FF4500` | Accent |

Icons are platform `@android:drawable/*` glyphs recolored per-cell with `app:tint` (orange, green, blue,
pink…), so the project ships **zero** icon assets. 🎨

## 🗂️ Where things live

| File | What it's for |
|------|--------------|
| [`MainActivity.java`](app/src/main/java/com/example/simpletab/MainActivity.java) | Top bar wiring, tab styling, `ViewPager2` + adapter 🎛️ |
| [`Tab1Fragment.java`](app/src/main/java/com/example/simpletab/Tab1Fragment.java) / [`Tab2Fragment.java`](app/src/main/java/com/example/simpletab/Tab2Fragment.java) | Two-line inflaters 📄 |
| [`activity_main.xml`](app/src/main/res/layout/activity_main.xml) | Avatar, tab pill, search/scan, pager |
| [`fragment_tab1.xml`](app/src/main/res/layout/fragment_tab1.xml), [`fragment_tab2.xml`](app/src/main/res/layout/fragment_tab2.xml) | The card grids |
| [`bg_card.xml`](app/src/main/res/drawable/bg_card.xml), [`bg_tab_container.xml`](app/src/main/res/drawable/bg_tab_container.xml), [`bg_tab_selected.xml`](app/src/main/res/drawable/bg_tab_selected.xml), [`bg_tab_unselected.xml`](app/src/main/res/drawable/bg_tab_unselected.xml) | The rounded/translucent backgrounds |
| [`strings.xml`](app/src/main/res/values/strings.xml) | Every label, extracted 🔤 |

## 🚀 Run it

1. Use a recent **[Android Studio](https://developer.android.com/studio)** — the project rides on **AGP 9.1.0**. 🆕
2. Open this `SimpleTab` folder and let Gradle sync (it reads `gradle/libs.versions.toml`). 🔄
3. Hit **Run** and switch between `我的` and `发现` by tapping. ▶️

## 🧰 Toolchain

| Setting | Value |
|---------|-------|
| Language | Java 11 ☕ |
| `minSdk` / `targetSdk` | 24 / 36 |
| AGP | 9.1.0 |
| Layout & nav | ConstraintLayout 2.2.1, ViewPager2 1.1.0, Fragment 1.8.9 |
| UI | Material 1.13.0, AppCompat 1.7.1 |

## 💭 Notes & takeaways

- Weighted invisible `View`s are a wonderfully dumb way to get column alignment, and they survive screen
  rotation because everything is `weight`-based rather than fixed-width. 🪄
- **Views can't share a style-less weight trick across rows** — each row needs its own spacers, which is why
  Tab 2 needs a *different* number of them than Tab 1. A `GridLayout` would have expressed this natively.
- The XML sets some initial tab colors, but `selectTab(0)` runs immediately in `onCreate()` and overwrites
  them — the XML values are basically decoration. Remember to keep the two in sync. 🔁
- The two fragments have no code beyond `inflate()`. That's a sign the tab logic is where it belongs (the
  Activity) *and* a hint that a `RecyclerView` would scale better than hand-written card XML. 📈
