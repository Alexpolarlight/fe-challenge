(ns kasta.fe.main
  (:require [rum.core :as rum]
            [clojure.string :as string]
            [kasta.campaigns.campaigns_networking :as campaigns]
            [cljs-time.core :as time]
            [cljs-time.format :as format]
            ))

(def sharesData campaigns/campaignsData)
(def shares-state (atom sharesData))
(def tag-state (atom "date"))
(def tagValue (str (deref tag-state)))

;;; Start Filtering data
(defn filterData
  "Filter data by actual campaign data and tag "
  [vector tag-state]
  (def tagName (str (deref tag-state)))
  (def sortedVector vector)

  (defn isActual [key1 key2]
    (fn [m]
      (defn- getCurrentDate
        []
        (let [currentDateTime (time/now)]
          (format/unparse
           (format/formatters :date-time-no-ms)
           currentDateTime)))

      (def currentTime (getCurrentDate))

      (and
       (> (compare currentTime (m key1)) 0)
       (< (compare currentTime (m key2)) 0))
      ))

  (defn has-value [key value]
    (fn [m]
      (string/includes? (m key) value)))

  (def sequenceOfMaps (filter (isActual :starts_at :finishes_at) sortedVector))
  (def filtered (if (true? (identical? tagName "date"))
    sequenceOfMaps
    (filter (has-value :tags tagName) sequenceOfMaps)))
  )
;;; End Filtering data

;;; Start Table
(rum/defc shares-table < rum.static
  [data]
    (def campaigns (get (deref data) :items))
  (filterData campaigns tag-state)

  (rum/defc tdProcess
  [data]
  [:tr
   [:td (get data :name)]
   [:td (get data :description)]
   [:td (get data :tags)]
   [:td (get data :starts_at)]
   [:td (get data :finishes_at)]
   ]
  )

  [:table {:class "table"}
   [:thead
    [:tr
     [:th "Name"]
     [:th "Description"]
     [:th "Tag"]
     [:th "Starts at"]
     [:th "Finishes at"]]]
   [:tbody
    (mapv tdProcess filtered)
    ]])
;; End Table

;; Start Tag Selector
(rum/defc heading < rum.static [size text]
  [size text])

(rum/defc tagSelector [tag]
  [:div
   (heading :h3 "Active campaigns list")
   (heading :h4 "Filter tags list")
   [:h4 "Filter by - " @tag]
   [:button {:on-click #(reset! tag "F")} "`F` - Для женщин"]
   [:button {:on-click #(reset! tag "M")} "`M` - Для мужчин"]
   [:button {:on-click #(reset! tag "C")} "`C` - Для детей"]
   [:button {:on-click #(reset! tag "H")} "`H` - Для дома"]
   [:button {:on-click #(reset! tag "A")} "`A` или `P` - Еда и алкоголь"]
   [:div (shares-table shares-state)]
   ]
)
;; End Tag Selector

;; Start Render
(add-watch tag-state :render
           #(rum/mount
             (tagSelector tag-state)

           (js/document.getElementById "content")))

(rum/defc Root []
  [(tagSelector tag-state)]
  )

(defn ^:export trigger-render []
  (rum/mount (Root) (js/document.getElementById "content")))
;; End Render
