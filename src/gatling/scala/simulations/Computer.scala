package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class Computer extends Simulation {

  val httpProtocol = http
    .baseURL("http://computer-database.gatling.io")
    .inferHtmlResources()
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0")

  val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")



  val scn = scenario("computer")
    .exec(http("request_0")
      .get("/computers")
      .headers(headers_0)
      .resources(http("request_1")
        .get("/favicon.ico")
        .check(status.is(404))))
    .pause(6)
    .exec(http("request_2")
      .get("/computers/new")
      .headers(headers_0)
      .resources(http("request_3")
        .get("/favicon.ico")
        .check(status.is(404))))
    .pause(26)
    .exec(http("request_4")
      .post("/computers")
      .headers(headers_0)
      .formParam("name", "test777")
      .formParam("introduced", "2018-05-9")
      .formParam("discontinued", "218-06-09")
      .formParam("company", "3")
      .resources(http("request_5")
        .get("/favicon.ico")
        .check(status.is(404))))

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}