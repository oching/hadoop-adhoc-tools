# Hadoop Adhoc Tools

Adhoc utility tools for Hadoop.

To build the project:

```
mvn clean install
```

An executable jar file, with the dependencies included in the bundle, will be generated in the `target` directory.

## HDFS Data Latency Checker

Calculates the percentage (%) of daily partition data latency. This tool is useful for gauging how frequent is your data delayed.

It lists the contents of each daily partition of the target directory path in HDFS, gets the latest modification date of the files, then takes the
difference (in days) between the partition date and the latest modification date. The number of daily partitions with the same latency are grouped
together then each group is divided by the total number of days covered in the calculation to get the % of data that are delayed by N number of days.

Required parameters:

* `N` - where N can be any value from 1 to 12, to indicate the last N months to be covered in the computation

* `/path/to/target/directory/in/HDFS` - the base path of the daily partitioned directory to be checked

* `partition format` - the format of the target directory's daily partition. Possible values are: `[partition_year]=yyyy/[partition_month]=MM/[partition_day]=dd or [partition_dir_name]=yyyy-MM-dd`

The tool expects the `core-site.xml` and `hdfs-site.xml` to be located at `/etc/hadoop/conf/` directory where the jar will be executed.

To run this utility tool, build the project then copy the generated `hadoop-adhoc-tools-1.0-SNAPSHOT-jar-with-dependencies.jar` to the node which acts as a
client for your Hadoop cluster. Make sure that the Hadoop configuration files are in `/etc/hadoop/conf`. To execute the tool, run the following command:

```
java -jar /path/to/hadoop-adhoc-tools-1.0-SNAPSHOT-jar-with-dependencies.jar [N] [/path/to/target/directory/in/HDFS] [partition format]
```


Below is an example to calculate % of daily partition of movies data latency for the last 6 months:

```
java -jar hadoop-adhoc-tools-1.0-SNAPSHOT-jar-with-dependencies.jar 6 /data/movies year=yyyy/month=MM/day=dd
```

It will produce an output similar to this:

```
17/02/13 16:54:25 INFO HdfsDataLatencyChecker$: Starting check of data latency: 6 /data/movies year=yyyy/month=MM/day=dd
17/02/13 16:54:26 WARN NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-13: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-14: 2
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-15: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-16: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-17: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-18: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-19: 1
...
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-02-08: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-02-09: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-02-10: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-02-11: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-02-12: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Successfully gathered data latency..

17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Data latency in /data/movies for the last 6 months:
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: T - 2: 6.521739130434782 %  (12 out of 184 days)
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: T - 3: 0.543478260869565 %  (1 out of 184 days)
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: T - 1: 92.93478260869566 %  (171 out of 184 days)
```

The `T` in the output corresponds to the partition date of the data. Analyzing the output above, it means that for the last 6 months, `92.9%` of the movies data has a latency of `T - 1`, `6.5%` of the movies data has a latency of `T - 2`, and `0.54%` of the movies data has a latency of `T - 3`.