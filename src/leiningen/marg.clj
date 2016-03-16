(ns leiningen.marg)

(defn eval-in-project
  "Support eval-in-project in both Leiningen 1.x and 2.x."
  [project form init]
  (let [[eip two?] (or (try (require 'leiningen.core.eval)
                            [(resolve 'leiningen.core.eval/eval-in-project)
                             true]
                            (catch java.io.FileNotFoundException _))
                       (try (require 'leiningen.compile)
                            [(resolve 'leiningen.compile/eval-in-project)]
                            (catch java.io.FileNotFoundException _)))]
    (if two?
      (eip project form init)
      (eip project form nil nil init))))

(def dep ['marginalia "0.9.0"])

(defn- add-marg-dep [project]
  ;; Leiningen 2 is a bit smarter about only conjing it in if it
  ;; doesn't already exist and warning the user.
  (if-let [conj-dependency (resolve 'leiningen.core.project/conj-dependency)]
    (conj-dependency project dep)
    (update-in project [:dependencies] conj dep)))

;; ## Note:
;; The docstring for the marg function is used by Leiningen when a
;; user types `lein help marg`. Because of this, and because, for
;; instance, an escaped asterisk in a docstring will cause errors when
;; you attempt to run `lein marg`, some extra care (read: hacking) is
;; required to get this docstring to look reasonable both in the lein
;; help output and in a marginalia uberdoc.

(defn marg
"Run Marginalia against your project source files.

Usage:
  
    lein marg <options> <files>

Marginalia accepts options as described below:

-d --dir     Directory into which the documentation will be written (default `docs`)

-f --file    File into which the documentation will be written (default `uberdoc.html`)

-n --name    Project name (Taken from `project.clj` by default.)

-v --version Project version (Taken from `project.clj` by default.)

-D --desc    Project description (Taken from `project.clj` by default.)

-a --deps    Project dependencies in the form `<group1>:<artifact1>:<version1>;<group2>...`
             (Taken from `project.clj` by default.)

-c --css     Additional css resources `<resource1>;<resource2>;...` 
             (Taken from `project.clj` by default.)

-j --js      Additional javascript resources `<jsfile1>;<jsfile2>;...` 
             (Taken from `project.clj` by default.)

-m --multi   Generate each namespace documentation as a separate file"
  [project & args]
  (eval-in-project (add-marg-dep project)
                   `(binding [marginalia.html/*resources* ""]
                      (marginalia.core/run-marginalia (list ~@args)))
                   '(require 'marginalia.core)))
