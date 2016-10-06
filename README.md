# producer-consumer-workflow

This is a simple experiment to test the concurrent processing using clojure.core.async channel.

<pre>
                                                  +-----------+
                                        +-------> | Processor | +------+
                                        |         +-----------+        |
                                        |         +-----------+        |
                                        +-------> | Processor | +------+
+----------+                            |         +-----------+        |                            +----------+
| Producer |      +----------------+    |         +-----------+        |    +----------------+      | Consumer |
|          | ---> | input channel  | +----------> | Processor | +---------> | output channel | ---> |          |
+----------+      +----------------+    |         +-----------+        |    +----------------+      +----------+
                                        |         +-----------+        |
                                        +-------> | Processor | +------+
                                        |         +-----------+        |
                                        |         +-----------+        |
                                        +-------> | Processor | +------+
                                                  +-----------+
</pre>

## How to run

    $ lein repl
    $ (-main [number_of_thread] [number_of_item_to_be_processed])