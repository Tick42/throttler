(defproject tick42/throttler "1.0.1"
  :description "Control the throughput of function calls and core.async channels using the token bucket algorithm"
  :url "https://github.com/tick42/throttler"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0" :scope "provided"]
                 [org.clojure/core.async "0.4.490" :scope "provided"]]
  :profiles {:dev {:dependencies [[criterium "0.4.3"]]
                   :plugins      [[test2junit "1.3.3"]]}}

  :test2junit-run-ant false
  :test2junit-output-dir "test-results")
