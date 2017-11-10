import $ivy.{
  `org.scalikejdbc::scalikejdbc:3.0.0`,
  `ch.qos.logback:logback-classic:1.2.3`,
  `mysql:mysql-connector-java:5.1.6`,
  `com.opencsv:opencsv:3.8`
}
@main
def main(monitorSql:String)={
Class.forName("com.mysql.jdbc.Driver")
import scalikejdbc._
import scala.collection.JavaConversions._
import java.io._
import com.opencsv.CSVWriter
implicit val session = AutoSession
GlobalSettings.loggingSQLAndTime = new LoggingSQLAndTimeSettings(
  enabled = false,
  singleLineMode = true,
  logLevel = 'DEBUG
)
GlobalSettings.loggingConnections=false
ConnectionPool.singleton(s"jdbc:mysql://127.0.0.1:3306/jbpm", "root", "root")
GlobalSettings.loggingSQLAndTime = new LoggingSQLAndTimeSettings(
  enabled = false,
  singleLineMode = true,
  logLevel = 'DEBUG
)
GlobalSettings.loggingConnections=false
val select2 = SQLSyntax.createUnsafely(s"${monitorSql}")
val tasks = sql"""${select2}"""
val data = tasks.map(_.toMap.values.toList).list.apply().map(x=>{println();x.mkString(",").split(",")})
val writer = new StringWriter();
val csvWriter = new CSVWriter(writer, ',','\u0000');
csvWriter.writeAll(data);
csvWriter.close();
println(writer);
}
