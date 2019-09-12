(ns try-etaoin.shipper
  (:require [etaoin.keys :as k]
            [etaoin.api :as eta]
            [etaoin.dev :as dev]
            [clojure.tools.logging :as log]))

(defn init
  [driver]
  (eta/go driver "https://pr.kargo.tech/shipper/welcome/login"))

(defn fill-phone-number
  [driver number]
  (doto driver
    (eta/wait-visible [{:id :render_phone_number-field}])
    (eta/fill [{:id :render_phone_number-field}] number)))

(defn fill-password
  [driver password]
  (doto driver
    (eta/wait-visible [{:id :render_textfield}])
    (eta/fill [{:id :render_textfield}] password)))

(defn submit
  [driver]
  (doto driver
    (eta/wait-visible [{:id :button_component-button}])
    (eta/click [{:id :button_component-button}])))


(defn get-phone-number-warning
  [driver]
  (eta/get-element-text driver {:css "form>div>div>div:nth-child(3)>div>span"}))

(defn get-password-warning
  [driver]
  (eta/get-element-text driver {:css "form>div>div>div:nth-child(3)>div>span"}))


(defn login
  [driver login-map]
  (let [phone-number (:num login-map)
        password (:pass login-map)]
    (-> driver
      (fill-phone-number phone-number)
      (fill-password password)
      (submit))))

(def home-url "https://pr.kargo.tech/shipper/welcome/home")
(def wrong-password-warning-text "Masukkan password yang benar")
(def wrong-phone-number-warning-text "Email atau nomor telepon Anda tidak terdaftar pada akun Kargo.")

(def happy-flow
  {:num "82137014747"
   :pass "Kargo123"})

(def negative-flow
  {:num "82137014747"
   :pass "kargo123"})

(def negative-flow2
  {:num "81285625353"
   :pass "Kargo123"})

(defn warning-logging
  [warning]
  (case
      wrong-password-warning-text
      wrong-phone-number-warning-text (log/debug "")))

(defn test-happy-flow
  [driver login-map]
  (doto driver
    (init)
    (login login-map))
  (println home-url)
  (println (eta/get-url driver))
  (Thread/sleep 2000)
  (if (= home-url (eta/get-url driver))
    (println "happy-flow succeed")
    (do (Thread/sleep 2000)
      (let [warning (get-phone-number-warning driver)]
        (println (str "happy-flow failed because of " warning))))))
