package com.oching.hadoop.adhoc.tools

import scala.collection.mutable.Map

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.security.UserGroupInformation

import org.joda.time.{DateTime, Days}

import org.slf4j.LoggerFactory

/**
  * Calculates the latency of the data in HDFS.
  *
  * Expected parameters:
  * - N, where N can be 1 to 12 (to indicate whether to calculate the latency for the last N months)
  * - directory to be checked
  * - partition format (year=yyyy/month=MM/day=dd or partition_date=yyyy-MM-dd
  *
  * To execute:
  *
  * java -jar hadoop-adhoc-tools-1.0-SNAPSHOT-jar-with-dependencies.jar 6 /prod/view/warehouse/sitecatalyst/nimbus/hit_data year=yyyy/month=MM/day=dd
  */
object HdfsDataLatencyChecker {

  val HADOOP_CONF_DIR = "/etc/hadoop/conf"

  val requiredJobArguments =
    "\n[1-12]                 - indicates whether to calculate the latency for the last N months" +
    "\n[/path/to/directory]   - directory in HDFS to be checked for data latency" +
    "\n[partition format]     - format of the partition directories: [partition_year]=yyyy/[partition_month]=MM/[partition_day]=dd or [partition_dir_name]=yyyy-MM-dd"

  val _LOG = LoggerFactory.getLogger(getClass)

  @throws(classOf[Exception])
  def main(args: Array[String]) = {

    if (args.isEmpty || args.length != 3)
      throw new Exception(s"Passed parameters does not match expected arguments. Please provide the following arguments: $requiredJobArguments")

    val Array(lastNMonths, directoryPath, partitionFormat) = args
//    validate(lastNMonths, partitionFormat)

    _LOG.info(s"Starting check of data latency: $lastNMonths $directoryPath $partitionFormat")

    // create Hadoop configuration
    val config = new Configuration()
    config.addResource(new Path(s"file://$HADOOP_CONF_DIR/core-site.xml"))
    config.addResource(new Path(s"file://$HADOOP_CONF_DIR/hdfs-site.xml"))

    config.set("fs.hdfs.impl", classOf[org.apache.hadoop.hdfs.DistributedFileSystem].getName)
    config.set("fs.file.impl", classOf[org.apache.hadoop.fs.LocalFileSystem].getName)

    // TODO: enable from args
    // handle Kerberos authentication, use auth token already in the server
    UserGroupInformation.setConfiguration(config)
    UserGroupInformation.loginUserFromSubject(null)

    val fs = FileSystem.get(config)
    val partitions = partitionFormat.split("/")
    val latencyMap: Map[String, Int] = Map()
    val currentDate = DateTime.now
    var startDate = currentDate.minusMonths(lastNMonths.toInt)
    var ctr = 0

    while (!currentDate.isEqual(startDate)) {
      val partitionPath = new Path(buildPath(directoryPath, partitions, startDate))
      var latestTs = 1L
      val files = fs.globStatus(partitionPath)

      _LOG.debug(s"Processing partition: partitionPath=$partitionPath")

      files.foreach(file => {
          _LOG.debug(s"Processing file: file=${file.getPath.getName} | modificationTime=${file.getModificationTime}")
          if (file.getModificationTime > latestTs) latestTs = file.getModificationTime
      })

      _LOG.debug(s"latestTs=$latestTs | startDate=${startDate.getMillis}")

      val latencyInDays = Days.daysBetween(startDate.withTimeAtStartOfDay(), new DateTime(latestTs).withTimeAtStartOfDay()).getDays.toString
      latencyMap.put(latencyInDays, latencyMap.getOrElse(latencyInDays, 0) + 1)

      _LOG.info(s"Latency for ${startDate.toString("yyyy-MM-dd")}: $latencyInDays")

      ctr = ctr + 1
      startDate = startDate.plusDays(1)
    }

    _LOG.info("Successfully gathered data latency..\n")

    _LOG.info(s"Data latency in $directoryPath for the last $lastNMonths months:")

    latencyMap.foreach(e => {
      _LOG.debug(s"Latency map entry: key=${e._1} val=${e._2}")

      _LOG.info(s"T - ${e._1}: ${((e._2.toDouble) / ctr.toDouble) * 100} %  (${e._2} out of $ctr days)")
    })
  }

  @throws(classOf[Exception])
  private def validate(lastNMonths: String, partitionFormat: String) = {
    if (!"[1-12]".r.findFirstIn(lastNMonths).isDefined)
      throw new Exception("Invalid Last N months argument. Possible values are 0-12")

    if (!"([a-zA-Z_]=yyyy/[a-zA-Z_]=MM/[a-zA-Z_]=dd|[a-zA-Z_]=yyyy-MM-dd)".r.findFirstIn(partitionFormat).isDefined)
      throw new Exception("Invalid partition format. Possible values are [partition_year]=yyyy/[partition_month]=MM/[partition_day]=dd or [partition_dir_name]=yyyy-MM-dd")
  }

  private def buildPath(basePath: String, partitions: Array[String], date: DateTime) =
    basePath + "/" + partitions.map(p => {
      val sepIdx = p.indexOf("=") + 1
      p. substring(0, sepIdx) + date.toString(p.substring(sepIdx))
    }).mkString("/") + "/*"
}
