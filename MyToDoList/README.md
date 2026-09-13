# ✅ Homework 7 — MyToDoList (RecyclerView + Gson + Notifications)

A small but *complete* todo app: add items, tap to complete, swipe to delete, and everything survives a
restart. This is the homework where `RecyclerView`, `SharedPreferences`, and Android's notification
permission all clicked for me at once. 🧩

## 🗺️ What it does

1. **Add** — title + content in, a timestamped card out. ⏱️
2. **Tap to toggle** — unfinished work floats to the top, finished work sinks to the bottom. 🔄
3. **Swipe left** — the item is gone (on purpose, this time). 🗑️
4. **Restart** — `loadData()` brings the whole list back. 💾
5. **Every add** pops a system notification (Android 13+ permission handled). 🔔

## 🧩 How it's wired

### ✍️ `addTodo()`
Trims both fields, bails silently if either is empty, stamps the time with
`new SimpleDateFormat("yyyy-MM-dd HH:mm")`, inserts the new `TodoItem` at **index 0**, refreshes the
adapter, persists, notifies, and clears the inputs.

### 🔄 Tap to toggle
In [`MainActivity.java`](app/src/main/java/com/example/mytodolist/MainActivity.java) the adapter's click
callback removes the item from its position and re-inserts it — at the **end** if it's now complete, at
**index 0** if it isn't:

```java
item.setCompleted(!item.isCompleted());
todoList.remove(position);
todoList.add(item.isCompleted() ? todoList.size() : 0, item);
```

[`TodoAdapter.java`](app/src/main/java/com/example/mytodolist/TodoAdapter.java) draws the state: a
`Paint.STRIKE_THRU_TEXT_FLAG` on the title while it's done.

### 🗑️ Swipe to delete
An `ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT)` — no drag-and-drop, left only. `onSwiped()`
guards against `NO_POSITION` and stale indices before removing and saving:

```java
if (position == RecyclerView.NO_POSITION || position < 0 || position >= todoList.size()) {
    adapter.notifyDataSetChanged();  // item snaps back instead of crashing
    return;
}
```

### 💾 Persistence
`SharedPreferences` named `todo_prefs`, one key `todo_list`, holding the serialized list. Gson does the
heavy lifting in both directions, with a `TypeToken` to keep the generic type alive:

```java
Type type = new TypeToken<List<TodoItem>>() {}.getType();
return new Gson().fromJson(json, type);
```

A `null` preference (first launch) returns an empty list rather than blowing up. 🎉

### 🔔 Notifications
- Permission: declared in the [manifest](app/src/main/AndroidManifest.xml) and requested at startup through
  `ActivityResultContracts.RequestPermission` — only on API 33+. 📱
- Channel: `todo_channel_v2`, created with `IMPORTANCE_HIGH` and vibration enabled. 📣
- Message: `Successfully added <title> to Todolist.`, posted via `NotificationManagerCompat` as a big-text
  notification that auto-cancels.
- Guardrails: the permission is re-checked right before posting, and the `notify()` call is wrapped in a
  `try/catch (SecurityException)` — because a user can revoke the permission *while the app is running*. 🛡️

## 🎨 The look

Two stacked sections on a very light purple page:

| Color | Value | Used for |
|-------|-------|----------|
| `screen_bg` | `#FFF8F4FF` | Page background |
| `list_section_bg` | `#FFF3EDFF` | "My Tasks" panel |
| `create_section_bg` | `#FFEDE4FF` | "Create Todo" panel |
| `section_border` | `#FFD9C9F3` | Panel outlines |
| `deep_purple` | `#FF4A148C` | Headings + Add button tint |

Each card in [`item_todo.xml`](app/src/main/res/layout/item_todo.xml) is white on the purple panel and
carries three text layers — title (18sp bold), content (14sp, one line, `ellipsize="end"`), time (12sp).
The content is clipped **only in the list**; the model still holds the full string. ✂️

## 🗂️ Where things live

| File | What it's for |
|------|--------------|
| [`MainActivity.java`](app/src/main/java/com/example/mytodolist/MainActivity.java) | Add, toggle, swipe, persist, notify 🧠 |
| [`TodoAdapter.java`](app/src/main/java/com/example/mytodolist/TodoAdapter.java) | Adapter + click callback 🧱 |
| [`TodoItem.java`](app/src/main/java/com/example/mytodolist/TodoItem.java) | Model: title, content, time, completed |
| [`activity_main.xml`](app/src/main/res/layout/activity_main.xml) | List panel on top, create panel at the bottom |
| [`strings.xml`](app/src/main/res/values/strings.xml) | All seven user-visible strings, properly extracted 🔤 |
| [`bg_list_section.xml`](app/src/main/res/drawable/bg_list_section.xml), [`bg_create_section.xml`](app/src/main/res/drawable/bg_create_section.xml) | Rounded panel backgrounds |

## 🚀 Run it

1. Open this `MyToDoList` folder in **Android Studio**. 📂
2. Sync Gradle (first run needs the network, for Gson). 🌐
3. Run it, add a task, and grant the notification prompt when it appears. ▶️
4. Kill the app and reopen it — your list is still there. 💪

## 🧰 Toolchain

| Setting | Value |
|---------|-------|
| Language | Java 11 ☕ |
| `minSdk` / `targetSdk` | 24 / 36 |
| AGP | 9.1.0 |
| Libraries | Gson 2.13.2, Material 1.13.0, RecyclerView 1.4.0 |

## 💭 Notes & takeaways

- `notifyDataSetChanged()` everywhere is the lazy option — every toggle and delete redraws the entire list.
  `notifyItemMoved` / `notifyItemRemoved` would animate it properly. 🐢
- Because tapping **reorders** the list, a click handler must resolve its position at click time
  (`getBindingAdapterPosition()`), never cache the one passed to `onBindViewHolder`.
- Catching `SecurityException` around `notify()` looks paranoid until you realise permissions are mutable at
  runtime. Now it's my default. 🛡️
- Storing JSON in `SharedPreferences` is perfectly fine for tens of items — but this is exactly the seam
  where a Room database would take over.
