import java.io.{BufferedReader, BufferedWriter, ByteArrayInputStream, ByteArrayOutputStream }
import java.net._
import scala.io._
import org.scalatest.{FlatSpec, Matchers}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar

class EchoServerTest extends FlatSpec with Matchers with MockitoSugar {

  "get Resource Name" should "Return Http response" in {
    var in = mock[BufferedReader]
    var out = mock[BufferedWriter]

    when(in.readLine()).thenReturn("GET resourceName HTTP/1.1")
    EchoServer.getResourceName(in, out)
    verify(out).write("HTTP/1.1 200 OK \r\n")
    verify(out).write("Content-Type: text/html \r\n")
    verify(out).write("Connection: close \r\n")
    verify(out).write("\r\n\n")
    verify(out).write("<!DOCTYPE html>\r\n")
    verify(out).write("<html lang=\"en\">\r\n")
    verify(out).flush()
    verify(out).close()
    verify(in).close()

    in = mock[BufferedReader]
    out = mock[BufferedWriter]
    when(in.readLine()).thenReturn("GET resourceName HTTP/1.1")
    EchoServer.getResourceName(in, out)
    verify(out).write("HTTP/1.1 404 Not Found \r\n")
    verify(out).write("Content-Type: text/html \r\n")
    verify(out).write("Connection: close \r\n")
    verify(out).write("\r\n\n")
    verify(out).flush()
    verify(out).close()
    verify(in).close()
  }
}
