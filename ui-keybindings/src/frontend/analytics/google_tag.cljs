(ns frontend.analytics.google-tag
  (:require
   [taoensso.timbre :refer-macros [debug info warn error]]))

   ; gtag("event", "sign_up", {"method": "email" });
(defn send-event [action data]
  (let [datajs (clj->js data)]
    (info "ga event" action data)
    (js/gtag "event" action datajs)))
