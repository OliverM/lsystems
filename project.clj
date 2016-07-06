(defproject lsystems "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.8.51"]
                 [org.clojure/core.async "0.2.374"
                  :exclusions [org.clojure/tools.reader]]
                 [thi.ng/geom "0.0.1173-SNAPSHOT"]
                 [thi.ng/math "0.2.1"]
                 [reagent "0.6.0-alpha2"]]
  :source-paths ["src/clj"]
  :main ^:skip-aot lsystems.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
