//
// Copyright 2017 Commonwealth Bank of Australia
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//        http://www.apache.org/licenses/LICENSE-2.0
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//   limitations under the License.

package com.oching.hadoop.adhoc.tools

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.security.UserGroupInformation

trait HadoopFS {

  // TODO: make this configurable from args
  val HADOOP_CONF_DIR = "/etc/hadoop/conf"

  def getFileSystem(hadoopConfDir: String = HADOOP_CONF_DIR): FileSystem = {
    val config = new Configuration()
    config.addResource(new Path(s"file://$hadoopConfDir/core-site.xml"))
    config.addResource(new Path(s"file://$hadoopConfDir/hdfs-site.xml"))
    config.set("fs.hdfs.impl", classOf[org.apache.hadoop.hdfs.DistributedFileSystem].getName)
    config.set("fs.file.impl", classOf[org.apache.hadoop.fs.LocalFileSystem].getName)

    // TODO: enable from args
    // handle Kerberos authentication, use auth token already in the server
    UserGroupInformation.setConfiguration(config)
    UserGroupInformation.loginUserFromSubject(null)

    FileSystem.get(config)
  }

}
