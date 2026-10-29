# Cwaore Casting

This project was generated from a donor template by TemplateForge.
Application type: **Game** — a memory card game: find the matching pairs
(Jetpack Compose, Koin, Room).

- Package: `gourmet.cwaorecasting.hubapp`
- Project name: `Cwaore Casting`

## Where the content lives

- `app/src/main/res/values/strings.xml` — English strings, the primary
  locale, and the only text content of this app: there is no data file
  to fill in. `app/src/main/res/values-<lang>/strings.xml` — the same
  resources translated into de, es, fr, hi, id, pt and ar.
- Card faces are icons from one of the symbol sets in
  `app/src/main/java/<pkg>/ui/composable/screen/game/symbols/` (14
  icons, `CARD_SYMBOL_COUNT`), where `<pkg>` is the package above with
  dots replaced by slashes. The set was selected during generation and
  is not content.
- The number of cards is defined by `Difficulty` in
  `app/src/main/java/<pkg>/data/model/Difficulty.kt`: Easy 12 cards,
  Medium 16, Hard 24, with a preview of 4, 6 and 9 seconds. It is
  fixed.

## Data invariants

- English is the primary language: `values/strings.xml` is in English,
  and every other locale is a translation of it.
- Keep every format placeholder (`%1$d`, `%2$d`, `%1$s`, `%%`) and its
  count, the `string-array` structure and the number of its items. A
  translation keeps the resource name and the same placeholders; their
  order changes only if the language needs it, and then in positional
  form (`%2$s` before `%1$s`).
- The `difficulty_easy`, `difficulty_medium` and `difficulty_hard`
  resources label the fixed `Difficulty` values and keep their meaning
  (easy, medium, hard) in every language, however they are worded.
- A right-to-left language (Arabic, Hebrew, Persian, Urdu) reorders a
  run of digits and separators sitting inside its own text: `%1$d/%2$d`
  can reach the screen as `8/3`, and `%1$d moves · %2$s` or `#%1$d`
  with their parts swapped. Keep the placeholders in the order the
  English string gives them, and wrap each such run in left-to-right
  marks — `\u200E%1$d / %2$d\u200E` — which are invisible and change
  nothing else. It applies to every digit-and-symbol string: a move
  count, a rank, a pair counter, a card-and-pair summary, and a time
  like `1:23` substituted into a sentence.
- `about_rules` describes how the game actually works: all cards are
  shown face up for a few seconds, then hidden; a tap flips a card; the
  player looks for the matching pair of the flipped card; a higher
  difficulty means more cards on the field. Change it only when the
  game behaviour changes, and keep every fact and the same number of
  items in every language.
- Resources marked `translatable="false"` — the app name, the developer
  name, the contact link — stay exactly as they are and are not copied
  into `values-<lang>/`; neither are empty resources.
- In any `strings.xml`, escape an apostrophe as `\'` and an ampersand
  as `&amp;`. A bare apostrophe is still well-formed XML, so a plain
  XML check will not catch it — aapt2 will, by failing the build.

## Text has to fit the layout

- A replacement keeps roughly the length of the text it replaces — the
  screens were built around those lengths, and longer text wraps badly
  or gets truncated on a phone. The same goes for a translation against
  its English original.
- Menu items, buttons, tabs, filter chips, dialog buttons, labels,
  titles and the difficulty names stay short, usually one to three
  words: do not turn a label into a sentence, and do not add marketing
  filler, adjective chains, or the company name where the original had
  none.
- A short text that has no plain, natural synonym stays as it is. Never
  swap it for something ornate just to make it different.

## Working in this repo

- Keep changes scoped to what the current task asks for — this is a
  freshly generated project, not a hand-maintained one, so do not
  "clean up" unrelated files.
- Preserve the existing package structure and naming conventions found
  in the generated code instead of introducing new patterns.
- Do not modify build/gradle configuration, project name, package name,
  or app id — those were set intentionally during generation.
- Screen variants live in `v_*` packages, and the theme and typography
  in `ui/theme`; they were selected during generation and are not
  content.
- Do not run Gradle builds, dependency downloads, Android lint, or
  emulator tests. The project is published as-is after your work.
  Validate only the modified resource files with lightweight local
  checks.
- Do not change business logic or UI code — limit changes strictly to
  the string resources and their translations. Creating the
  `values-<lang>/strings.xml` files is part of that data.
