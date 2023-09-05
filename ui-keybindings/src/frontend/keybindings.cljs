(ns frontend.keybindings
  (:require
   [re-frame.core :as rf]
   ; side effects
   [frontend.keybindings.component]
   [frontend.keybindings.events]
   [frontend.keybindings.init]
   
   ))


(defn keybindings-init! [keybindings-config]
  (rf/dispatch [:keybindings/init keybindings-config]))

(defn palette-show []
   (rf/dispatch [:palette/show]))

