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

(defn marg
  "Run Marginalia against your project source files."
  [project & args]
  (eval-in-project project
                   `(binding [marginalia.html/*resources* ""]
                      (marginalia.core/run-marginalia (list ~@args)))
                   '(require 'marginalia.core)))
