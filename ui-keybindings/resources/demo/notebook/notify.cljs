(ns demo.notebook.notify
  (:require
   [frontend.notification :refer [show-notification]]))

(show-notification :info [:span.bg-blue-300.inline "the sky is blue!"] 1000)

(show-notification :warning "shipping fee charged is below cost!")

(show-notification :error
        [:span "its " [:span.bg-red-300 "raining"] "in Panama!"]
        0)