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

(def dep ['marginalia "0.8.0"])

(defn- add-marg-dep [project]
  ;; Leiningen 2 is a bit smarter about only conjing it in if it
  ;; doesn't already exist and warning the user.
  (if-let [conj-dependency (resolve 'leiningen.core.project/conj-dependency)]
    (conj-dependency project dep)
    (update-in project [:dependencies] conj dep)))

(defn marg
  "Run Marginalia against your project source files."
  [project & args]
  (eval-in-project (add-marg-dep project)
                   `(binding [marginalia.html/*resources* ""]
                      (marginalia.core/run-marginalia (list ~@args)))
                   '(require 'marginalia.core)))
