(ns frontend.notification.type)


(def notification-types #{:info :warning :error})

(defn notification
  ([hiccup]
   (notification hiccup :info 5000))
  ([type hiccup]
   (notification hiccup type 5000))
  ([type hiccup ms]
   (assert (notification-types type))
   {:id (random-uuid)
    :type type
    :hiccup hiccup
    :ms ms}))