# lein-marginalia

[![Clojars Project](https://img.shields.io/clojars/v/lein-marginalia.svg)](https://clojars.org/lein-marginalia)

A [Leiningen](https://leiningen.org/) plugin for [Marginalia](https://github.com/clj-commons/marginalia).  You would most likely use this rather than Marginalia directly.

## Installation

### Leiningen 2

Add `[[lein-marginalia "0.9.1"]]` to the `:plugins` entry in
either your `project.clj` file or your `:user` profile.

### Leiningen 1

Add `[lein-marginalia "0.9.1"]` to your `project.clj`'s
`:dev-dependencies` or, install lein-marginalia as a user-level
leiningen plugin by running
`$ lein plugin install lein-marginalia 0.9.1`.

## Usage

`$ lein marg <args>` in your project's root.

License
-------

Copyright (C) 2011-2023 Gary, Fogus, and contributors.

Distributed under the Eclipse Public License, the same as Clojure.
