# Java coding exercise

The exercise uses a well-known travel domain (flights) and focuses on a
theoretical but production-ready implementation of a flight search service.

The API should allow users to search for and filter a sorted list of airline
codes (representing flights) matching their search criteria.

The service should be backed
by `com.happyflights.availability.FlightAvailabilityService` service, which
returns all data the search service requires.

## Important points

- The intention of the exercise is to test pragmatic core Java 8+ programming
  skills, OOP and domain design.
- Focus on clean code, design patterns, and coding principles.
- Only the dependencies available in the pom should be needed and
  used. [Lombok](https://projectlombok.org/) is provided to reduce boilerplate
  code.
- You should not create a full application (e.g. a webservice). This is just a
  service from component point of view.
- The service should be safe to be used in a concurrent environment. Additional
  non-functional requirements should also be considered.
- Please mind readability and maintainability.
- Existing files must not be changed unless explicitly asked to do so.
- Ensure that your changes can be compiled and tested running `mvn verify`. You
  should have JDK 21+ and Maven 3.8.x+ installed.

## Exercise

Create a flight search service component targeting a Java 21+ runtime
environment.

The API should expect a search query as an argument and return an ordered
collection of airline codes. The service should delegate whatever is possible to
the provided `FlightAvailaibilityService` service and do additional processing
of the results. Implementation of `FlightAvailaibilityService` is expected to be
available at runtime as external dependency, so it is ***not*** required to
provide that implementation nor testing it.

The service should use the `FlightAvailaibilityService` to get a collection of
flights, and then filter, sort and limit the results as per the followings:

- Search for flights based on origin-destination, departure date, and number of
  travellers.
- Filter results according to whether the customer wants flights which can be
  cancelled or not, or is happy with either.
- Filter results below an average price if customer provides such maximum price.
- Sort the results by length of flight or average price depending on the
  customer's preference.
- Allow the number of results to be limited. If no limit is specified, return 3
  results.

For more information on the provided classes in
package `com.happyflights.availability.flight`, please refer to their javadoc
documentation.

If you happen to run into any ambiguous requirements, feel free to decide on
your own but please document your decision.

## Bonus Exercise

The current implementations
of `com.happyflights.availability.FlightAvailabilityRequest`
and `com.happyflights.availability.FlightSummary` are not ideal.

Please add FIXME comment at the problematic part(s) with the reasoning and
a potential fix.
