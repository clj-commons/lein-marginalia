(defproject lein-marginalia "0.9.2"
  :description "Leiningen plugin for Marginalia."
  :url "http://github.com/clj-commons/marginalia"
  :license {:name "Eclipse Public License - v 1.0"
          :url "http://www.eclipse.org/legal/epl-v10.html"
          :distribution :repo
          :comments "same as Clojure"}
  :dependencies [[marginalia "0.9.2"]]

  :deploy-repositories [["releases" {:url "https://clojars.org/repo"
                                     :sign-releases false}]
                        ["snapshots" {:url "https://clojars.org/repo"
                                      :sign-releases false}]]

  :eval-in :leiningen)
