(ns leiningen.marg
  "Run Marginalia against your project source files."
  (:use [leiningen.compile :only [eval-in-project]]
        marginalia.core))

(defn marg [project & args]
  (binding [marginalia.html/*resources* ""]
    (marginalia.core/run-marginalia args))
  #_(eval-in-project project
                   `
                   nil
                   nil
                   '(require 'marginalia.core)))

#_(.setMeta #'marg
          (assoc (.meta #'marg)
            :doc (with-out-str (run-marginalia (list "-h")))))
