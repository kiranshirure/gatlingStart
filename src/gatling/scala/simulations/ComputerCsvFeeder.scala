package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class ComputerCsvFeeder  extends Simulation {

  // this reads the csv file in src/gatling/resources/data
  val httpProtocol = http
    .baseURL("http://computer-database.gatling.io")
    .inferHtmlResources()
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0")
  val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

  val csvFeeder = csv("computerCsvFile.csv").circular


  val scn = scenario("computer")
    .exec(http("Load Computer Database")
      .get("/computers")
      .headers(headers_0)
      .resources(http("Click on add a computer")
        .get("/favicon.ico")
        .check(status.is(404))))
    .pause(6)
    .exec(http("Add a computer page open")
      .get("/computers/new")
      .headers(headers_0)
      .resources(http("request_3")
        .get("/favicon.ico")
        .check(status.is(404))))
    .pause(26)
    //data
      .feed(csvFeeder)
    .exec(http("Enter details")
      .post("/computers")
      .headers(headers_0)
      .formParam("name", "${name}")
      .formParam("introduced", "${introduced}")
      .formParam("discontinued", "${discontinued}")
      .formParam("company", "${company}")
      .resources(http("request_5")
        .get("/favicon.ico")
        .check(status.is(404))))

  setUp(
    scn.inject(atOnceUsers(2))
  ).protocols(httpProtocol)

}