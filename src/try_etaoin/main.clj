(ns try-etaoin.main
  (:gen-class) ; for -main method in uberjar
  (:require [io.pedestal.http :as server]
            [io.pedestal.http.route :as route]
            [testing.service :as service]
            [etaoin.keys :as k]
            [etaoin.api :as eta]
            [etaoin.dev :as dev]))

#_(def c (eta/chrome {:dev
                {:perf
                 {:level :all
                  :network? true
                  :page? true
                  :interval 1000
                  :categories [:devtools
                               :devtools.network
                               :devtools.timeline]}}}))
(def queries
  {:populate [{:id :populate}]
   :developer-name [{:id :developer-name}]
   :check-boxes [{:tag :div :class "column col-1"}
                 {:tag :fieldset :index 2}
                 {:tag :input}]
   :radio-button [{:tag :div :class "column col-2"}
                  {:tag :fieldset :index 1}
                  {:tag :input}]
   :test-cafe-interface [{:id :preferred-interface}]
   :tried-test-cafe [{:tag :div :class "form-bottom"}
                     {:tag :fieldset :index 1}
                     {:tag :input}]
   :slider [{:id :slider} {:tag :span}]
   :comments [{:id :comments}]
   :article-header [{:id :article-header}]})

(defn checked?
  "see if checkbox checked"
  [driver el]
  (eta/js-execute driver "return arguments[0].checked" el))

#_(def driver1 (eta/firefox))
(def driver2 (eta/chrome))
;;"https://en.wikipedia.org/"
;;"https://devexpress.github.io/testcafe/example/"
(defn test-fixture
  [driver]
  (doto driver
    (eta/go "https://devexpress.github.io/testcafe/example/")
    (eta/wait-visible [{:id :developer-name}])
    (eta/fill {:id :developer-name} "Clojure")
    (eta/click [{:tag :div :class "column col-1"}
                {:tag :fieldset :index 2}
                {:tag :input}])))
