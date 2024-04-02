(ns frontend.notification
  (:require
   [re-frame.core :as rf]
   [frontend.notification.subscriptions] ; side-effects
   [frontend.notification.events] ; side-effects
   ))

;; stolen from:
;; https://github.com/baskeboler/cljs-karaoke-client/blob/master/src/main/cljs_karaoke/notifications.cljs

; todo: make it more tailwind like
; ; https://www.creative-tim.com/learning-lab/tailwind-starter-kit/documentation/react/alerts

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

(defn type-css-class [notification-type]
  (assert (notification-types notification-type))
  (let [notification-type (or notification-type :info)
        n-class (case notification-type
                  :error "bg-red-100 border-l-4 border-red-500 text-red-700 p-4"
                  :warning "bg-yellow-100 border-l-4 border-yellow-500 text-yellow-700 p-4"
                  :info "bg-blue-100 border-l-4 border-blue-500 text-blue-700 p-4")]
    (str "notification " n-class)))

(defn notification-component [{:keys [id type hiccup]}]
  [:div
   {:key (str "notification-" id)
    :class (type-css-class type)
    :role "alert"}
   [:button
    {:on-click #(rf/dispatch [:notification/dismiss id])}
    [:i.fas.fa-trash.pr-3]]
   hiccup])

(defn ^:export notification-container []
  (let [nots-subs (rf/subscribe [:notifications])]
    [:div {:style {:position "fixed"
                   :width "calc(100vw - 3em)"
                   :top "6em"
                   :right "1em"
                   :z-index 300;
                   :overflow "hidden"}}
     [:style (str ".notification {"
                  "opacity: 0.8; "
                  "margin-bottom 0.5em !important; "
                  "} "
                  ".notification:hover { "
                  "opacity: 1; "
                  "} ")]
     (when (not-empty @nots-subs)
       (doall
        (for [n @nots-subs]
          ^{:key (str "notify-" (:id n))}
          [notification-component n])))]))

(defn ^:export show-notification
  ([hiccup]
   (show-notification :info hiccup 5000))
  ([type hiccup]
   (show-notification type hiccup 5000))
  ([type hiccup ms]
   (let [n (notification type hiccup ms)]
     (rf/dispatch [:notification/add n])
     (:id n))))

