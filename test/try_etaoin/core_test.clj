(ns try-etaoin.core-test
  (:require [clojure.test :refer :all]
            [try-etaoin.core :refer :all]
            [try-etaoin.main :refer :all]
            [etaoin.keys :as k]
            [etaoin.api :as eta]
            [etaoin.dev :as dev]))

(def ^:dynamic *driver*)

(defn fixture-driver
  "Executes a test running a driver. Bounds a driver
   with the global *driver* variable."
  [f]
  (eta/with-chrome {:dev
                    {:perf
                     {:level :all
                      :network? true
                      :page? true
                      :interval 1000
                      :categories [:devtools
                                   :devtools.network
                                   :devtools.timeline]}}} driver
    (binding [*driver* driver]
      (f))))

(use-fixtures
  :each ;; start and stop driver for each test
  fixture-driver)

#_(deftest page-response-test
  (testing "FIXME, I fail."
    (doto *driver*
      (init)
      (eta/wait-visible (:developer-name queries)))
    (let [req (last (dev/get-requests *driver*))
          status (get-in req [:response :status])
          _ (do (println req) (println status))]
      (is (= status 200)))))

(deftest login-happy-flow-test
  (testing "check happy flow"
    (doto *driver*
      (init)
      (login happy-flow))
    (let [submit-button-attr (get-elmt-attrs *driver* [{:id :submit-button}] [:id :disabled])] 
      (println submit-button-attr)
      (is (= (:id submit-button-attr) "submit-button"))
      (is (= (:disabled submit-button-attr) "true"))
      )))
