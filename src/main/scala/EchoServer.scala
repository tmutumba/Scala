//***** Echo

import java.net._
import java.io._
import scala.io._

object EchoServer {
  def main(args: Array[String]) {
    val server = new ServerSocket(9998)
    while (true) {
      val s = server.accept()
      val in = new BufferedReader(new InputStreamReader((s.getInputStream)))
      val out = new BufferedWriter(new OutputStreamWriter((s.getOutputStream())))


      getResourceName(in, out)

      out.flush()
      s.close()
    }
  }
  // retrieving first line of request and setting it up for parsing
  def getResourceName(in: BufferedReader, out: BufferedWriter ): Unit = {

    val firstLine = in.readLine()
    val splitString = firstLine.split(" ")
    val requestVerb = splitString(0)
    val requestNoun = splitString(1)
    val requestPlural = splitString(2)
    val resourceName = splitString(3)

    if (resourceName.equals("GET")){
      var link = firstLine.substring(4,firstLine.indexOf("HTTP"))
      if (link.replace("test.html ","404 file.html ").equals("/")) {
        link = "/test.html"
      }
        try {
        val reader = new BufferedReader(new FileReader("src" + link))
        //val w = new BufferedWriter(new FileWriter("404 file.html"))

          //response header
          out.write("HTTP/1.1 200 OK \r\n")
          out.write("Content-Type; text/html \r\n")
          out.write("Connection; close \r\n")
          out.write("\r\n")

          for (line <- reader.lines().toArray()) {
            out.write(line + "\r\n")
          }
        reader.close()
        }
      catch {
        case e: FileNotFoundException =>
          out.write("HTTP/1.1 404 FileNotFound \r\n")
          out.write("Content-Type; text/html \r\n")
          out.write("Connection; close \r\n")
          out.write("\r\n")
          //out.write("<HTML><BODY><TITLE> File Not Found</H1></BODY></HTML> \")")
          //out.write("<HTML><BODY><H1><CENTER> Error 404: File Not Found</CENTER></H1></BODY></HTML>")
          }
      }
    }
}