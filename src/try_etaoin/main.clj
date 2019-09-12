(ns try-etaoin.main
  (:gen-class) ; for -main method in uberjar
  (:require [etaoin.keys :as k]
            [etaoin.api :as eta]
            [etaoin.dev :as dev]
            [clojure.tools.logging :as log]))

#_(def driver1 (eta/chrome {:dev
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
   :slider [{:id :slider}]
   :slider-button [{:id :slider} {:tag :span}]
   :comments [{:id :comments}]
   :article-header [{:id :article-header}]
   :submit [{:id :submit-button}]})

(defn click-all-el
  [driver query]
  (let [elements (eta/query-all driver query)]
    (mapv #(eta/click-el driver %) elements)))

(defn el-selected?
  "see if checkbox checked"
  [driver el]
  (eta/js-execute driver "return arguments[0].checked" (eta/el->ref el)))

(defn get-elmt-attrs
  "see if checkbox checked"
  [driver query attrs]
  (let []
    (zipmap attrs
            (apply eta/get-element-attrs driver query attrs))))

#_(def driver1 (eta/firefox))
#_(def driver2 (eta/chrome))
(defn driver
  []
  (eta/chrome))

(defn init
  [driver]
  (eta/go driver "https://pr.kargo.tech/shipper/welcome/login"))

(defn fill-name
  [driver]
  (doto driver
    (eta/wait-visible (:developer-name queries))
    (eta/fill (:developer-name queries) "Clojure")))

(defn check-all-features-box
  [driver]
  (doto driver
    (eta/wait-visible (:check-boxes queries))
    (click-all-el (:check-boxes queries))))
